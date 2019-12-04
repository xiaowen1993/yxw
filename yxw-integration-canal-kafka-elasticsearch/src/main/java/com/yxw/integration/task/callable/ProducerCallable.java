package com.yxw.integration.task.callable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.yxw.integration.common.PropertiesUtil;
import com.yxw.integration.common.SimpleHashPartitionGenerator;

public class ProducerCallable {

	protected final static Logger logger = LoggerFactory.getLogger(ProducerCallable.class);

	private static int debug = PropertiesUtil.debug;

	private static AtomicInteger successed = new AtomicInteger(0);

	private String destination;

	private Producer producer;//kafka producer

	private CanalConnector connector;

	public ProducerCallable(String destination, Producer producer, CanalConnector connector) {
		super();
		this.setDestination(destination);
		this.setProducer(producer);
		this.setConnector(connector);
	}

	public void call() {

		int batchSize = 5 * 1024;

		//		try {

		while (true) {

			try {
				Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
				long batchId = message.getId();
				int size = message.getEntries().size();
				if (batchId == -1 || size == 0) {
					if (debug > 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {

						}
					}
				} else {
					if (debug > 0) {
						logger.debug("message[batchId=%s,size=%s] \n", batchId, size);
					}

					if (syncEntry(message.getEntries())) {
						connector.ack(batchId); // 提交确认
					} else {
						connector.rollback(batchId); // 处理失败, 回滚数据
						logger.error("processing failure, rollback of data.");
					}
				}

				successed.incrementAndGet();
			} catch (Exception e) {
				if (e instanceof CanalClientException) {
					connector.disconnect();
					boolean status = true;
					while (status) {
						try {
							connector.connect();
							if (connector.checkValid()) {
								status = false;
							}
						} catch (Exception e2) {
							status = true;
						}
					}
				}
			}
		}

	}

