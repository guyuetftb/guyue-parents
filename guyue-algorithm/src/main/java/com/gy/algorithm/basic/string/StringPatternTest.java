package com.gy.algorithm.basic.string;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @ClassName StringPatternTest
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-04-08 17:30
 */
public class StringPatternTest {


	public static void main(String[] args) {
		String[] patterns = new String[]{"abba", "aabb", "abc", "acac"};
		String[] locations = new String[]{"北京,杭州,杭州,北京", "北京,杭州,杭州,北京", "北京,杭州,杭州,南京", "北京,杭州,北京,广州"};

		for (int i = 0; i < patterns.length; i++) {
			System.out.println(patterns[i] + ", " + locations[i] + ", 是否匹配 = " + isPair(patterns[i], locations[i]));
		}
	}

	public static boolean isPair(String pattern, String location) {
		LinkedHashMap<String, String> pairMap = new LinkedHashMap<>();
		String[] patternArr = pattern.split("");
		String[] locationArr = location.split(",");
		// 假如分隔的长度不同, 直接返回 false
		if (patternArr.length != locationArr.length) {
			return false;
		}

		boolean pair = true;
		for (int index = 0; index < patternArr.length; index++) {

			String pat = patternArr[index];
			String loc = locationArr[index];
			if (!pairMap.containsKey(pat)) {
				pairMap.put(pat, loc);
				continue;
			}

			if (pairMap.get(pat).equals(loc)) {
				continue;
			}
			pair = false;
			break;
		}
		return pair;
	}

}
