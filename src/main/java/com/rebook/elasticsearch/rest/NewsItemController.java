package com.rebook.elasticsearch.rest;

import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
import com.rebook.elasticsearch.service.SearchNewsService;
import com.rebook.elasticsearch.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/news-item")
public class NewsItemController {

  @Autowired
  private SearchNewsService searchNewsService;

  @GetMapping
  public ResponseEntity<?> getNewsItemById(@RequestParam String id) {
    return new ResponseEntity<>(searchNewsService.getNewsById(id), HttpStatus.OK);
  }

  @GetMapping("/all-news")
  public ResponseEntity<?> getAllNews(@RequestParam int offset) {
    return new ResponseEntity<>(searchNewsService.getAllNews(offset), HttpStatus.OK);
  }

  @PostMapping("/search")
  public ResponseEntity<?> searchNewsByFilter(@RequestBody String request) {
    RequestFilterSearchDto requestSearch = GsonUtils.fromJsonString(request, RequestFilterSearchDto.class);
    return new ResponseEntity<>(searchNewsService.searchNewsByFilter(requestSearch), HttpStatus.OK);
  }

  @GetMapping("/search-price")
  public ResponseEntity<?> searchByPrice(@RequestParam String priceFrom, @RequestParam String priceTo) {
    return new ResponseEntity<>(searchNewsService.searchByPrice(priceFrom, priceTo), HttpStatus.OK);
  }

  @GetMapping("/search-area")
  public ResponseEntity<?> searchByArea(@RequestParam String areaFrom, @RequestParam String areaTo) {
    return new ResponseEntity<>(searchNewsService.searchByArea(areaFrom, areaTo), HttpStatus.OK);
  }

}
