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
public class ContactOwnerDao {
  private static final Logger logger = LoggerFactory.getLogger(ContactOwnerDao.class);

  private final String INDEX = "rebook_contact_owner_es";
  private final String TYPE = "contact_owner_es";
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;

  public ContactOwnerDao(RestHighLevelClient restHighLevelClient,
      ObjectMapper objectMapper) {
    this.restHighLevelClient = restHighLevelClient;
    this.objectMapper = objectMapper;
  }

  public Map<String, Object> getById(String id) {
    GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
    GetResponse getResponse = null;
    try {
      getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("ContactOwnerDao getResponse -- {} ", getResponse);
    }
    catch (Exception ex) {
      logger.error("ContactOwnerDao getById exception - {}", ex.getMessage());
    }
    assert getResponse != null;
    return getResponse.getSourceAsMap();
  }
}