	private boolean syncEntry(List<Entry> entrys) {
		String topic = "";
		int no = 0;
		RecordMetadata metadata = null;
		boolean ret = true;
		for (Entry entry : entrys) {
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
				continue;
			}

			RowChange rowChage = null;
			try {
				rowChage = RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
				throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
			}

			EventType eventType = rowChage.getEventType();
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> head = new HashMap<String, Object>();
			head.put("binlog_file", entry.getHeader().getLogfileName());
			head.put("binlog_pos", entry.getHeader().getLogfileOffset());
			head.put("db", entry.getHeader().getSchemaName());
			head.put("table", entry.getHeader().getTableName());
			head.put("type", eventType);
			data.put("head", head);

			String tableName = entry.getHeader().getTableName();
			Pattern pattern = Pattern.compile("\\d+$");
			Matcher matcher = pattern.matcher(tableName);

			if (matcher.find()) {
				topic =
						entry.getHeader().getServerId() + "." + entry.getHeader().getSchemaName() + "."
								+ tableName.substring(0, tableName.lastIndexOf("_"));
			} else {
				topic = entry.getHeader().getServerId() + "." + entry.getHeader().getSchemaName() + "." + tableName;
			}

			no = (int) entry.getHeader().getLogfileOffset();
			for (RowData rowData : rowChage.getRowDatasList()) {
				if (eventType == EventType.DELETE) {
					data.put("before", makeColumn(rowData.getBeforeColumnsList()));
				} else if (eventType == EventType.INSERT) {
					data.put("after", makeColumn(rowData.getAfterColumnsList()));
				} else {
					data.put("before", makeColumn(rowData.getBeforeColumnsList()));
					data.put("after", makeColumn(rowData.getAfterColumnsList()));
				}

				Collection<Column> columns = Collections.EMPTY_LIST;
				if (eventType == EventType.INSERT) {
					columns = Collections2.filter(rowData.getAfterColumnsList(), new Predicate<Column>() {
						@Override
						public boolean apply(Column column) {
							return column.getName().contains("ID");
						}
					});
				} else {
					columns = Collections2.filter(rowData.getBeforeColumnsList(), new Predicate<Column>() {
						@Override
						public boolean apply(Column column) {
							return column.getName().contains("ID");
						}
					});
				}

				String rowDataId = ( (Column) ( columns.toArray()[0] ) ).getValue();

				String text = JSONObject.toJSONString(data, SerializerFeature.WriteNonStringValueAsString);
				try {
					metadata =
							(RecordMetadata) producer.send(
									new ProducerRecord<>(topic, SimpleHashPartitionGenerator.getPartition(rowDataId), no, text),
									new Callback() {

										@Override
										public void onCompletion(RecordMetadata metadata, Exception e) {
											if (e != null) {
												logger.error("kafka send message error: ", e);

											} else {
												if (debug > 0) {
													logger.debug("The offset of the record we just sent is: " + metadata.offset());
												}

											}
										}
									}).get();

					if (metadata == null) {
						ret = false;
					}

					if (debug > 0) {
						logger.debug("Send message: (" + topic + "," + no + ", " + text + ")");
					}

				} catch (InterruptedException | ExecutionException e) {
					logger.error("kafka send message error: ", e);
					ret = false;
				}
			}
			data.clear();
			data = null;
		}
		return ret;
	}

	private static List<Map<String, Object>> makeColumn(List<Column> columns) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Column column : columns) {
			Map<String, Object> one = new HashMap<String, Object>();
			one.put("name", column.getName());
			one.put("value", typeProcessor(column.getMysqlType(), column.getValue()));

			one.put("update", column.getUpdated());
			list.add(one);
		}
		return list;
	}

	public static Object typeProcessor(String sqlType, String sqlValue) {
		Object obj = null;

		if (StringUtils.isBlank(sqlValue)) {
			return new String();
		}

		if (StringUtils.startsWithIgnoreCase(sqlType, "BIT"))
			new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TINYINT"))
			new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "BOOL"))
			obj = new Boolean(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "BOOLEAN"))
			obj = new Boolean(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "SMALLINT"))
			obj = new Integer(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "MEDIUMINT"))
			obj = new Integer(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "INT"))
			obj = new Integer(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "INTEGER"))
			obj = new Integer(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "BIGINT"))
			obj = new BigInteger(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "FLOAT"))
			obj = new Float(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "DOUBLE"))
			obj = new Double(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "DECIMAL"))
			obj = new BigDecimal(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "DATE"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "DATETIME"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TIMESTAMP"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TIME"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "YEAR"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "CHAR"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "VARCHAR"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "BINARY"))
			obj = sqlValue.getBytes();
		else if (StringUtils.startsWithIgnoreCase(sqlType, "VARBINARY"))
			obj = sqlValue.getBytes();
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TINYBLOB"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TINYTEXT"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "BLOB"))
			obj = sqlValue.getBytes();
		else if (StringUtils.startsWithIgnoreCase(sqlType, "TEXT"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "MEDIUMBLOB"))
			obj = sqlValue.getBytes();
		else if (StringUtils.startsWithIgnoreCase(sqlType, "MEDIUMTEXT"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "LONGBLOB"))
			obj = sqlValue.getBytes();
		else if (StringUtils.startsWithIgnoreCase(sqlType, "LONGTEXT"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "ENUM"))
			obj = new String(sqlValue);
		else if (StringUtils.startsWithIgnoreCase(sqlType, "SET"))
			obj = new String(sqlValue);
		else {
			obj = new String(sqlValue);
		}

		return obj;
	}

	/**
	* @return the destination
	*/
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the producer
	 */
	public Producer getProducer() {
		return producer;
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	/**
	 * @return the connector
	 */
	public CanalConnector getConnector() {
		return connector;
	}

	/**
	 * @param connector the connector to set
	 */
	public void setConnector(CanalConnector connector) {
		this.connector = connector;
	}

}
