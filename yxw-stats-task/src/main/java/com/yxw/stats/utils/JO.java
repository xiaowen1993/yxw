package com.yxw.stats.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;

public class JO {

	private JSONObject target;

	public JO() {
		this(new JSONObject());
	}

	public JO(String text) {
		this(JSON.parseObject(text, Feature.AllowSingleQuotes, Feature.AllowUnQuotedFieldNames));
	}

	public JO(Map<String, Object> map) {
		this(new JSONObject(map));
		// this.target.putAll(map);
	}

	public JO(JSONObject jsonObject) {
		this.target = jsonObject;
	}

	public JSONObject myJSONObject() {
		return this.target;
	}

	public JSONObject getMyJSONObject() {
		return this.target;
	}

	public Set<String> keySet() {
		return this.target.keySet();
	}

	public JO putJO(String key) {
		// if (target.containsKey(key)) {
		// target.remove(key);
		// }
		JSONObject value = new JSONObject();
		this.target.put(key, value);
		return new JO(value);
	}

	public JO putJO(String key, String json) {
		return putJO(key, new JO(json));
	}

	public JO putJO(String key, Map<String, Object> map) {
		return putJO(key, new JO(map));
	}

	public JO putJO(String key, JO jo) {
		// if (target.containsKey(key)) {
		// target.remove(key);
		// }
		JSONObject value = jo.myJSONObject();
		this.target.put(key, value);
		return jo;
	}

	public JO O(String jpath) {
		return getJO(jpath);
	}

	public byte[] B(String jpath) {
		return getBytes(jpath);
	}

	public String S(String jpath) {
		return getStr(jpath);
	}

	public Date D(String jpath) {
		return getDate(jpath);
	}

	public int I(String jpath) {
		return getInt(jpath);
	}

	public float F(String jpath) {
		return getFloat(jpath);
	}

	private <T> Object getLObject(JSONObject root, String jpath, Class<T> cls) {

		if (root == null) {
			return null;
		}

		String[] names = jpath.split("\\.");
		String key = names[0];

		JSONObject jobj = null;
		Object result = null;

		if (key.matches("^.+(\\[\\d+\\])+$")) {

			String[] tmps = key.replaceAll("\\]", "").split("\\[");

			JSONArray jarray = root.getJSONArray(tmps[0]);

			if (jarray != null) {
				for (int j = 1; j < tmps.length; j++) {

					int index = Integer.parseInt(tmps[j]);
					if (j == tmps.length - 1) {
						if (names.length == 1) {
							result = jarray.getObject(index, cls);
						} else {
							jobj = jarray.getJSONObject(index);
							result = getLObject(jobj, jpath.substring(key.length() + 1), cls);
						}
					} else {
						jarray = jarray.getJSONArray(index);
					}
				}
			}

		} else {

			if (names.length == 1) {
				result = root.getObject(key, cls);
			} else {
				jobj = root.getJSONObject(key);
				result = getLObject(jobj, jpath.substring(key.length() + 1), cls);
			}

		}

		return result;

	}

	public JO getJO(String jpath) {
		JSONObject value = (JSONObject) getLObject(this.target, jpath, JSONObject.class);
		if (value == null) {
			return null;
		}
		return new JO(value);
	}

	public boolean getBool(String jpath) {
		return getBool(jpath, false);
	}

	public boolean getBool(String jpath, boolean def) {
		try {
			Object value = getLObject(this.target, jpath, Boolean.class);
			if (value == null) {
				return def;
			}
			return TypeUtils.castToBoolean(value).booleanValue();
		} catch (Exception e) {
			return def;
		}
	}

	public String getStr(String jpath) {
		return getStr(jpath, null);
	}

	public String getStr(String jpath, String def) {
		try {
			Object value = getLObject(this.target, jpath, String.class);
			if (value == null) {
				return def;
			}
			return TypeUtils.castToString(value);
		} catch (Exception e) {
			return def;
		}
	}

	public int getInt(String jpath) {
		return getInt(jpath, 0);
	}

