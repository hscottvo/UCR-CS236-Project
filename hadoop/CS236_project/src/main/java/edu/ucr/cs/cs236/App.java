package edu.ucr.cs.cs236;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App {
    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, IntWritable> {
        private Text groupKey = new Text();
        private IntWritable outputValue = new IntWritable();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            // one row in the CSV, in a string
            String currentLine = value.toString();
            // the string split by commas 
            String[] tokens = currentLine.split(",");

            //TODO: Put a string value to group by. Note how the group variable is used in the rest of the function
            // How will you get the group-by value?
            String group = ""; 

            // TODO: Put the column that you are summing
            // Where do you use this? Think about your SQL query that you wrote for PySpark
            String variable_to_sum = "";

            // TODO: Change the condition to filter to make sure you're summing up only the values that you want
            if (true){
                try {
                    // TODO: Parse the correct token into an integer
                    // it is okay to hard-code the index of tokens
                    // Use Integer.parseInt() to do this
                    // int colValue = ____;

                    // TODO: What will the groupKey be? This is how Hadoop knows what to sum together
                    // use `groupKey.set(____);`

                    // TODO: What value will you be pairing with the key? This is the value that Hadoop is summing
                    // outputValue.set(_____);

                    context.write(groupKey, outputValue);
                } catch (NumberFormatException e) {
                    // leave this empty
                }
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                          Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                // TODO: values is an iterable that contains all the values pertaining to the key 
                // How do we get the total value?
                
                // `sum = ____`;
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "get_state_by_pop_2010");
        job.setJarByClass(App.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}