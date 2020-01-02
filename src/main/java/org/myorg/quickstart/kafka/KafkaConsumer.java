package org.myorg.quickstart.kafka;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.myorg.quickstart.Sink.EsSink.EsUtils;
import org.myorg.quickstart.source.kafkaSource.KafkaUtils;

/**
 * 使用flink  从kafka 读取数据
 */
public class KafkaConsumer {
    public static void main(String[] args) {
        //获取一个flink 环境对象
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //从kafka 读取数据
        DataStream<String> dataStream = KafkaUtils.flinkReadFromKafka(env);

        dataStream.print();
        //将数据写入es
        EsUtils.flinkWriteToEsIndex(dataStream);
        //flink输出
        dataStream.print();

        //flink 任务开始执行
        try {
            env.execute("flink learning connectors kafka");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
