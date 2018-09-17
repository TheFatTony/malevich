package io.malevich.server.transfer;


public class LobStorageDto {

  private long id;
  private long fileId;
  private String content;
  private long fileSize;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

}
