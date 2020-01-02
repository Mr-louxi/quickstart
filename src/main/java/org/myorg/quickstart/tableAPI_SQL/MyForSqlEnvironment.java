package org.myorg.quickstart.tableAPI_SQL;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * 使用 create sql 去创建 输入输出表
 * 使用 select sql 去查询
 */
public class MyForSqlEnvironment {

    /**
     * 使用 sql 的方式  读取 kafka  并写入 Mysql
     * @param args
     */
    public static void main(String[] args) {
        //1.获取flink 环境
   /*     StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2.获取 StringTable 环境
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env);

*/
        //读取文件
        URL url = MyForSqlEnvironment.class.getClassLoader().getResource("source.sql");
        File file = new File(url.getFile());
        FileReader fr = null;
        BufferedReader bf = null;
        try {
             fr = new FileReader(file);
             bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                System.out.println(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fr!=null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bf!=null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
