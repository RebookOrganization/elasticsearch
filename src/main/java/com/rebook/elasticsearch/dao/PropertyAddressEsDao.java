package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.elasticsearch.model.PropertyAddressEs;
import com.rebook.elasticsearch.utils.GsonUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyAddressEsDao {
  private static final Logger logger = LoggerFactory.getLogger(PropertyAddressEsDao.class);

  private final String INDEX = "rebook_property_address_es";
  private final String TYPE = "property_address_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public PropertyAddressEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    Map<String, Object> result = null;
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      assert getResponse != null;
      logger.info("PropertyAddressEsDao getResponse -- {} ", getResponse);
      result = getResponse.getSourceAsMap();
    }
    catch (Exception ex) {
      logger.error("PropertyAddressEsDao getById exception - {}", ex.getMessage());
    }

    return result;
  }

  public List<Map<String, Object>> findByAddress(String content, String district, String province) {
    List<Map<String, Object>> listAddress = new ArrayList<>();

    MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
    SearchRequest firstRequest = new SearchRequest(INDEX);
    firstRequest.types(TYPE);
    SearchSourceBuilder firstBuilder = new SearchSourceBuilder();
    firstBuilder.query(QueryBuilders.matchQuery("district", district));
    firstRequest.source(firstBuilder);
    multiSearchRequest.add(firstRequest);

    SearchRequest secondRequest = new SearchRequest(INDEX);
    secondRequest.types(TYPE);
    SearchSourceBuilder secondBuilder = new SearchSourceBuilder();
    secondBuilder.query(QueryBuilders.matchQuery("province", province));
    secondRequest.source(secondBuilder);
    multiSearchRequest.add(secondRequest);

    SearchRequest thirdRequest = new SearchRequest(INDEX);
    thirdRequest.types(TYPE);
    SearchSourceBuilder thirdBuilder = new SearchSourceBuilder();
    thirdBuilder.query(QueryBuilders.matchQuery("summary", content));
    thirdRequest.source(thirdBuilder);
    multiSearchRequest.add(thirdRequest);

    MultiSearchResponse response;
    try {
      response = restHighLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);

      MultiSearchResponse.Item firstResponse = response.getResponses()[0];
      SearchResponse searchFirstResponse = firstResponse.getResponse();
      SearchHit[] firstHits = searchFirstResponse.getHits().getHits();
      int firstLen = firstHits.length;

      MultiSearchResponse.Item secondResponse = response.getResponses()[1];
      SearchResponse searchSecondResponse = secondResponse.getResponse();
      SearchHit[] secondHit = searchSecondResponse.getHits().getHits();
      int secondLen = secondHit.length;

      MultiSearchResponse.Item thirdResponse = response.getResponses()[1];
      SearchResponse searchThirdResponse = thirdResponse.getResponse();
      SearchHit[] thirdHit = searchThirdResponse.getHits().getHits();
      int thirdLen = thirdHit.length;

      SearchHit[] result = new SearchHit[firstLen + secondLen + thirdLen];
      System.arraycopy(firstHits, 0, result, 0, firstLen);
      System.arraycopy(secondHit, 0, result, firstLen, secondLen);
      System.arraycopy(thirdHit, 0, result, secondLen, thirdLen);

      logger.info("findByAddress result - {}", Arrays.toString(result));

      for (SearchHit hit : result) {
        Map<String, Object> map = hit.getSourceAsMap();
        //objectMapper
        PropertyAddressEs addressEs = objectMapper.convertValue(map, PropertyAddressEs.class);
        logger.info("objectMapper addressEs - {}", GsonUtils.toJsonString(addressEs));

        listAddress.add(map);
      }

    }
    catch (Exception ex) {
      logger.error("PropertyAddressEsDao findByAddress exception - {}", ex.getMessage());
    }

    logger.info("findByAddress listAddress - {}", listAddress);
    return listAddress;
  }
}
