package org.myorg.quickstart.Sink;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class TT {
    public static void main(String[] args) {
        //System.out.println(LocalDate.now());
        String username = "elastic";
        String password = "changeme";
        //使用base64进行加密   //ZWxhc3RpYzpjaGFuZ2VtZQ==
        byte[] tokenByte = Base64.encodeBase64((username+":"+password).getBytes());
        //将加密的信息转换为string
        try {
            String tokenStr =  new String(tokenByte,"UTF-8");
            System.out.println(tokenStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
