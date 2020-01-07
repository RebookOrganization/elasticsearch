package com.rebook.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig extends AbstractFactoryBean<RestHighLevelClient> {

  private static final Logger logger = LoggerFactory.getLogger(ElasticConfig.class);
  @Value("${spring.data.elasticsearch.cluster-nodes}")
  private String clusterNodes;
  @Value("${spring.data.elasticsearch.cluster-name}")
  private String clusterName;
  @Value("${elasticsearch.host}")
  private String esHost;
  @Value("${elasticsearch.port}")
  private int esPort;

  private static RestHighLevelClient restHighLevelClient;

  @Bean
  @ConditionalOnMissingBean
  public static RestHighLevelClient restHighLevelClient() {
    return restHighLevelClient;
  }

  @Override
  public void destroy() {
    try {
      if (restHighLevelClient != null) {
        restHighLevelClient.close();
      }
    } catch (final Exception e) {
      logger.error("Error closing ElasticSearch client: ", e);
    }
  }

  @Override
  public Class<RestHighLevelClient> getObjectType() {
    return RestHighLevelClient.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  @Override
  protected RestHighLevelClient createInstance() throws Exception {
    return buildClient();
  }

  private RestHighLevelClient buildClient() {
    try {
      restHighLevelClient = new RestHighLevelClient(
          RestClient.builder(
              new HttpHost(esHost, esPort, "http"),
              new HttpHost(esHost, 9201, "http")));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return restHighLevelClient;
  }
}
