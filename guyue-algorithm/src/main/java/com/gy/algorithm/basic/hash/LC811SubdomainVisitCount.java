package com.gy.algorithm.basic.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC811SubdomainVisitCount {

    public static void main(String[] args) {
        LC811SubdomainVisitCount a = new LC811SubdomainVisitCount();
        a.subdomainVisits(new String[]{"9001 discuss.leetcode.com"});
    }

    public List<String> subdomainVisits(String[] domains) {

        Map<String, Integer> domainFre = new HashMap<String, Integer>();

        for (String domain : domains) {

            // 字符串长度
            int length = domain.length();

            //
            int idx = domain.indexOf(" ");

            // 频率数
            int cnt = Integer.parseInt(domain.substring(0, idx));

            int startIndex = idx + 1;
            idx = length - 1;
            while (idx >= startIndex) {

                // 从后向前 逐个判断字符, 遇到 '.' 就代表 是一个域名
                while (idx >= startIndex && domain.charAt(idx) != '.') {
                    idx--;
                }

                // 截取 域名, 并对域名次数做累加
                String subDomain = domain.substring(idx + 1);
                domainFre.put(subDomain, domainFre.getOrDefault(subDomain, 0) + cnt);
                // 指针 左移
                idx--;
            }
        }


        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : domainFre.entrySet()) {
            result.add(entry.getValue() + " " + entry.getKey());
        }
        return result;
    }
}
