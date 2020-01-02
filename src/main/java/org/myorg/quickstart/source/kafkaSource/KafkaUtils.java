package org.myorg.quickstart.source.kafkaSource;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 *操作Kafka
 */
public class KafkaUtils {

    /**
     * 从kafka 读取数据
     * @param environment  flink 环境变量
     * @return
     */
    public static DataStream<String> flinkReadFromKafka(StreamExecutionEnvironment environment){
        //配置kafka 连接信息
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.31.55:9092");
        //创建kafka消费者对象 去取kafka队列的信息
        // FlinkKafkaConsumer(String topic, DeserializationSchema<T> valueDeserializer, Properties props)
        FlinkKafkaConsumer<String> flinkKafkaConsumer = new FlinkKafkaConsumer<>("test",new SimpleStringSchema(),properties);
        //设置消费起始位置  -- flink 针对kafka的 消费策略    从最早的数据开始进行消费，忽略存储的offset信息
        flinkKafkaConsumer.setStartFromEarliest();

        //flink 流对象 获取 kafka消费者对象的数据
        DataStream<String> dataStream = environment.addSource(flinkKafkaConsumer);
        return  dataStream;
    }
}
