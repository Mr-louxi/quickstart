package org.myorg.quickstart.Sink.EsSink;

import org.apache.flink.streaming.connectors.elasticsearch6.RestClientFactory;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClientBuilder;

public class RestClientFactoryImpl implements RestClientFactory {
    @Override
    public void configureRestClientBuilder(RestClientBuilder restClientBuilder) {
        BasicHeader type = new BasicHeader("Content-Type","application/json");
        Header[] headers = {type};
        restClientBuilder.setDefaultHeaders(headers); //以数组的形式可以添加多个header
        restClientBuilder.setMaxRetryTimeoutMillis(90000);
    }
}
