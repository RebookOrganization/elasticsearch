package com.rebook.elasticsearch.service.impl;

import com.rebook.elasticsearch.bean.BaseResponse;
import com.rebook.elasticsearch.dao.CommentEsDao;
import com.rebook.elasticsearch.dao.ContactOwnerDao;
import com.rebook.elasticsearch.dao.LikeNewsEsDao;
import com.rebook.elasticsearch.dao.NewsImageEsDao;
import com.rebook.elasticsearch.dao.NewsItemEsDao;
import com.rebook.elasticsearch.dao.PropertyAddressEsDao;
import com.rebook.elasticsearch.dao.PropertyProjectEsDao;
import com.rebook.elasticsearch.dao.ShareNewsEsDao;
import com.rebook.elasticsearch.dao.UserEsDao;
import com.rebook.elasticsearch.dto.CommentNewsDTO;
import com.rebook.elasticsearch.dto.LikeNewsDTO;
import com.rebook.elasticsearch.dto.NewsResponseDTO;
import com.rebook.elasticsearch.dto.RequestFilterSearchDto;
import com.rebook.elasticsearch.dto.ShareNewsDTO;
import com.rebook.elasticsearch.model.NewsImageEs;
import com.rebook.elasticsearch.model.ShareNewsEs;
import com.rebook.elasticsearch.model.UserEs;
import com.rebook.elasticsearch.service.SearchNewsService;
import com.rebook.elasticsearch.utils.GsonUtils;
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

  @Autowired
  private ShareNewsEsDao shareNewsEsDao;

  @Autowired
  private UserEsDao userEsDao;

  private int returnCode = 1;
  private String returnMessage = "successfully";

  @Override
  public BaseResponse getAllNews(int offset) {
    List<Map<String, Object>> allNews = newsItemEsDao.getAllNewsByOffset(offset);
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
    logger.info("searchNewsByFilter request - {}", GsonUtils.toJsonString(request));
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
    List<Map<String, Object>> result = new ArrayList<>();

    String priceFrom = request.getPriceFrom();
    String priceTo = request.getPriceTo();
    String areaFrom = request.getAreaFrom();
    String areaTo = request.getAreaTo();
    String district = request.getDistrict();
    String province = request.getProvinceCity();
    String content = request.getContent();
    String directHouse = request.getDirectHouse();

    if (province != null && !province.isEmpty()) {
      List<Map<String, Object>> listAddressProperty = propertyAddressEsDao.findByAddress(content, district, province);
      for (Map<String, Object> map : listAddressProperty) {
        String addressId = String.valueOf(map.get("id"));
        Map<String, Object> news = newsItemEsDao.findByAddressId(addressId);

        if (news.get("price_num") != null) {
          double priceNum = Double.parseDouble(news.get("price_num").toString());
          if (priceNum >= Double.parseDouble(priceFrom) && priceNum <= Double.parseDouble(priceTo)) {
            result.add(newsItemEsDao.findByAddressId(addressId));
          }
        }

        if (news.get("area_num") != null) {
          double areaNum = Double.parseDouble(news.get("area_num").toString());
          if (areaNum >= Double.parseDouble(areaFrom) && areaNum <= Double.parseDouble(areaTo)) {
            result.add(newsItemEsDao.findByAddressId(addressId));
          }
        }
      }
    }
    else {
      result.addAll(newsItemEsDao.findAllByAreaAndPrice(priceFrom, priceTo, areaFrom, areaTo));
    }

    if (directHouse != null && !directHouse.isEmpty()) {
      result.addAll(newsItemEsDao.findAllByDirectHouse(directHouse));
    }

    for (Map<String, Object> item : result) {
      newsResponseDTOList.add(mapNewsToNewsResponseDTO(item));
    }

    logger.info("searchNewsByFilter response: {}", GsonUtils.toJsonString(newsResponseDTOList));

    return new BaseResponse<>(returnCode, returnMessage,
        newsResponseDTOList.size(), newsResponseDTOList);
  }

  @Override
  public List<Map<String, Object>> searchByPrice(String priceFrom, String priceTo) {
    return newsItemEsDao.findAllByPrice(priceFrom, priceTo);
  }

  @Override
  public List<Map<String, Object>> searchByArea(String areaFrom, String areaTo) {
    return newsItemEsDao.findAllByArea(areaFrom, areaTo);
  }

  @Override
  public BaseResponse getNewsById(String id) {
    try {
      Map<String, Object> newsItem = newsItemEsDao.getNewsItemById(id);
      NewsResponseDTO newsResponseDTO = mapNewsToNewsResponseDTO(newsItem);
      return new BaseResponse<>(1, "Successfully", newsResponseDTO);
    }
    catch (Exception ex) {
      logger.info("getNewsById Exception: ", ex);
      return new BaseResponse<>(0, ex.getMessage(), null);
    }
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

    Map<String, Object> user = userEsDao.getUserById(news.get("user_id").toString());
    newsResponseDTO.setUsername(user.get("name").toString());
    newsResponseDTO.setImageUser(user.get("image_url").toString());

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

    List<LikeNewsDTO> likeNewsDTOList = new ArrayList<>();
    List<Map<String, Object>> mapList = likeNewsEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (Map<String, Object> map : mapList) {
      LikeNewsDTO likeNewsDTO = new LikeNewsDTO();
      likeNewsDTO.setId(Long.parseLong(map.get("id").toString()));
      likeNewsDTO.setLike((Boolean) map.get("is_like"));
      likeNewsDTO.setNewsItemId(Long.parseLong(map.get("news_item_id").toString()));
      likeNewsDTO.setUserId(Long.parseLong(map.get("user_id").toString()));
      Map<String, Object> userLike = userEsDao.getUserById(map.get("user_id").toString());
      if (userLike != null) {
        likeNewsDTO.setUserName(userLike.get("name").toString());
        likeNewsDTO.setUserImageUrl(userLike.get("image_url").toString());
      }

      likeNewsDTOList.add(likeNewsDTO);
    }
    newsResponseDTO.setLikeNewsList(likeNewsDTOList);

    List<CommentNewsDTO> commentEsList = new ArrayList<>();
    List<Map<String, Object>> mapCommentList = commentEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (Map<String, Object> map : mapCommentList) {
      CommentNewsDTO commentNewsDTO = new CommentNewsDTO();
      commentNewsDTO.setId(Long.parseLong(map.get("id").toString()));
      commentNewsDTO.setContent((String) map.get("content"));
      commentNewsDTO.setNewItemId(Long.parseLong(map.get("news_item_id").toString()));
      commentNewsDTO.setUserId(Long.parseLong(map.get("user_id").toString()));
      Map<String, Object> userComment = userEsDao.getUserById(map.get("user_id").toString());
      if (userComment != null) {
        commentNewsDTO.setUserName(userComment.get("name").toString());
        commentNewsDTO.setUserImageUrl(userComment.get("image_url").toString());
      }
      commentEsList.add(commentNewsDTO);
    }
    newsResponseDTO.setCommentList(commentEsList);

    List<ShareNewsDTO> shareNewsDTOList = new ArrayList<>();
    List<ShareNewsEs> shareNewsEsList = shareNewsEsDao.getByNewsId(String.valueOf(news.get("id")));
    for (ShareNewsEs shareNewsEs : shareNewsEsList) {
      ShareNewsDTO shareNewsDTO = new ShareNewsDTO();
      shareNewsDTO.setId(shareNewsEs.getId());
      shareNewsDTO.setShare(shareNewsEs.isShare());
      shareNewsDTO.setNewItemId(shareNewsEs.getNewItemId());
      shareNewsDTO.setUserId(shareNewsEs.getUserId());
      Map<String, Object> userShare = userEsDao.getUserById(shareNewsEs.getUserId().toString());
      if (userShare != null) {
        shareNewsDTO.setUserName(userShare.get("name").toString());
        shareNewsDTO.setUserImageUrl(userShare.get("image_url").toString());
      }
      shareNewsDTOList.add(shareNewsDTO);
    }
    newsResponseDTO.setShareNewsList(shareNewsDTOList);

    return  newsResponseDTO;
  }
}
