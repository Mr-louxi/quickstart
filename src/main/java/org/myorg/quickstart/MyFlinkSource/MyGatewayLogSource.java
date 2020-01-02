package org.myorg.quickstart.MyFlinkSource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 生产日志数据 到 kafka
 */
public class MyGatewayLogSource implements SourceFunction<String> {//1

    private boolean isRunning = true;

    /**
     * 主要的方法
     * 启动一个source
     * 大部分情况下，都需要在这个run方法中实现一个循环，这样就可以循环产生数据了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        List<String> books = new ArrayList<>();
        String filePath = "C:\\Users\\louxi\\Desktop\\Flink\\测试案例\\gatewaylog.log";
        JSONArray jsonArray = this.toArrayByFileReader1(filePath);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ctx.collect(jsonObject.toString());
            System.out.println("写入kafka一条消息:"+jsonObject.toString());
        }
    }
    //取消一个cancel的时候会调用的方法
    @Override
    public void cancel() {
        isRunning = false;
    }



    public static JSONArray toArrayByFileReader1(String filePath) {
        JSONArray jsonArray = new JSONArray();
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            Map<String,String> map = new HashMap<>();
            String s = arrayList.get(i);
            String[] ss = s.split("\\|");
            map.put("remote_addr",ss[0]);
            map.put("remote_user",ss[1]);
            map.put("time_iso8601",ss[2]);
            map.put("request",ss[3]);
            map.put("status",ss[4]);
            map.put("body_bytes_sent",ss[5]);
            map.put("upstream_response_time",ss[6]);
            map.put("upstream_addr",ss[7]);
            map.put("http_referer",ss[8]);
            map.put("http_user_agent",ss[9]);
            map.put("http_x_forwarded_for",ss[10]);
            map.put("request_uri",ss[11]);
            jsonArray.put(map);
        }
        // 返回数组
        return jsonArray;
    }
}
