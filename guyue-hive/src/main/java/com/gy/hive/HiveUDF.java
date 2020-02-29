package com.gy.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lipeng on 15/8/12.
 */
public class HiveUDF extends UDF {

	private String choose   = "choose:";
	private int    c_length = choose.length();

	public String evaluate(String trace) {
		if (null == trace || trace.length() <= 0) {
			return "-1";//chatting records is null.
		}
		StringBuilder stringBuilder = new StringBuilder();
		String[] coordArr = trace.split(";");
		for (String coordItem : coordArr) {
			if (coordItem.indexOf(choose) > -1) {
				if (coordItem.startsWith(choose)) {
					coordItem = coordItem.substring(c_length);
					String[] words = coordItem.split(",");
					if (words.length >= 4) {
						String type = words[2].equals("false") ? "base" : "extra";//选择模式
						int type_index = coordItem.indexOf(words[2]);//
						String alternatives = String.valueOf(words.length - 3);
						String choose_index = words[1];
						stringBuilder.append(choose_index).append(",");
					}
				}
			}
		}
		if (stringBuilder.length() <= 0) {
			return "-3";// can't find label of choose.
		}
		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}

	public static void main(String[] args) {
		HiveUDF udf = new HiveUDF();
		File file = new File("/Users/lipeng/Downloads/choose");
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + udf.evaluate(tempString));
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e1) {
			}
			if (reader != null) {
			}
		}
	}

	//    select emoji,sum(num) from (
	//            select num_arr[0] as emoji , num_arr[1] as num from (
	//            select word,num_, split(num_,',') as num_arr from (
	//    select EmojiNum(word)as num,word from word where cdate=20150601
	//            ) b LATERAL VIEW explode(split(num,'\t')) numtable as num_
	//    ) c
	//    ) d group by emoji;
}
