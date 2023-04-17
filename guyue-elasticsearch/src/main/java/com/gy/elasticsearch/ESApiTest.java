package com.gy.elasticsearch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ESApiTest {

    TransportClient client = null;

    @Before
    public void before() {
        // client = TestCommon.getClient();
        client = ESUtils.getESClient();
    }

    @Test
    public void test() {
        int pageNo = 0;
        int pageSize = 20;

        String index = "sqlcommand";
        String type = "sqlinfo";

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("_id", "1"));
//        boolQueryBuilder.must(QueryBuilders.termQuery("application_id",  "zhangqian20181212054557"));
//        boolQueryBuilder.must(QueryBuilders.termQuery("user_ip_address", "zhangqian20181212054557"));
//        boolQueryBuilder.must(QueryBuilders.termQuery("ret", "zhangqian20181212054557"));
//        boolQueryBuilder.must(QueryBuilders.termQuery("mode_type", "zhangqian20181212054557"));
//         boolQueryBuilder.must(QueryBuilders.rangeQuery("current_time").gte("2019-01-02 00:00:50"));

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(boolQueryBuilder)
                .setFrom(pageNo * pageSize)
                .setSize(pageSize);

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        System.out.println("------------------" + response.toString());
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(hits.getHits().length);

        for (SearchHit hit : response.getHits().getHits()) {
            String line = hit.getSourceAsString();
            System.out.println(line);
        }
    }

    private String queryHiveLog() {

        return null;
    }

    @After
    public void after() {
        client.close();
    }

}
