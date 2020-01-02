package org.myorg.quickstart.source.fileSource;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TextFileSource {

    public static DataStreamSource<String> flinkReadTxtFile(StreamExecutionEnvironment environment,String filePath){
        DataStreamSource<String> dataStreamSource =  environment.readTextFile(filePath);
        return dataStreamSource;
    }
}
