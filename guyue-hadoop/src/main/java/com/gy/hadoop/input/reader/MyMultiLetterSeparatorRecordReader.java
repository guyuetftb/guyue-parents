package com.gy.hadoop.input.reader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

public class MyMultiLetterSeparatorRecordReader {

	private static Logger logger = Logger.getLogger(MyMultiLetterSeparatorRecordReader.class);

	// private static final int DEFAULT_BUFFER_SIZE = 64 * 1024;
	private static final int DEFAULT_BUFFER_SIZE = 64;
	private static int bufferSize = DEFAULT_BUFFER_SIZE;
	private InputStream in;
	private byte[] buffer;
	private int bufferPosn;
	private int bufferLength;
	private byte[] separator = "END\n".getBytes();

	public MyMultiLetterSeparatorRecordReader(InputStream in) {
		this(in, DEFAULT_BUFFER_SIZE);
	}

	public MyMultiLetterSeparatorRecordReader(InputStream in, Configuration conf) {
		// this(in, conf.getInt("io.file.buffer.size", DEFAULT_BUFFER_SIZE));
		this(in, DEFAULT_BUFFER_SIZE);
	}

	public MyMultiLetterSeparatorRecordReader(InputStream in, int bufferSize) {
		this.in = in;
		MyMultiLetterSeparatorRecordReader.bufferSize = bufferSize;
		this.buffer = new byte[MyMultiLetterSeparatorRecordReader.bufferSize];
	}

	private int readLine(Text str, int maxLineLength, int maxBytesToConsume) throws IOException {
		str.clear();
		Text record = new Text();
		int txtLength = 0;
		long bytesConsumed = 0L;
		boolean newline = false;
		int sepPosn = 0;
		logger.info(" -- BEGIN-READING bufferPosn = " + bufferPosn + ", bufferLength = " + bufferLength);
		int bufferedReadTimes = 0;
		do {
			bufferedReadTimes++;
			logger.info(" -- bufferedReadTimes = " + bufferedReadTimes);
			// 已经读到buffer的末尾了，读下一个buffer
			if (this.bufferPosn >= this.bufferLength) {
				logger.info("  -- bufferPosn 大于 bufferLength 重读缓存.");
				bufferPosn = 0;
				bufferLength = in.read(buffer);
				logger.info("  -- bufferPosn = " + bufferPosn + ", bufferLength = " + bufferLength);
				// 读到文件末尾了，则跳出，进行下一个文件的读取
				if (bufferLength <= 0) {
					logger.info(" -- FILE-END bufferLength = " + bufferLength);
					break;
				}
			}
			int startPosn = this.bufferPosn;
			logger.info(" -- START-CIRCLE bufferPosn = " + bufferPosn + ", startPosn = " + startPosn);
			for (; bufferPosn < bufferLength; bufferPosn++) {
				// 处理上一个buffer的尾巴被切成了两半的分隔符,(如果分隔符中重复字符过多在这里会有问题)
				if (sepPosn > 0 && buffer[bufferPosn] != separator[sepPosn]) {
					logger.info(" -- bufferPosn = " + bufferPosn + ", sepPosn = " + sepPosn + ", 分隔符被切分,分隔符FALSE.");
					// 不是分隔符,则从分隔符的第0个字节开始比较.
					sepPosn = 0;
				}
				// 遇到行分隔符的第一个字符
				if (buffer[bufferPosn] == separator[sepPosn]) {
					logger.info("  -- bufferPosn = " + bufferPosn + ", sepPosn =  " + sepPosn + ", 遇到相等字符.");
					// [第1字符已经比较]
					bufferPosn++;
					sepPosn++;
					int i = 0;
					// 判断接下来的字符是否也是行分隔符中的字符
					for (; sepPosn < separator.length; i++, sepPosn++) {
						// buffer的最后刚好是分隔符，且分隔符被不幸地切成了两半
						if (bufferPosn + i >= bufferLength) {
							logger.info(" -- BREAK[1] bufferPosn = " + bufferPosn + ", i = " + i + ", bufferLength = " + bufferLength + ", 最后刚好是分隔符，且分隔符被不幸地切成了两半");
							bufferPosn += i - 1;
							break;
						}
						logger.info(" -- bufferPosn = " + (this.bufferPosn + i) + "[" + ((char) this.buffer[this.bufferPosn + i]) + "], i = " + i + ", sepPosn = " + sepPosn + "["
								+ ((char) separator[sepPosn]) + "]");
						// 一旦其中有一个字符不相同，就判定为不是分隔符
						if (this.buffer[this.bufferPosn + i] != separator[sepPosn]) {
							logger.info(" --BEEAK[2] bufferPosn = " + bufferPosn + ", i = " + i + ", not equal.");
							sepPosn = 0;
							break;
						}
					}
					// 的确遇到了行分隔符
					if (sepPosn == separator.length) {
						logger.info(" --BEEAK[3-true]. bufferPosn = " + bufferPosn + ", i = " + i + ", 的确遇到了行分隔符, newline=true");
						bufferPosn += i;
						newline = true;
						sepPosn = 0;
						break;
					}
				}
			}
			// 读到数据的长度,里面包含分隔符的长度
			int readLength = this.bufferPosn - startPosn;
			logger.info(" -- bytesConsumed=" + bytesConsumed + ", txtLength=" + txtLength + ", readLength=" + readLength);
			// 累加读到的字节数
			// 1.buffer已读完,一行未读完
			bytesConsumed += readLength;
			// 行分隔符不放入块中
			if (readLength > maxLineLength - txtLength) {
				readLength = maxLineLength - txtLength;
			}
			logger.info(" -- bytesConsumed=" + bytesConsumed + ", txtLength=" + txtLength + ", readLength=" + readLength);
			if (readLength > 0) {
				// 累加读到的数据,如果读取是分多次,则最后一次读取里面包括分隔符
				record.append(this.buffer, startPosn, readLength);
				txtLength += readLength;
				// 去掉记录的分隔符
				if (newline) {
					str.set(record.getBytes(), 0, record.getLength() - separator.length);
				}
			}
			logger.info(" -- bytesConsumed=" + bytesConsumed + ", txtLength=" + txtLength + ", readLength=" + readLength);
		} while (!newline && (bytesConsumed < maxBytesToConsume));
		if (bytesConsumed > (long) Integer.MAX_VALUE) {
			throw new IOException("Too many bytes before newline: " + bytesConsumed);
		}
		logger.info(" -- END-READING bytesConsumed = " + bytesConsumed);
		return (int) bytesConsumed;
	}

	public int readLine(Text str, int maxLineLength) throws IOException {
		return this.readLine(str, maxLineLength, Integer.MAX_VALUE);
	}

	public int readLine(Text str) throws IOException {
		return this.readLine(str, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void close() throws IOException {
		logger.info(" close() - ");
		if (null != in) {
			in.close();
		}
	}
}
