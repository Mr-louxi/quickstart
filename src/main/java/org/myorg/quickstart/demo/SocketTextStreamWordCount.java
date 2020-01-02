package org.myorg.quickstart.demo;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.myorg.quickstart.functions.LineSplitter;

/**
 * Flink 流处理
 * DataStream  流处理API
 */
public class SocketTextStreamWordCount {
    public static void main(String[] args) {
        if(args.length!=2){
            System.err.println("USAGE:\nSocketTextStreamWordCount <hostname> <port>");
            return;
        }
        String hostname = args[0];
        Integer port = Integer.parseInt(args[1]);
        //Flink 程序 由以下几个部分构成
        //1.获得一个execution environment，
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //2.加载初始化数据
        DataStream<String> text = env.socketTextStream(hostname,port);

        //3.指定此数据的转换
        DataStream<Tuple2<String,Integer>> count = text.flatMap(new LineSplitter()).keyBy(0).sum(1);

        //4.指定放置计算结果的位置，
        count.print();

        //5.触发程序执行
        try {
            env.execute("Java WordCount from SocketTextStream Example");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
