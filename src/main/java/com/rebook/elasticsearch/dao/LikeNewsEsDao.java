package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LikeNewsEsDao {
  private static final Logger logger = LoggerFactory.getLogger(LikeNewsEsDao.class);

  private final String INDEX = "rebook_like_news_es";
  private final String TYPE = "like_news_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public LikeNewsEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    Map<String, Object> result = null;
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("LikeNewsEsDao getResponse -- {} ", getResponse);
      assert getResponse != null;
      result = getResponse.getSourceAsMap();
    }
    catch (Exception ex) {
      logger.error("LikeNewsEsDao getById exception - {}", ex.getMessage());
    }

    return result;
  }

  public List<Map<String, Object>> getByNewsId(String newsId) {
    List<Map<String, Object>> result = new ArrayList<>();

    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.termQuery("news_item_id", newsId));
    searchRequest.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      assert searchResponse != null;
      logger.info("LikeNewsEsDao getByNewsId response: {}", searchResponse);

      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit: hits.getHits()) {
        Map<String, Object> map = hit.getSourceAsMap();
        result.add(map);
      }
    }
    catch (Exception ex) {
      logger.error("LikeNewsEsDao getByNewsId exception - {}", ex.getMessage());
    }

    return result;
  }

}
