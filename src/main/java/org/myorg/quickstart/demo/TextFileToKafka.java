package org.myorg.quickstart.demo;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.myorg.quickstart.source.fileSource.TextFileSource;

public class TextFileToKafka {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\louxi\\Desktop\\Flink\\测试案例\\gatewaylog.log";
        //获取环境
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //获取文件 Source
        DataStreamSource dataStreamSource = TextFileSource.flinkReadTxtFile(executionEnvironment,filePath);


    }
}
