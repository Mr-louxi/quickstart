package org.myorg.quickstart.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.myorg.quickstart.MyFlinkSource.MyGatewayLogSource;
import org.myorg.quickstart.MyFlinkSource.MyNoParalleSource;

import java.util.Properties;

/**
 * 使用flink 向kafka 写数据
 */
public class KafkaProducer {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> text = env.addSource(new MyGatewayLogSource()).setParallelism(1);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.31.55:9092");
        //new FlinkKafkaProducer("topn",new KeyedSerializationSchemaWrapper(new SimpleStringSchema()),properties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);
        FlinkKafkaProducer<String> producer = new FlinkKafkaProducer("gatewaylog_topic",new SimpleStringSchema(),properties);

        text.addSink(producer);
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
