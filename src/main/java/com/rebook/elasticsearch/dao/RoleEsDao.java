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
public class RoleEsDao {
  private static final Logger logger = LoggerFactory.getLogger(RoleEsDao.class);

  private final String INDEX = "rebook_role_es";
  private final String TYPE = "role_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public RoleEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    GetResponse getResponse = null;
    try {
      getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("RoleEsDao getResponse -- {} ", getResponse);
    }
    catch (Exception ex) {
      logger.error("RoleEsDao getById exception - {}", ex.getMessage());
    }
    assert getResponse != null;
    return getResponse.getSourceAsMap();
  }
}
