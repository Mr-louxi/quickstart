package org.myorg.quickstart.tableAPI_SQL;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.types.Row;

import java.util.Properties;

/**
 *  从Kafka 读取数据  使用TableAPI 操作数据  并写入 ES
 *
 */
public class MyTableEnvironment {
    public static Schema schema = null;
    static {
        schema = new Schema();
        schema.field("remote_addr", Types.STRING);
        schema.field("remote_user", Types.STRING);
        schema.field("time_iso8601", Types.STRING);
        schema.field("request", Types.STRING);
        schema.field("status", Types.STRING);
        schema.field("body_bytes_sent", Types.STRING);
        schema.field("upstream_response_time", Types.STRING);
        schema.field("upstream_addr", Types.STRING);
        schema.field("http_referer", Types.STRING);
        schema.field("http_user_agent", Types.STRING);
        schema.field("http_x_forwarded_for", Types.STRING);
        schema.field("request_uri", Types.STRING);
    }

    public static void main(String[] args) {
        //1.获取 Flink Stream 的 运行环境 Env
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setParallelism(1);
        //
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        //2.获取 StreamTable 的 运行环境
        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(executionEnvironment,settings);
          //配置kafka的信息
        //配置kafka 连接信息
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.31.55:9092");
        /*properties.put("topic","gatewaylog_topic");*/
        //properties.put("group.id","test");
        // 使用Table API 读取Kafka
        //3. 构建表结构
        Kafka kafka = new Kafka().properties(properties);
        kafka.topic("gatewaylog_topic");
        //kafka.version("0.10");
        kafka.version("universal");
        //设置消费起始位置
        kafka.startFromEarliest();
        //连接 kafka 并 注册 table source
        streamTableEnvironment.connect(kafka).withFormat( new Json().failOnMissingField(true).deriveSchema()).withSchema(schema).inAppendMode().registerTableSource("source_gatewaylog");

        //4.Table API
        //构建sql
        String sql = "select * from source_gatewaylog where status = '200'";
        // 使用 Table API 进行计算
        Table table = streamTableEnvironment.sqlQuery(sql);
        // 讲查询数据 以追加的形式 存放到  流中
      //  streamTableEnvironment.toAppendStream(table,TypeInformation.of(Row.class)).print("append");

       /* //5. Print ES Table
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.host("192.168.31.40", 9200,"http");// 如果是集群  该项可以配置多个
        //设置 索引
        elasticsearch.index("gatewaylog");
        elasticsearch.documentType("gatewaylog");

        streamTableEnvironment.connect(elasticsearch).withSchema(schema).withFormat(new Json().deriveSchema()).inUpsertMode().registerTableSink("sink_gatewaylog");

        streamTableEnvironment.insertInto(table,new StreamQueryConfig(),"Reslut");
        try {
            executionEnvironment.execute("SqlSinkElasticSearchStream");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataStream<Row> rowDataStream = streamTableEnvironment.toAppendStream(table,TypeInformation.of(Row.class));
        rowDataStream.print();
        table.printSchema();


        try {
            executionEnvironment.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
