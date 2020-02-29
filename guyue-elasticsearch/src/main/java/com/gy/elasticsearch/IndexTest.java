package com.gy.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Created by lipeng
 * com.gy.elasticsearch
 * lipeng
 * 2019/1/7
 */
public class IndexTest {

    public static void main(String[] argv){

        //
        // index = sqlcommand
        // type = sqlinfo

        String serverName = "10.2.40.10:9200,10.2.40.14:9200,10.2.40.15:9200";


        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", serverName).build();

        // 创建一个查询请求对象
        SearchRequest searchRequest = new SearchRequest("sqlcommand");
        searchRequest.types("sqlinfo");

        System.exit(0);

        // 创建一个查询条件对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));




        //创建client
        @SuppressWarnings("resource")
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("0.0.0.0"), 9300));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //写入数据
        // createDate(client);
        //搜索数据
        GetResponse response = client.prepareGet("blog", "article", "1").execute().actionGet();

        //输出结果
        System.out.println(response.getSource());

        //关闭client
        client.close();
    }
}
