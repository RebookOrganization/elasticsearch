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
public class PropertyProjectEsDao {
  private static final Logger logger = LoggerFactory.getLogger(PropertyProjectEsDao.class);

  private final String INDEX = "rebook_property_project_es";
  private final String TYPE = "property_project_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public PropertyProjectEsDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    GetResponse getResponse = null;
    try {
      getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
    }
    catch (Exception ex) {
      logger.error("PropertyProjectEsDao getById exception - {}", ex.getMessage());
    }
    assert getResponse != null;
    return getResponse.getSourceAsMap();
  }
}
