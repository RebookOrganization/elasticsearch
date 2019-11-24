package com.rebook.elasticsearch.dto;

import java.util.List;
import java.util.Map;

public class ScrollNewsResponse {
  private String scrollId;
  private List<Map<String, Object>> result;

  public ScrollNewsResponse() {
  }

  public ScrollNewsResponse(String scrollId,
      List<Map<String, Object>> result) {
    this.scrollId = scrollId;
    this.result = result;
  }

  public String getScrollId() {
    return scrollId;
  }

  public void setScrollId(String scrollId) {
    this.scrollId = scrollId;
  }

  public List<Map<String, Object>> getResult() {
    return result;
  }

  public void setResult(List<Map<String, Object>> result) {
    this.result = result;
  }
}
