package com.zby.bluescan.database;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import java.util.List;

/**
 * @author zhuj 2017/6/29 下午4:43.
 */
public class BlueDao extends RealmObject {

  private final static String COLUMN_ID = "id";

  @PrimaryKey
  private String mac;
  private String name;

  public static void saveOrUpdate(final BlueBean bean) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(castDao(bean));
      }
    });
  }

  public static void saveOrUpdates(final List<BlueBean> list) {
    BlueDao kidDao;
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    for (BlueBean bean : list) {
      kidDao = new BlueDao();
      kidDao.name = bean.getName();
      kidDao.mac = bean.getMac();
      try {
        realm.copyToRealmOrUpdate(kidDao);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    realm.commitTransaction();
  }

  /**
   * 查询所有记录,倒序查
   */
  public static RealmResults<BlueDao> getList() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<BlueDao> results;
    realm.beginTransaction();
    results = realm.where(BlueDao.class).findAll();
    realm.commitTransaction();
    //只获取最前面30条， 并清除多余的
    //if (results.size()>30) {
    //  results = results.subList(0,30);
    //}
    return results;
  }

  public static RealmResults<BlueDao> getListSync() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(BlueDao.class).findAllAsync();
  }

  /**
   * 清除所有
   */
  public static void cleanAll() {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.where(BlueDao.class).findAll().deleteAllFromRealm();
      }
    });
  }

  public static BlueDao castDao(BlueBean bean) {
    BlueDao dao = new BlueDao();
    dao.name = bean.getName();
    dao.mac = bean.getMac();
    return dao;
  }

  public BlueBean castBean() {
    BlueBean bean = new BlueBean();
    bean.setName(name);
    bean.setMac(mac);
    return bean;
  }
}
