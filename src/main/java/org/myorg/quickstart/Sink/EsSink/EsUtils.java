package org.myorg.quickstart.Sink.EsSink;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchUpsertTableSinkBase;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch.util.IgnoringFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.util.RetryRejectedExecutionFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch6.Elasticsearch6UpsertTableSink;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Elasticsearch;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentType;
import org.myorg.quickstart.tableAPI_SQL.MyTableEnvironment;

import java.time.LocalDate;
import java.util.*;

/**
 *  操作es
 */
public class EsUtils {

    /**
     * 写入es 索引
     * @param dataStream
     */
    public static void flinkWriteToEsIndex(DataStream<String> dataStream){
        Map<String, String> config = new HashMap<>();

        config.put("bulk.flush.max.actions", "1");
        config.put("cluster.name", "ggs_elk_es");
        try {
            //List<InetSocketAddress> transports = new ArrayList<>();
            //transports.add(new InetSocketAddress("192.168.31.40", 9300)); // port is 9300 not 9200 for ES TransportClient
            List<HttpHost> httpHosts = new ArrayList<>();
            //这里使用 el 配置文件中 配置的http端口
            httpHosts.add(new HttpHost("192.168.31.40", 9200,"http"));
            ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(httpHosts, new ElasticsearchSinkFunction<String>() {
                @Override
                public void process(String s, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                    requestIndexer.add(createIndexRequest(s));
                }
                public IndexRequest createIndexRequest(String element) {
                    String[] logContent = element.trim().split("\\|");
                    Map<String, String> esJson = new HashMap<>();
                    esJson.put("name", logContent[0]);
                    esJson.put("floor", logContent[1]);
                    long epoch = System.currentTimeMillis();
                    String s = Objects.toString(epoch, null);
                    esJson.put("datetime",s);
                    return Requests
                            .indexRequest()
                            .index("kafka-data."+ LocalDate.now())
                            .type(logContent[0])
                            .source(esJson);
                }
            });
            esSinkBuilder.setBulkFlushMaxActions(1);
            //ES 无密码
            //   esSinkBuilder.setRestClientFactory(new RestClientFactoryImpl());
            //ES 有密码
            esSinkBuilder.setRestClientFactory(new RestClientParamFactoryImpl());
            esSinkBuilder.setFailureHandler(new RetryRejectedExecutionFailureHandler());
            dataStream.addSink(esSinkBuilder.build());
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * 使用 table api 的方式写入es
     */
   /* public static void flinkTableSinkES(StreamTableEnvironment streamTableEnvironment){
        Elasticsearch elasticsearch = new Elasticsearch();
        Map<ElasticsearchUpsertTableSinkBase.SinkOption, String> map = new HashMap<>();
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.DISABLE_FLUSH_ON_CHECKPOINT, "false");
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.BULK_FLUSH_MAX_ACTIONS, "20");
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.BULK_FLUSH_MAX_SIZE, "20 mb");
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.BULK_FLUSH_INTERVAL, "60000");
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.BULK_FLUSH_BACKOFF_DELAY, "30000");
        map.put(ElasticsearchUpsertTableSinkBase.SinkOption.BULK_FLUSH_BACKOFF_RETRIES, "3");

        try {
            List<HttpHost> httpHosts = new ArrayList<>();
            //这里使用 el 配置文件中 配置的http端口
            httpHosts.add(new HttpHost("192.168.31.40", 9200,"http"));
            Elasticsearch6UpsertTableSink sink = new Elasticsearch6UpsertTableSink(
                    true, MyTableEnvironment.schema, httpHosts, "test", "test",
                    "_", "null",
                    schemaRow,
                    XContentType.JSON, new IgnoringFailureHandler(), map);
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
}
