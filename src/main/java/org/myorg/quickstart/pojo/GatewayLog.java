package org.myorg.quickstart.pojo;

import java.io.Serializable;

public class GatewayLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private String remote_addr;
    private String remote_user;
    private String time_iso8601;
    private String request;
    private String status;
    private String body_bytes_sent;
    private String upstream_response_time;
    private String upstream_addr;
    private String http_referer;
    private String http_user_agent;

    private String http_x_forwarded_for;
    private String request_uri;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getRemote_user() {
        return remote_user;
    }

    public void setRemote_user(String remote_user) {
        this.remote_user = remote_user;
    }

    public String getTime_iso8601() {
        return time_iso8601;
    }

    public void setTime_iso8601(String time_iso8601) {
        this.time_iso8601 = time_iso8601;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody_bytes_sent() {
        return body_bytes_sent;
    }

    public void setBody_bytes_sent(String body_bytes_sent) {
        this.body_bytes_sent = body_bytes_sent;
    }

    public String getUpstream_response_time() {
        return upstream_response_time;
    }

    public void setUpstream_response_time(String upstream_response_time) {
        this.upstream_response_time = upstream_response_time;
    }

    public String getUpstream_addr() {
        return upstream_addr;
    }

    public void setUpstream_addr(String upstream_addr) {
        this.upstream_addr = upstream_addr;
    }

    public String getHttp_referer() {
        return http_referer;
    }

    public void setHttp_referer(String http_referer) {
        this.http_referer = http_referer;
    }

    public String getHttp_user_agent() {
        return http_user_agent;
    }

    public void setHttp_user_agent(String http_user_agent) {
        this.http_user_agent = http_user_agent;
    }

    public String getHttp_x_forwarded_for() {
        return http_x_forwarded_for;
    }

    public void setHttp_x_forwarded_for(String http_x_forwarded_for) {
        this.http_x_forwarded_for = http_x_forwarded_for;
    }

    public String getRequest_uri() {
        return request_uri;
    }

    public void setRequest_uri(String request_uri) {
        this.request_uri = request_uri;
    }
}
