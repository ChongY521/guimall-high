package com.gulimall.search;

import com.gulimall.search.config.MyElasticsearchConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author aqiang9  2020-08-12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchApplicationTests {

    @Autowired
    RestHighLevelClient client ;
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
//        指定检索的缩影
        searchRequest.indices("bank");
//        DSL
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill")) ;


        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10)) ;
        searchSourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));

        System.out.println("检索条件");
        System.out.println(searchSourceBuilder.toString() );

        searchRequest.source(searchSourceBuilder) ;

//        执行检索
        SearchResponse search = client.search(searchRequest, MyElasticsearchConfig.COMMON_OPTIONS);
        System.out.println(search);
        Aggregations aggregations = search.getAggregations();


        System.out.println(aggregations.asMap());
    }

    /**
     * 测试 存储数据
     */
    @Test
    public void testIndex() throws IOException {
        IndexRequest userIndex = new IndexRequest("user");
        userIndex.id("1");
        userIndex.source(
                "userName","zs" ,
                "age",18 , "gender","n") ;
//        User user = new User("zs", "n", 10);
//        userIndex.source()
        IndexResponse index = client.index(userIndex, MyElasticsearchConfig.COMMON_OPTIONS);
        System.out.println(index);

    }


    class User{
        private String userName ;
        private String gender ;
        private Integer age ;

        public User(String userName, String gender, Integer age) {
            this.userName = userName;
            this.gender = gender;
            this.age = age;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
