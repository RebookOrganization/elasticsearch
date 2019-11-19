package com.rebook.elasticsearch.model;

public class ShareNewsEs {

    private Long id;
    private Long userId;
    private Long newItemId;
    private boolean isShare;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getNewItemId() { return newItemId; }

    public void setNewItemId(Long newItemId) { this.newItemId = newItemId; }

    public boolean isShare() { return isShare; }

    public void setShare(boolean share) { isShare = share; }
}
