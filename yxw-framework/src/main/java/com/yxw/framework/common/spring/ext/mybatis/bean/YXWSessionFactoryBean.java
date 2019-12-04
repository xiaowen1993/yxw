/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年4月29日
 * @version 1.0
 */
package com.yxw.framework.common.spring.ext.mybatis.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年4月29日  
 */
public class YXWSessionFactoryBean extends SqlSessionFactoryBean {

	private static final Log logger = LogFactory.getLog(YXWSessionFactoryBean.class);

	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	private Interceptor[] plugins;
	private Class<?>[] typeAliases;
	private String typeAliasesPackage;
	private TypeHandler<?>[] typeHandlers;
	private String typeHandlersPackage;
	private TransactionFactory transactionFactory;
	private Properties configurationProperties;
	private Resource[] configLocations;
	private DataSource dataSource;
	private String environment = SqlSessionFactoryBean.class.getSimpleName();
	private DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
	private Resource[] mapperLocations;
	private SqlSessionFactory sqlSessionFactory;

	public void setConfigLocation(Resource configLocation) {

		this.configLocations = configLocation != null ? new Resource[] { configLocation } : null;
	}

	public void setConfigLocations(Resource[] configLocations) {

		this.configLocations = configLocations;
	}

	public Document SQLConfigMap() {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		DocumentFactory documentFactory = new DocumentFactory();
		DocumentType docType =
				documentFactory.createDocType("configuration", "-//mybatis.org//DTD Config 3.0//EN", "http://mybatis.org/dtd/mybatis-3-config.dtd");
		doc.setDocType(docType);
		Element rootElement = doc.addElement("configuration");
		rootElement.addElement("typeAliases");
		rootElement.addElement("mappers");
		return doc;
	}

	public void readXML(Resource configXML, final Element elementTypeAlias, final Element elementMapper) {
		SAXReader saxReader = new SAXReader();
		saxReader.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				String jarPath = SqlSessionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String filePath = "org/apache/ibatis/builder/xml/mybatis-3-config.dtd";
				InputStream jarIn = null;
				try {
					JarFile jarFile = new JarFile(jarPath);
					JarEntry jarEntry = jarFile.getJarEntry(filePath);
					jarIn = jarFile.getInputStream(jarEntry);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return new InputSource(jarIn);
			}
		});

		saxReader.addHandler("/configuration/typeAliases/typeAlias", new ElementHandler() {
			public void onEnd(ElementPath path) {
				Element row = path.getCurrent();
				Element els = elementTypeAlias.addElement("typeAlias");
				els.addAttribute("alias", row.attributeValue("alias")).addAttribute("type", row.attributeValue("type"));
				row.detach();
			}

			public void onStart(ElementPath arg0) {
			}

		});

		saxReader.addHandler("/configuration/mappers/mapper", new ElementHandler() {

			public void onEnd(ElementPath path) {
				Element row = path.getCurrent();
				Element els = elementMapper.addElement("mapper");
				String mapper = row.attributeValue("mapper");
				String resource = row.attributeValue("resource");
				els.addAttribute("mapper", mapper);
				els.addAttribute("resource", resource);
				row.detach();
			}

			public void onStart(ElementPath arg0) {

			}

		});

		try {
			saxReader.read(configXML.getInputStream());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		Configuration configuration = null;
		XMLConfigBuilder xmlConfigBuilder = null;
		Document document = this.SQLConfigMap();
		Element root = document.getRootElement();
		Element elementMapper = root.element("mappers");
		Element elementTypeAlias = root.element("typeAliases");
		for (Resource configLocation : configLocations) {
			readXML(configLocation, elementTypeAlias, elementMapper);
		}
		// Reader reader = null; InputStream inputStream = null;
		if (this.configLocations != null) {
			logger.debug(document.asXML());
			InputStream inputSteam = new ByteArrayInputStream(document.asXML().getBytes());
			xmlConfigBuilder = new XMLConfigBuilder(inputSteam, null, this.configurationProperties);
			configuration = xmlConfigBuilder.getConfiguration();
			if (inputSteam != null) {
				inputSteam.close();
				inputSteam = null;
			}
			document = null;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Property 'configLocation' not specified,using default MyBatis Configuration");
			}
			configuration = new Configuration();
			configuration.setVariables(this.configurationProperties);
		}

		if (StringUtils.hasLength(this.typeAliasesPackage)) {
			String[] typeAliasPackageArray =
					StringUtils.tokenizeToStringArray(this.typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeAliasPackageArray) {
				configuration.getTypeAliasRegistry().registerAliases(packageToScan);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan + "' for aliases");
				}
			}
		}
		if (!ObjectUtils.isEmpty(this.typeAliases)) {
			for (Class<?> typeAlias : this.typeAliases) {
				configuration.getTypeAliasRegistry().registerAlias(typeAlias);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type alias: '" + typeAlias + "'");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.plugins)) {
			for (Interceptor plugin : this.plugins) {
				configuration.addInterceptor(plugin);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered plugin: '" + plugin + "'");
				}
			}
		}

		if (StringUtils.hasLength(this.typeHandlersPackage)) {
			String[] typeHandlersPackageArray =
					StringUtils.tokenizeToStringArray(this.typeHandlersPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeHandlersPackageArray) {
				configuration.getTypeHandlerRegistry().register(packageToScan);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.typeHandlers)) {
			for (TypeHandler<?> typeHandler : this.typeHandlers) {
				configuration.getTypeHandlerRegistry().register(typeHandler);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type handler: '" + typeHandler + "'");
				}
			}
		}

		if (xmlConfigBuilder != null) {
			try {
				xmlConfigBuilder.parse();

				if (logger.isDebugEnabled()) {
					logger.debug("Parsed configuration file: '" + this.configLocations + "'");
				}
			} catch (Exception ex) {
				throw new NestedIOException("Failed to parse config resource: " + this.configLocations, ex);
			} finally {
				ErrorContext.instance().reset();
			}
		}

		if (this.transactionFactory == null) {
			this.transactionFactory = new SpringManagedTransactionFactory();
		}

		Environment environment = new Environment(this.environment, this.transactionFactory, this.dataSource);
		configuration.setEnvironment(environment);

		if (this.databaseIdProvider != null) {
			try {
				configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
			} catch (SQLException e) {
				throw new NestedIOException("Failed getting a databaseId", e);
			}
		}

		if (!ObjectUtils.isEmpty(this.mapperLocations)) {
			for (Resource mapperLocation : this.mapperLocations) {
				if (mapperLocation == null) {
					continue;
				}

				try {
					XMLMapperBuilder xmlMapperBuilder =
							new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(),
									configuration.getSqlFragments());
					xmlMapperBuilder.parse();
				} catch (Exception e) {
					throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
				} finally {
					ErrorContext.instance().reset();
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Parsed mapper file: '" + mapperLocation + "'");
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
			}
		}
		return this.sqlSessionFactoryBuilder.build(configuration);
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSource, "Property 'dataSource' is required");
		Assert.notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
		this.sqlSessionFactory = buildSqlSessionFactory();
	}

	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			afterPropertiesSet();
		}

		return this.sqlSessionFactory;
	}

	public SqlSessionFactoryBuilder getSqlSessionFactoryBuilder() {
		return sqlSessionFactoryBuilder;
	}

	public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
		this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

	public void setTypeAliases(Class<?>[] typeAliases) {
		this.typeAliases = typeAliases;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	public void setConfigurationProperties(Properties configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			this.dataSource = ( (TransactionAwareDataSourceProxy) dataSource ).getTargetDataSource();
		} else {
			this.dataSource = dataSource;
		}
	}
}
