package com.tanishk.auxalis.datasets;

public class ExternalLinks {

  private String wikipedia;

  public ExternalLinks() {
  }

  public void setWikipedia(String wikipedia) {
    this.wikipedia = wikipedia;
  }

  public String getWikipedia() {
    return wikipedia;
  }

  @Override
  public String toString() {
    return "ExternalLinks{" +
        "wikipedia='" + wikipedia + '\'' +
        '}';
  }
}
