package com.rebook.elasticsearch.service;

import com.google.gson.JsonObject;
import com.rebook.elasticsearch.bean.BaseResponse;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.MultiValueMap;

public interface SearchNewsService {

  BaseResponse getAllNews(int offset);

  BaseResponse searchNewsByFilter(RequestFilterSearchDto request);

  List<Map<String, Object>> searchByPrice(String priceFrom, String priceTo);

  List<Map<String, Object>> searchByArea(String areaFrom, String areaTo);

  BaseResponse getNewsById(String id);
}
