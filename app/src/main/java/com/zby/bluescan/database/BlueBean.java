package com.zby.bluescan.database;

/**
 * @author zhuj 2017/7/6 上午1:06.
 */
public class BlueBean {

  private String mac;
  private String name;

  public BlueBean(){}

  public BlueBean (String name, String mac) {
    this.name = name;
    this.mac = mac;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
