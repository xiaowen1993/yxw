/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年7月18日
 * @version 1.0
 */
package com.yxw.utils;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年7月18日  
 */
public class Test {

	public static void main(String[] args) throws Exception {
		/*		Set<String> result1 = Sets.union(Test3.ls1, Test3.ls2);
				Set<String> result2 = Sets.union(Test4.ls3, Test4.ls4);

				SetView<String> intersection = Sets.difference(result2, result1);

				for (String string : intersection) {
					System.out.println(string);
				}*/

		//		Set<String> result = Sets.union(result1, result2);
		/*		for (String i : result) {
					System.out.println(Math.abs(i.hashCode() & 0xff) % 8);
				}*/
		//		System.out.println( ( ( ( "a89f3b785148445b8b7f99474a8b378a".hashCode() * 2654435769L ) >> 28 ) & 0xff ) % 8);

		/*Set<String> s1 = Sets.union(Test1.ls1, Test1.ls2);
		Set<String> s2 = Sets.union(Test2.ls3, Test2.ls4);
		SetView<String> intersection = Sets.difference(s2, s1);

		for (String string : intersection) {
			System.out.println(string);
		}*/
		/*List<String> dateStr =
				Lists.newArrayList("2018-01-10 18:11:01", "2018-01-10 18:12:45", "2018-01-15 17:22:25", "2018-01-15 17:46:59",
						"2018-01-23 15:19:05", "2018-01-26 17:35:08", "2018-01-26 17:43:28");
		for (String STR : dateStr) {

			System.out.println(DateUtils.StringToDate(STR).getTime());
		}*/

		/*try {
			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			String path = "";
			if (os_name.indexOf("windows") != -1) {
				path = InitDataServlet.class.getResource("/ICD1.json").toURI().getPath().substring(1);
			} else if (os_name.indexOf("linux") != -1) {
				path = InitDataServlet.class.getResource("/ICD1.json").toURI().getPath();
			}
			String text = FileUtils.readFileToString(new File(path), "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(text);
			ICDConfigMapVals = jsonObject.getJSONArray("list");

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		List<Object> ICDs = InitDataServlet.ICDConfigMapVals;

		Set<Object> set = Sets.newLinkedHashSet(ICDs);

		System.out.println(ICDs.size() - set.size());

		List<String> results = Lists.newLinkedList();
		for (Multiset.Entry<Object> entry : HashMultiset.create(ICDs).entrySet()) {
			System.out.println(JSONObject.toJSONString(entry, true));
			if (entry.getCount() > 1) {
				results.add(entry.getElement().toString());
			}
		}

		System.out.println(JSONObject.toJSONString(results, true));*/

		/*		String a = com.yxw.utils.StringUtils.cent2Yuan("", false);
				System.out.println(a);*/

	}
}
