package com.gy.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lipeng on 15/8/12.
 */
public class HiveSpaceUDF extends UDF {

	private String choose   = "choose:";
	private int    c_length = choose.length();

	public Long evaluateBak(String trace) {
		if (null == trace || trace.length() <= 0) {
			return -1l;//chatting records is null.
		}
		Long result = new Long(0);
		String[] coordArr = trace.split(";");
		for (int index = 0; index < coordArr.length; index++) {
			/**
			 * current
			 */
			String[] words = coordArr[index].split(",");
			if (words.length >= 4) {
				if (words[3].equals(" ")) {

					/**
					 * next
					 */
					int next = index + 1;
					if (next < coordArr.length) {
						String[] nextWords = coordArr[next].split(",");
						if (nextWords.length >= 4) {
							if (nextWords[3].equals("delete")) {
								continue;
							} else {
								result++;
							}
						}
					} else {
						result++;
					}
				}
				//                stringBuilder.append(choose_index).append(",");
			}
		}
		if (result.longValue() <= 0) {
			return -3l;// can't find label of space.
		}
		return result;
	}

	public String evaluate(String trace) {
		if (null == trace || trace.length() <= 0) {
			return "null";//chatting records is null.
		}
		String[] coordArr = trace.split(";");
		for (String coordItem : coordArr) {
			if (coordItem.indexOf(choose) > -1 && coordItem.startsWith(choose)) {
				return "choose";
			}
		}

		Long result = new Long(0);
		for (int index = 0; index < coordArr.length; index++) {
			/**
			 * current
			 */
			String[] words = coordArr[index].split(",");
			if (words.length >= 4) {
				if (words[3].equals(" ")) {

					/**
					 * next
					 */
					int next = index + 1;
					if (next < coordArr.length) {
						String[] nextWords = coordArr[next].split(",");
						if (nextWords.length >= 4) {
							if (nextWords[3].equals("delete")) {
								continue;
							} else {
								result++;
							}
						}
					} else {
						result++;
					}
				}
				//                stringBuilder.append(choose_index).append(",");
			}
		}
		if (result.longValue() <= 0) {
			return "0";// can't find label of space.
		}
		if (result.longValue() == 270) {
			return trace;
		}
		return "other";
	}

	public static void main(String[] args) {
		HiveSpaceUDF udf = new HiveSpaceUDF();
		File file = new File("/Users/lipeng/workspace/space_20150812");
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
