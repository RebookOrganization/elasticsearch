package com.rebook.elasticsearch.service;

import com.rebook.elasticsearch.bean.BaseResponse;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;

public interface SearchNewsService {

  BaseResponse getAllNews(int offset);

  BaseResponse searchNewsByFilter(RequestFilterSearchDto request);
}
