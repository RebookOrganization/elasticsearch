package com.rebook.elasticsearch.model;

public class LikeNewsEs  {

    private Long id;
    private Long userId;
    private Long newsItemId;
    private boolean isLike;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewsItemId() { return newsItemId; }

    public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }

    public boolean isLike() { return isLike; }

    public void setLike(boolean like) { isLike = like; }
}
