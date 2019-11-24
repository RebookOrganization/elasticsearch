package com.rebook.elasticsearch.rest;

import com.rebook.elasticsearch.dao.NewsItemEsDao;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
import com.rebook.elasticsearch.service.SearchNewsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/news-item")
public class NewsItemController {

  @Autowired
  private NewsItemEsDao newsItemEsDao;

  @Autowired
  private SearchNewsService searchNewsService;

  @GetMapping("/{id}")
  public Map<String, Object> getNewsItemById(@PathVariable String id) {
    return newsItemEsDao.getNewsItemById(id);
  }

  @GetMapping("/all-news")
  public ResponseEntity<?> getAllNews(@RequestParam int offset) {
    return new ResponseEntity<>(searchNewsService.getAllNews(offset), HttpStatus.OK);
  }

  @PostMapping("/search")
  public ResponseEntity<?> searchNewsByFilter(@RequestBody RequestFilterSearchDto request) {
    return new ResponseEntity<>(searchNewsService.searchNewsByFilter(request), HttpStatus.OK);
  }

}
