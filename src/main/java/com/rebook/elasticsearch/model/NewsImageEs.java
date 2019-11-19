package com.rebook.elasticsearch.model;

public class NewsImageEs {
    private Long id;
    private long newsItemId;
    private String imageUrl;
    private String imageType;
    private Long imageSize;
    private byte[] picByte;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public long getNewsItemId() { return newsItemId; }

    public void setNewsItemId(long newsItemId) { this.newsItemId = newsItemId; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getImageType() { return imageType; }

    public void setImageType(String imageType) { this.imageType = imageType; }

    public Long getImageSize() { return imageSize; }

    public void setImageSize(Long imageSize) { this.imageSize = imageSize; }

    public byte[] getPicByte() { return picByte; }

    public void setPicByte(byte[] picByte) { this.picByte = picByte; }
}
