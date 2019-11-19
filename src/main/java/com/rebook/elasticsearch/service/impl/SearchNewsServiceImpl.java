package com.rebook.elasticsearch.service.impl;

import com.rebook.elasticsearch.bean.BaseResponse;
import com.rebook.elasticsearch.dao.CommentEsDao;
import com.rebook.elasticsearch.dao.ContactOwnerDao;
import com.rebook.elasticsearch.dao.LikeNewsEsDao;
import com.rebook.elasticsearch.dao.NewsImageEsDao;
import com.rebook.elasticsearch.dao.NewsItemEsDao;
import com.rebook.elasticsearch.dao.PropertyAddressEsDao;
import com.rebook.elasticsearch.dao.PropertyProjectEsDao;
import com.rebook.elasticsearch.dto.NewsResponseDTO;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
import com.rebook.elasticsearch.model.CommentEs;
import com.rebook.elasticsearch.model.LikeNewsEs;
import com.rebook.elasticsearch.model.NewsImageEs;
import com.rebook.elasticsearch.service.SearchNewsService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchNewsServiceImpl implements SearchNewsService {

  private static final Logger logger = LoggerFactory.getLogger(SearchNewsServiceImpl.class);

  @Autowired
  private NewsItemEsDao newsItemEsDao;

  @Autowired
  private PropertyAddressEsDao propertyAddressEsDao;

  @Autowired
  private PropertyProjectEsDao propertyProjectEsDao;

  @Autowired
  private ContactOwnerDao contactOwnerDao;

  @Autowired
  private NewsImageEsDao newsImageEsDao;

  @Autowired
  private LikeNewsEsDao likeNewsEsDao;

  @Autowired
  private CommentEsDao commentEsDao;

  private int returnCode = 1;
  private String returnMessage = "successfully";

  @Override
  public BaseResponse getAllNews() {
    List<Map<String, Object>> allNews = newsItemEsDao.getAllNews();
    logger.info("SearchNewsServiceImpl getAllNews - {}", allNews);
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();

    for (Map<String, Object> news : allNews) {
      newsResponseDTOList.add(mapNewsToNewsResponseDTO(news));
    }

    return new BaseResponse<>(returnCode, returnMessage,
        newsResponseDTOList.size(), newsResponseDTOList);
  }

  @Override
  public BaseResponse searchNewsByFilter(RequestFilterSearchDto request) {
    return null;
  }

  private NewsResponseDTO mapNewsToNewsResponseDTO (Map<String, Object> news) {
    NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
    newsResponseDTO.setTitleNews((String) news.get("title"));
    newsResponseDTO.setSummaryNews((String) news.get("summary"));
    newsResponseDTO.setDescriptionNews((String) news.get("description"));
    newsResponseDTO.setArea((String) news.get("area"));
    newsResponseDTO.setPrice((String) news.get("price"));
    newsResponseDTO.setPubDate((String) news.get("pub_date"));
    newsResponseDTO.setNewsId(Long.parseLong(news.get("id").toString()));
    newsResponseDTO.setUserId(Long.parseLong(news.get("user_id").toString()));

    Set<NewsImageEs> newsImageEsSet = new HashSet<>();
    List<Map<String, Object>> mapImagesList = newsImageEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (Map<String, Object> map : mapImagesList) {
      NewsImageEs newsImageEs = new NewsImageEs();
      newsImageEs.setId(Long.parseLong(map.get("id").toString()));
      newsImageEs.setImageUrl((String) map.get("image_url"));
      newsImageEs.setNewsItemId(Long.parseLong(map.get("news_item_id").toString()));
      newsImageEsSet.add(newsImageEs);
    }
    newsResponseDTO.setImageUrlList(newsImageEsSet);

    Map<String, Object> address = propertyAddressEsDao.getById(String.valueOf(news.get("property_address_id")));
    assert address != null;
    newsResponseDTO.setAddress_prop((String) address.get("summary"));

    Map<String, Object> project = propertyProjectEsDao.getById(String.valueOf(news.get("property_project_id")));
    if (project != null) {
      newsResponseDTO.setProjectName((String) project.get("project_name"));
      newsResponseDTO.setProjectOwner((String) project.get("project_owner"));
      newsResponseDTO.setProjectSize((String) project.get("project_size"));
    }

    Map<String, Object> contact = contactOwnerDao.getById(String.valueOf(news.get("contact_owner_id")));
    if (contact != null) {
      newsResponseDTO.setContactName((String) contact.get("contact_name"));
      newsResponseDTO.setContactPhone((String) contact.get("phone_number"));
      newsResponseDTO.setContactEmail((String) contact.get("email"));
    }

    List<LikeNewsEs> likeNewsEsList = new ArrayList<>();
    List<Map<String, Object>> mapList = likeNewsEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (Map<String, Object> map : mapList) {
      LikeNewsEs likeNewsEs = new LikeNewsEs();
      likeNewsEs.setId(Long.parseLong(map.get("id").toString()));
      likeNewsEs.setLike((Boolean) map.get("is_like"));
      likeNewsEs.setNewsItemId(Long.parseLong(map.get("news_item_id").toString()));
      likeNewsEs.setUserId(Long.parseLong(map.get("user_id").toString()));
      likeNewsEsList.add(likeNewsEs);
    }
    newsResponseDTO.setLikeNewsList(likeNewsEsList);

    List<CommentEs> commentEsList = new ArrayList<>();
    List<Map<String, Object>> mapCommentList = commentEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (Map<String, Object> map : mapCommentList) {
      CommentEs commentEs = new CommentEs();
      commentEs.setId(Long.parseLong(map.get("id").toString()));
      commentEs.setContent((String) map.get("content"));
      commentEs.setNewItemId(Long.parseLong(map.get("news_item_id").toString()));
      commentEs.setUserId(Long.parseLong(map.get("user_id").toString()));
      commentEsList.add(commentEs);
    }
    newsResponseDTO.setCommentList(commentEsList);

    return  newsResponseDTO;
  }
}