	public int getInt(String jpath, int def) {
		try {
			Object value = getLObject(this.target, jpath, Integer.class);
			if (value == null) {
				return def;
			}
			return TypeUtils.castToInt(value).intValue();
		} catch (Exception e) {
			return def;
		}
	}

	public float getFloat(String jpath) {
		return getFloat(jpath, 0);
	}

	public float getFloat(String jpath, float def) {
		try {
			Object value = getLObject(this.target, jpath, Double.class);
			if (value == null) {
				return def;
			}
			return TypeUtils.castToDouble(value).floatValue();
		} catch (Exception e) {
			return def;
		}
	}

	public Date getDate(String jpath) {
		Object value = getLObject(this.target, jpath, Date.class);
		if (value == null) {
			return null;
		}
		return TypeUtils.castToDate(value);
	}

	public byte[] getBytes(String jpath) {
		Object value = getLObject(this.target, jpath, Object.class);
		if (value == null) {
			return null;
		}
		return TypeUtils.castToBytes(value);
	}

	public JSONObject getJObject(String jpath) {
		JSONObject v = (JSONObject) getLObject(this.target, jpath, JSONObject.class);
		return v;
	}

	public JSONArray getJArray(String jpath) {
		JSONArray v = (JSONArray) getLObject(this.target, jpath, JSONArray.class);
		return v;
	}

	public JSONArray addArray(String key, Object[] array) {
		JSONArray ja = target.getJSONArray(key);
		if (ja == null) {
			ja = new JSONArray();
			this.target.put(key, ja);
		}
		for (int i = 0; i < array.length; i++) {
			ja.add(array[i]);
		}
		return ja;
	}

	public JSONArray addJOArray(String key, JO[] array) {
		JSONArray ja = target.getJSONArray(key);
		if (ja == null) {
			ja = new JSONArray();
			this.target.put(key, ja);
		}
		for (int i = 0; i < array.length; i++) {
			ja.add(array[i].myJSONObject());
		}
		return ja;
	}

	public JSONArray addArrays(String key, Object... objects) {
		JSONArray ja = target.getJSONArray(key);
		if (ja == null) {
			ja = new JSONArray();
			this.target.put(key, ja);
		}
		for (Object Object : objects) {
			ja.add(Object);
		}
		return ja;
	}

	public JSONArray addJOArrays(String key, JO... jos) {
		JSONArray ja = target.getJSONArray(key);
		if (ja == null) {
			ja = new JSONArray();
			this.target.put(key, ja);
		}
		for (JO jo : jos) {
			ja.add(jo.myJSONObject());
		}
		return ja;
	}

	public JSONArray addJSONArray(String key) {
		JSONArray ja = new JSONArray();
		this.target.put(key, ja);
		return ja;
	}

	public JSONArray addJSONArray(String key, JSONArray ja) {
		this.target.put(key, ja);
		return ja;
	}

	public List<Object> getArray(String jpath) {
		return getJArray(jpath);
	}

	public List<JO> getJOArray(String jpath) {

		JSONArray jsonArray = (JSONArray) getLObject(this.target, jpath, JSONArray.class);

		List<JO> jsonList = new ArrayList<JO>();

		Iterator<Object> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {

			JSONObject jsonItem = (JSONObject) iterator.next();
			JO value = new JO(jsonItem);
			jsonList.add(value);

		}

		return jsonList;

	}

	public void put(String key, Object value) {
		this.target.put(key, value);
	}

	public void putAll(Map<String, Object> map) {
		this.target.putAll(map);
	}

	public void remove(String key) {
		this.target.remove(key);
	}

	public boolean containsKey(String key) {
		return this.target.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.target.containsValue(value);
	}

	public String toString() {
		return toJsonString();
	}

