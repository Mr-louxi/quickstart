package org.myorg.quickstart.Sink.EsSink;

import org.apache.commons.codec.binary.Base64;
import org.apache.flink.streaming.connectors.elasticsearch6.RestClientFactory;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClientBuilder;

import java.io.UnsupportedEncodingException;

/**
 * 带密码的 ES 验证登陆
 *
 * 由于Rest 接口的登陆认证 格式是统一的  Authorization:Base xxxx
 */
public class RestClientParamFactoryImpl implements RestClientFactory {
    @Override
    public void configureRestClientBuilder(RestClientBuilder restClientBuilder) {
        String user = "elastic";
        String password = "changeme";
        //Http 401 错误码  需要 在Header 中加入   Authorization:Base xxxxxxx                        其中 xxx 的格式  用户名:密码 进行base64转码
        byte[] tokenByte = Base64.encodeBase64((user+":"+password).getBytes());
        //将加密的信息转换为string
        String tokenStr = "";
        try {
            tokenStr =  new String(tokenByte,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BasicHeader authHead = new BasicHeader("Authorization","Basic "+tokenStr);
        BasicHeader typeHead = new BasicHeader("Content-Type","application/json");
        Header[] headers = {authHead,typeHead};
        restClientBuilder.setDefaultHeaders(headers); //以数组的形式可以添加多个header
        restClientBuilder.setMaxRetryTimeoutMillis(90000);
    }
}
