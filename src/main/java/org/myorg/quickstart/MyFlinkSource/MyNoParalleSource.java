package org.myorg.quickstart.MyFlinkSource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//使用并行度为1的source
public class MyNoParalleSource implements SourceFunction<String> {//1

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
        while(isRunning){
            List<String> books = new ArrayList<>();
            books.add("武大吉奥|1楼");//10
            books.add("武大吉奥|2楼");//8
            books.add("武大吉奥|3楼");//5
            books.add("武大吉奥|4楼");//3
            books.add("武大吉奥|5楼");//0-4
            int i = new Random().nextInt(5);
            ctx.collect(books.get(i));
            //每2秒产生一条数据
            System.out.println("已经向kafka写入一条消息:"+books.get(i));
            Thread.sleep(2000);
        }
    }
    //取消一个cancel的时候会调用的方法
    @Override
    public void cancel() {
        isRunning = false;
    }
}
