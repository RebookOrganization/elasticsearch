package com.rebook.elasticsearch.model;

import com.rebook.elasticsearch.dto.ChatMessageDTO.MessageType;

//@Document(indexName = "real_estate_schema", type = "chat_message_es")
public class ChatMessageES implements java.io.Serializable{

  private Long id;
  private MessageType messageType;
  private String content;
  private String sender;

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public MessageType getMessageType() { return messageType; }

  public void setMessageType(MessageType messageType) { this.messageType = messageType; }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getSender() { return sender; }

  public void setSender(String sender) { this.sender = sender; }
}
