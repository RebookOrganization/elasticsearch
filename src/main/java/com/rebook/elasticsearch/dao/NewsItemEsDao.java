package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class NewsItemEsDao {

  private static final Logger logger = LoggerFactory.getLogger(NewsItemEsDao.class);

  private final String INDEX = "rebook_news_item_es";
  private final String TYPE = "news_item_es";

  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public NewsItemEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getNewsItemById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    Map<String, Object> result = null;
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      assert getResponse != null;
      result = getResponse.getSourceAsMap();
    }
    catch (IOException e) {
      e.getLocalizedMessage();
    }

    return result;
  }

  public List<Map<String, Object>> getAllNews(){
    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchRequest.source(searchSourceBuilder);

    List<Map<String, Object>> result = new ArrayList<>();
    SearchResponse searchResponse = null;
    try {
      searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      logger.info("NewsItemEsDao getAllNews searchResponse: {}", searchResponse);

      assert searchResponse != null;
      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit: hits.getHits()) {
        Map<String, Object> map = hit.getSourceAsMap();
        result.add(map);
      }
    }
    catch (Exception ex) {
      ex.getLocalizedMessage();
    }

    return result;
  }

}
