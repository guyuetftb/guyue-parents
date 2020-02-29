package com.amazon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * 
 * @author daniel
 * 
 *         这个类里面我们是用来专门读取数据的
 * 
 * 
 */
public class AmazonDataReader extends RecordReader<Text, Text> {

	private Text key;
	private Text value;
	private BufferedReader reader;
	private int count = 0;
	private String line = null;
	private StringBuilder currentLineData = null;
	private FileSystem fileSystem = null;
	private static Pattern pattern1 = Pattern
			.compile("\\s+([^\\s]+)\\s+cutomer:\\s+([^\\s]+)\\s+rating:\\s+([^\\s]+)\\s+votes:\\s+([^\\s]+)\\s+helpful:\\s+([^\\s]+).*");

	public AmazonDataReader() {

	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public Text getCurrentKey() {
		return this.key;
	}

	@Override
	public Text getCurrentValue() {
		return this.value;
	}

	@Override
	public float getProgress() {
		return count; // 这个用于读取进度
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException {
		// 首先我们肯定会初始化这个方法,
		Path path = ((FileSplit) split).getPath(); // 这个是获取我们的文件路径
		if (fileSystem == null) {
			fileSystem = FileSystem.get(context.getConfiguration());
		}
		// 获取到hdfs系统api
		FSDataInputStream open = fileSystem.open(path); // 使用hdfs读取文件流
		reader = new BufferedReader(new InputStreamReader(open)); // 读取输入流供下面使用
		// 然后我们需要读取信息
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("Id:"))
				break;
		}
	}

	@Override
	public boolean nextKeyValue() throws IOException {
		// 上面初始化了之后,这一步的操作才是最关键的
		count++; // 首先我们代表进来读取了一次了
		currentLineData = new StringBuilder();
		boolean readingView = false; // 我们在那里面
										// 一定要做的是就是要一直往下读取,知道读取到reviews这个属性的位置,我们才能拿到我们想要的数据
		while ((line = reader.readLine()) != null) {
			// System.out.println("读进来了数据:" + line);
			if (line.trim().length() == 0) {
				// 这里代表没有读取到数据,那么就代表这一段数据我们读取完成了,需要提交一次
				value = new Text(currentLineData.toString());
				return true;
			}

			// 下面代表读取到了数据,那么我们就在这里做数据清洗,首先说明下下面是什么意思,首先我们读取的时候肯定有很多数据他没有包含在这个reviews的内容下,所以我们也需要进行处理
			if (readingView) {
				// 这里代表上一行的数据我们读取到了reviews
				Matcher matcher = pattern1.matcher(line);
				if (matcher.matches()) {
					// customer, voting, rates, helpful
					currentLineData.append("review=").append(matcher.group(2))
							.append("|").append(matcher.group(3)).append("|")
							.append(matcher.group(4)).append("|")
							.append(matcher.group(5)).append("#");
				} else {
					// 这里是不匹配,所以不进行操作
				}

			} else {
				// 这里代表上一行的数据里面没有读取到reviews
				/**
				 * Id: 3 ASIN: 0486287785 title: World War II Allied Fighter
				 * Planes Trading Cards group: Book salesrank: 1270652 similar:
				 * 0 categories: 1 |Books[283155]|Subjects[1000]|Home &
				 * Garden[48]|Crafts & Hobbies[5126]|General[5144]
				 */
				// 这个下面读取到得内容是上面这一段
				int indexOf = line.indexOf(":");
				if (indexOf > 0) {
					// 读取到得时Books上面的东西
					String key = line.substring(0, indexOf).trim();
					String value = line.substring(indexOf + 1).trim();
					if (key.isEmpty() || value.isEmpty())
						continue;
					// 当然,我们如果是第一条记录的话,我们需要处理一下
					if (value.indexOf("#") > 0)
						value.replaceAll("#", "_");

					if (key.equals("Id") || key.equals("ASIN")
							|| key.equals("title") || key.equals("group")
							|| key.equals("salesrank")) {
						if (key.equals("ASIN")) {
							this.key = new Text(value);
						}
						currentLineData.append(key).append("=")
								.append(value.replaceAll(",", "")).append("#");

					} else if (key.equals("similar")) {
						// 因为这个里面的数据跟之前是不一样的,所以我们要拿出来单独操作
						String[] tokens = value.split("\\s+");
						if (tokens.length > 2) {
							// 在similar里面 第一个数是相似度,第二个值是自己,所以我们一定要查找到有3个数据以上的东西
							currentLineData.append(key).append("=");
							for (int i = 1; i < tokens.length; i++) {
								currentLineData.append(tokens[i].trim())
										.append("|");
							}
							currentLineData.append("#");
						}
					} else if (key.equals("reviews")) {
						readingView = true;
					}
				} else {
					// 读到了Books的位置了
				}
			}
		}
		return false;
	}
}
