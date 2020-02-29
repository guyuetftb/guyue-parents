package com.gy.hadoop.join.v1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.gy.util.ConfigurationUtils;
import com.gy.util.HdfsUtils;

public class MyStatusForwardDataJoin extends Configured implements Tool {

	public static class MapClass extends DataJoinMapperBase {

		/**
		 * forward-file:status_forward-2013-09-24_00018
		 * 
		 */
		public final static Logger logger = Logger.getLogger(MapClass.class);
		private final static Text key = new Text();
		private final static Text dataTag = new Text("data");

		/**
		 * step 0.<br/>
		 * called only one times.
		 */
		@Override
		protected Text generateInputTag(String inputFile) {
			String[] filePathArr = inputFile.split("/");
			String type = filePathArr[filePathArr.length - 1];
			logger.info("step 0. [ generateInputTag. input-file=" + inputFile + ",type=" + type);
			return new Text(type);
		}

		/**
		 * step 1.<br/>
		 * create value.
		 */
		@Override
		protected TaggedMapOutput generateTaggedMapOutput(Object value) {
			TaggedWritable retv = new TaggedWritable((Text) value);
			retv.setTag(this.inputTag);
			logger.info("step 1. [ generateTaggedMapOutput. value=" + value.toString());
			return retv;
		}

		/**
		 * step 2.<br/>
		 * create key.
		 */
		@Override
		protected Text generateGroupKey(TaggedMapOutput aRecord) {
			String line = aRecord.getData().toString();
			String groupKey = null;
			String[] dataArr = line.split("\\|");
			logger.info(" length = " + dataArr.length + ", type=[" + inputTag + "] --> " + (inputTag.equals(dataTag)));
			if (inputTag.equals(dataTag)) {
				groupKey = dataArr[1];
			} else {
				groupKey = dataArr[0];
			}
			key.set(groupKey);
			logger.info("step 2. [ generateGroupKey.groupKey=" + groupKey);
			return key;
		}
	}

	public static class Reduce extends DataJoinReducerBase {

		public final static Logger logger = Logger.getLogger(Reduce.class);

		TaggedWritable retv = new TaggedWritable("");
		private final static Text dataTag = new Text("data");
		private final static StringBuilder result = new StringBuilder();

		protected TaggedMapOutput combine(Object[] tags, Object[] values) {
			// clean content.
			result.delete(0, result.length());

			String tmp = null;
			logger.info(" combine. args.length=" + tags.length + ", values.length=" + values.length);
			for (int index = 0; index < tags.length; index++) {
				// obtain 'Tag'
				Text tag = (Text) tags[index];

				// obtain 'Data'
				TaggedWritable tw = (TaggedWritable) values[index];
				String[] dataArr = tw.getData().toString().split("\\|");

				// log 'Tag' and 'Data'
				logger.info(" combine. tag=" + tag + ",data=" + tw.getData());
				if (tag.equals(dataTag)) {
					result.append(dataArr[0]).append("\t");
					tmp = dataArr[2];
				} else {
					result.append(dataArr[1]).append("\t");
					result.append(tmp);
				}
			}
			logger.info(" combine. result=" + result.toString());
			retv.setData(new Text(result.toString()));
			return retv;
		}

		private void clear() {
		}

		private void generateOld(String old) {
		}
	}

	public static class TaggedWritable extends TaggedMapOutput {
		private Writable data;

		public TaggedWritable() {
			this.tag = new Text();
		}

		public TaggedWritable(String data) {
			this.tag = new Text("");
			this.data = new Text(data);
		}

		public TaggedWritable(Writable data) {
			this.tag = new Text("");
			this.data = data;
		}

		public Writable getData() {
			return data;
		}

		public void setData(Writable data) {
			this.data = data;
		}

		public void reSetData(String d) {
			if (null == this.data) {
				this.data = new Text(d);
			} else {
				((Text) this.data).set(d);
			}
		}

		@Override
		public void write(DataOutput out) throws IOException {
			this.tag.write(out);
			out.writeUTF(this.data.getClass().getName());
			this.data.write(out);
		}

		@Override
		public void readFields(DataInput in) throws IOException {
			this.tag.readFields(in);
			String dataClz = in.readUTF();
			if (this.data == null || !this.data.getClass().getName().equals(dataClz)) {
				try {
					this.data = (Writable) ReflectionUtils.newInstance(Class.forName(dataClz), null);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			this.data.readFields(in);
		}
	}

	public int run(String[] args) throws Exception {
		String host = args[0];
		String port = args[1];
		String input = args[2];
		String output = args[3];

		Configuration conf = ConfigurationUtils.createConfiguratoin(host, port);
		JobConf job = new JobConf(conf, MyStatusForwardDataJoin.class);

		job.setJobName("MyStatusForwardDataJoin");
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);

		// input
		FileInputFormat.setInputPaths(job, input);

		// output
		HdfsUtils.deleteFile(conf, output);
		Path outputPath = new Path(output);
		FileOutputFormat.setOutputPath(job, outputPath);

		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TaggedWritable.class);
		job.set("mapred.textoutputformat.separator", ",");

		JobClient.runJob(job);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new MyStatusForwardDataJoin(), args);
		System.exit(res);
	}
}