	public String toJsonString(SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();

		try {
			JSONSerializer serializer = new JSONSerializer(out);
			for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
				serializer.config(feature, true);
			}

			serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
			serializer.setDateFormat("yyyy-MM-dd HH:mm:ss");

			serializer.config(SerializerFeature.WriteTabAsSpecial, true);
			serializer.config(SerializerFeature.WriteSlashAsSpecial, true);

			serializer.write(this.target);

			return out.toString();
		} finally {
			out.close();
		}
	}

	public String toFormatedString() {
		return toJsonString(SerializerFeature.PrettyFormat);
	}

	public String toJavascriptString() {
		return toJsonString(SerializerFeature.UseSingleQuotes, SerializerFeature.BrowserCompatible);
	}

	public static void main(String[] args) throws Exception {

		// 创建 JO对象：

		// 创建空的JO对象
		JO jo = new JO();
		System.out.println(jo);
		System.out.println(jo.getStr("a.b"));
		// 从json字符串创建 JO对象
		jo = new JO("{\"age\":18,\"name\":\"clean\"}");// 标准json
		System.out.println(jo);
		jo = new JO("{'age':18,'name':'clean'}");// javascript格式
		System.out.println(jo);
		jo = new JO("{age:18,name:'clean'}");// name无引号格式
		System.out.println(jo);

		// 从Map<String, Object>创建 JO对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "clean");
		map.put("age", 18);
		jo = new JO(map);
		System.out.println(jo);

		// JO中写值
		jo = new JO();

		// 直接写
		jo.put("name", "clean");
		jo.put("age", 18);
		System.out.println(jo);
		// 从Map<String, Object>导入
		jo = new JO();
		jo.putAll(map);
		System.out.println(jo);

		jo = new JO("{'age':18,'name':'clean'}");
		JO jo2 = new JO("{age:16,name:'angle'}");
		jo.putJO("sister", jo2);
		System.out.println(jo);

		jo = new JO("{'age':18,'name':'clean'}");
		jo2 = jo.putJO("sister");
		jo2.put("name", "angle");
		jo2.put("age", 16);
		System.out.println(jo);

		// 数组

		jo = new JO();
		jo.addArray("names", new String[] { "clean", "angle", "10086" });
		System.out.println(jo);

		jo = new JO();
		jo.addArrays("names", "clean" + "\\" + "\t");
		jo.addArrays("names", "angle", 10086);
		System.out.println(jo);

		jo = new JO();
		// List<Object> names = jo.addJSONArray("names");
		// 或jo.addJSONArray("names"); List<Object> names = jo.getArray("names");

		List<Object> names = jo.addJSONArray("names");
		names.add("clean");
		names.add("angle");
		names.add(10086);
		System.out.println(jo);

		jo = new JO();
		List<Object> girls = jo.addJSONArray("girls");
		girls.add(new JO("{'age':18,'name':'clean'}").myJSONObject());
		girls.add(new JO("{age:16,name:'angle'}").myJSONObject());
		girls.add(10086);
		System.out.println(jo);

		jo = new JO();
		jo.addJOArrays("girls", new JO("{'age':18,'name':'clean'}"), new JO("{age:16,name:'angle'}"));
		System.out.println(jo);

		jo.addJOArrays("girls", new JO("{'age':-1,'name':'unknow'}"));
		System.out.println(jo);

		// 读取JPATH

		System.out.println(new JO((JSONObject) jo.getArray("girls").get(0)).getStr("name"));
		System.out.println( ( (JSONObject) jo.getArray("girls").get(0) ).get("name"));
		System.out.println(jo.S("girls[0].name"));
		jo.getJO("girls[0]").putJO("brother", new JO("{name:'tom',age:15}"));
		jo.getJO("girls[1]").putJO("brother", new JO("{name:'peter',age:23}"));

		System.out.println(jo.S("girls[1].brother.name"));
		System.out.println(jo.getInt("girls[1].brother.age"));

		// 格式化
		System.out.println(jo.toFormatedString());
		System.out.println(jo.toJavascriptString());

		System.out.println(JSON.toJSONString(jo.toFormatedString()));

		// 时间日期
		jo = new JO();
		jo.put("now", new Date());
		System.out.println(jo);

		// 字节流
		jo = new JO();
		jo.put("B", "我是中文".getBytes());
		System.out.println(jo);
		System.out.println(new String(jo.getBytes("B")));

		jo = new JO();
		System.out.println("错误的JPATH:" + jo.getBytes("S.s[1]"));
	}
}
