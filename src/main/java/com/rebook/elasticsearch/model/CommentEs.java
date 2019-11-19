package com.rebook.elasticsearch.model;

public class CommentEs {

    private Long id;
    private Long userId;
    private Long newItemId;
    private String content;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewItemId() { return newItemId; }

    public void setNewItemId(Long newItemId) { this.newItemId = newItemId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
