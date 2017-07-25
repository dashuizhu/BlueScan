package com.zby.bluescan;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用来保存user相关数据， 切换用户，直接清除文件
 *
 * @author zhuj
 * @date 2017/6/15 下午6:11
 */
public class SharedPreUser extends BaseSharedPre {
  /**
   * 保存在手机里面的文件名
   */
  public static final String FILE_NAME = "user_data";
  private static SharedPreUser mSharedPre;

  public static SharedPreUser getInstance() {
    if (mSharedPre == null) {
      synchronized (SharedPreUser.class) {
        if (mSharedPre == null) {
          mSharedPre = new SharedPreUser();
        }
      }
    }
    return mSharedPre;
  }

  @Override SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
  }
}
