package com.yxw.insurance.biz;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;

public class Test {

	public static void main(String[] args) {
		/*String a = "{\"resultCode\":\"0\",\"resultMessage\":\"success 平台流水号：201844010090081398\"}";

		String b =
				"{\"resultCode\":\"0\",\"resultMessage\":\"success！客户郑瑞亮,证件号：440882198410120053住院申> 报已成功，不能重复申报,请核对!平台流水号：201844010090081398\"}";

		com.yxw.insurance.chinalife.interfaces.vo.response.InsuranceResponse response = JSONObject.parseObject(b, InsuranceResponse.class);
		if (response.getResultCode().equals("0")) {
			String orderNo = response.getResultMessage();
			System.out.println(StringUtils.substringAfter(orderNo, "平台流水号："));
		}*/
		long startTime = System.currentTimeMillis();

		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		try {
			List<String> a = FileUtils.readLines(new File(Test.class.getResource("/A.txt").toURI().getPath().substring(1)), "UTF-8");
			List<String> b = FileUtils.readLines(new File(Test.class.getResource("/B.txt").toURI().getPath().substring(1)), "UTF-8");
			l1.addAll(a);
			l1.addAll(b);

			List<String> c = FileUtils.readLines(new File(Test.class.getResource("/C.txt").toURI().getPath().substring(1)), "UTF-8");
			List<String> d = FileUtils.readLines(new File(Test.class.getResource("/D.txt").toURI().getPath().substring(1)), "UTF-8");
			l2.addAll(c);
			l2.addAll(d);

			System.out.println("l1:" + l1.size());
			System.out.println("l2:" + l2.size());

			int size = l1.size();

			//			for (String s1 : l1) {
			for (int i = 0; i < size; i++) {
				String s1 = l1.get(i);
				List<String> result = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(s1);
				final String temp = result.get(1);
				Collection<String> rel = Collections2.filter(l2, new Predicate<String>() {
					@Override
					public boolean apply(String str) {
						List<String> tls = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(str);
						return tls.get(1).equals(temp);
					}
				});
				if (rel.size() > 1) {
					System.out.println(JSONObject.toJSONString(rel));
				}

				if (rel.size() < 1) {
					System.out.println(JSONObject.toJSONString(s1));
				}

				if (rel.size() == 1) {
					String aa = result.get(2);
					String bb = result.get(3);
					String r = rel.toArray(new String[rel.size()])[0];
					List<String> rs = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(r);
					String cc = rs.get(2);
					String dd = rs.get(3);

					if (StringUtils.equals(aa, cc) && StringUtils.equals(bb, dd)) {

					} else {
						System.out.println(JSONObject.toJSONString(rel));
						System.out.println("+++++++++++++");
						System.out.println(JSONObject.toJSONString(s1));
					}

				}
			}

			/*final String itemName = "酚咖片*";
			Collection<String> rel1 = Collections2.filter(l1, new Predicate<String>() {
				@Override
				public boolean apply(String str) {
					List<String> tls = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(str);
					return tls.get(0).trim().equals(itemName);
				}
			});

			if (!CollectionUtils.isEmpty(rel1)) {
				String newData = rel1.toArray(new String[rel1.size()])[0];

				List<String> res = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(newData);
				final String temp = res.get(1);
				Collection<String> rel = Collections2.filter(l2, new Predicate<String>() {
					@Override
					public boolean apply(String str) {
						List<String> tls = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(str);
						return tls.get(1).trim().equals(temp);
					}
				});

				if (!CollectionUtils.isEmpty(rel)) {
					String data = rel.toArray(new String[rel.size()])[0];
					System.out.println(data);
				}
			}*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("时间毫秒数:" + String.valueOf(System.currentTimeMillis() - startTime));
		/*Set<String> result = Sets.newHashSet();
		result.addAll(Lists.transform(authInfoList, new Function<AuthResultModel.AuthModel, String>() {
		    @Override
		    public String apply(AuthResultModel.AuthModel input) {
		        return input.getCode();
		    }
		}));*/
	}
}
