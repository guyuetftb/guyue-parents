package com.gy.elasticsearch;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * Created by lipeng
 * com.gy.elasticsearch
 * lipeng
 * 2019/1/7
 */
public class ClientHighLevel {

    public static void main(String[] argv) {

        // serverName = 10.2.40.10:9200,10.2.40.14:9200,10.2.40.15:9200
        //索引库名
        String index = "sqlcommand";
        String type = "sqlinfo";

        String clusterName = "es_test";
        String clusterIps = "10.6.88.46:9300";

        MultiSearchRequest multiSearchRequest = null;
        RequestOptions options = null;

        RestHighLevelClient client = null;
//        RestHighLevelClient client = ESUtils.createHighLevelClient();

        try {
            SearchRequest searchRequest = new SearchRequest(index);
            // searchRequest.searchType(type);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("application_id","application_1540296196571_808862"));
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // client.msearch();

        // System.out.print("----------------His-> " + response.getHits());

        // 搜索数据
        // GetResponse response = client.prepareGet("sqlcommand", "sqlinfo", "1").execute().actionGet();

        // 输出结果
//        System.out.println(response.getSourceAsString());

        // 关闭client
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
