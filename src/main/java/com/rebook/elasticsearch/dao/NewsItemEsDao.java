package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
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
import org.elasticsearch.search.sort.SortOrder;
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

  public List<Map<String, Object>> getAllNews(int offset){
    final int size = 20;
    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.matchAllQuery());
    sourceBuilder.from(offset);
    sourceBuilder.size(size);
    sourceBuilder.sort("_id", SortOrder.DESC);
    searchRequest.source(sourceBuilder);

    List<Map<String, Object>> result = new ArrayList<>();
    SearchResponse searchResponse;
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
      logger.error("NewsItemEsDao getAllNews exception - " + ex);
      ex.getLocalizedMessage();
    }

    return result;
  }

  public List<Map<String, Object>> searchNewsByFilter(RequestFilterSearchDto request) {
    List<Map<String, Object>> result = new ArrayList<>();
    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    SearchResponse searchResponse = null;
    try {
      searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      logger.info("NewsItemEsDao searchNewsByFilter response: {}", searchResponse);

      assert searchResponse != null;
      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit: hits.getHits()) {
        Map<String, Object> map = hit.getSourceAsMap();
        result.add(map);
      }
    }
    catch (Exception ex) {
      logger.error("NewsItemEsDao searchNewsByFilter exception - " + ex);
      ex.getLocalizedMessage();
    }

    return result;
  }

  public List<Map<String, Object>> findAllByPrice(String priceFrom, String priceTo) {
    List<Map<String, Object>> listNewsPrice = new ArrayList<>();

    return listNewsPrice;
  }

  public List<Map<String, Object>> findAllByArea(String areaFrom, String areaTo) {
    List<Map<String, Object>> listNewsArea = new ArrayList<>();

    return listNewsArea;
  }

  public List<Map<String, Object>> findAllByDirectHouse(String directHouse) {
    List<Map<String, Object>> listNewsDirectHouse = new ArrayList<>();
    SearchRequest searchRequest = new SearchRequest(INDEX);
    searchRequest.types(TYPE);
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.termQuery("direct_of_house", directHouse));
    searchRequest.source(sourceBuilder);

    SearchResponse searchResponse;
    try {
      searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      logger.info("NewsItemEsDao findAllByDirectHouse response: {}", searchResponse);

      assert searchResponse != null;
      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit: hits.getHits()) {
        Map<String, Object> map = hit.getSourceAsMap();
        listNewsDirectHouse.add(map);
      }
    }
    catch (Exception ex) {
      logger.error("findAllByDirectHouse exception - ", ex);
    }

    return listNewsDirectHouse;
  }

  public List<Map<String, Object>> findNewsByAddress(List<Map<String, Object>> listAddress) {
    List<Map<String, Object>> listNewsAddress = new ArrayList<>();

    return listNewsAddress;
  }

}
