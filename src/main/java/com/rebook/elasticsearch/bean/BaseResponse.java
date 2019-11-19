package com.rebook.elasticsearch.bean;

public class BaseResponse<T> {
  private int returnCode;
  private String returnMessage;
  private int totalHits;
  private T result;

  public BaseResponse(int returnCode, String returnMessage, T result) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.result = result;
  }

  public BaseResponse(int returnCode, String returnMessage, int totalHits, T result) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.totalHits = totalHits;
    this.result = result;
  }

  public int getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(int returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  public int getTotalHits() {
    return totalHits;
  }

  public void setTotalHits(int totalHits) {
    this.totalHits = totalHits;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}
