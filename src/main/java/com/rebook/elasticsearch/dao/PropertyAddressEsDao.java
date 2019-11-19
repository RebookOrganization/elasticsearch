package com.rebook.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
}
