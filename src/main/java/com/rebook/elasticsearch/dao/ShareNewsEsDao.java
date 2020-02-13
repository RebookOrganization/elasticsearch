package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.elasticsearch.model.ShareNewsEs;
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
public class ShareNewsEsDao {
  private static final Logger logger = LoggerFactory.getLogger(ShareNewsEsDao.class);

  private final String INDEX = "rebook_share_news_es";
  private final String TYPE = "share_news_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public ShareNewsEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public ShareNewsEs getShareById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    GetResponse getResponse = null;
    try {
      getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("ShareNewsEsDao getResponse -- {} ", getResponse);
    }
    catch (Exception ex) {
      logger.error("ShareNewsEsDao getById exception - {}", ex.getMessage());
    }
    assert getResponse != null;
    return objectMapper.convertValue(getResponse.getSourceAsMap(), ShareNewsEs.class);
  }

  public List<ShareNewsEs> getByNewsId(String newsId) {
    List<ShareNewsEs> result = new ArrayList<>();
    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.termQuery("news_item_id", newsId));
    searchRequest.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      assert searchResponse != null;
      logger.info("ShareNewsEsDao getByNewsId response: {}", searchResponse);

      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit: hits.getHits()) {
        Map<String, Object> map = hit.getSourceAsMap();
        result.add(objectMapper.convertValue(map, ShareNewsEs.class));
      }
    }
    catch (Exception ex) {
      logger.error("ShareNewsEsDao getByNewsId exception - {}", ex.getMessage());
    }

    return result;
  }
}
