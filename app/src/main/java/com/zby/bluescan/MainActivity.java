package com.zby.bluescan;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.crashlytics.android.Crashlytics;
import com.zby.bluescan.database.BlueBean;
import com.zby.bluescan.database.BlueDao;
import com.zby.bluescan.database.BuddyRealm;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

  private final String TAG = "blueScan";

  @BindView(R.id.et_name) EditText mEtName;
  @BindView(R.id.btn_scan) Button mBtnScan;
  @BindView(R.id.btn_export) Button mBtnExport;
  @BindView(R.id.btn_clean) Button mBtnClean;
  @BindView(R.id.recycler) RecyclerView mRecycler;

  Thread scanThread;
  @BindView(R.id.tv_scan_size) TextView mTvScanSize;
  @BindView(R.id.tv_all_size) TextView mTvAllSize;
  private DeviceScanAdapter mAdapter;
  private List<BlueBean> mBeanList;
  private BluetoothAdapter btAdapter;

  private Toast mToast;
  private long scan_time = 5 * 1000;
  private Map<String, Long> filter = new HashMap<String, Long>();
  private String filterName;
  private boolean isScanWork;
  private RealmResults mRealmResults;

  /**
   * //     * 蓝牙设备搜索 监听
   * //
   */
  private BluetoothAdapter.LeScanCallback scanCallBack = new BluetoothAdapter.LeScanCallback() {

    @Override public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
      // TODO Auto-generated method stub
      Log.d(TAG, "发现蓝牙设备: " + arg0.getName() + arg0.getAddress());

      foundDevice(arg0);
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    Realm.init(getApplicationContext());
    BuddyRealm.setDefaultRealmForUser("blueScan");
    initViews();
  }

  private void initViews() {
    String name = (String) SharedPreUser.getInstance().get(this, "name", "");
    mEtName.setText(name);

    mBeanList = new ArrayList<>();
    mAdapter = new DeviceScanAdapter(mBeanList);
    mRecycler.setLayoutManager(new LinearLayoutManager(this));
    mRecycler.setAdapter(mAdapter);
  }

  private void addListener() {
    mRealmResults = BlueDao.getListSync();
    mRealmResults.addChangeListener(new RealmChangeListener<RealmResults>() {
      @Override public void onChange(RealmResults realmResults) {
        if (realmResults.isValid()) {
          Observable.just(realmResults.size())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<Integer>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {

                    }

                    @Override public void onNext(Integer integer) {
                      mTvAllSize.setText(String.valueOf(integer));
                    }
                  });
        }
      }
    });
  }

  @OnClick(R.id.btn_scan) public void onViewClicked() {
    if (!isScanWork) {
      filterName = mEtName.getText().toString();
      if (!TextUtils.isEmpty(filterName)) {
        SharedPreUser.getInstance().put(this, "name", filterName);
      }
      if (enableBTAdapter()) {
        mTvScanSize.setText("0");
        mBeanList.clear();
        mAdapter.notifyDataSetChanged();
        filter.clear();
        startScanThread(true);
      }
    } else {
      startScanThread(false);
    }
  }

  @OnClick(R.id.btn_export) public void onViewExportClicked() {
    RealmResults<BlueDao> results = BlueDao.getList();
    List<BlueBean> list = new ArrayList<>();
    for (BlueDao blueDao : results) {
      list.add(blueDao.castBean());
    }
    exportExcel(list);
  }

  @OnClick(R.id.btn_clean) public void onViewCleanClicked() {
    BlueDao.cleanAll();
  }

  @Override protected void onStart() {
    // TODO Auto-generated method stub
    enableBTAdapter();
    addListener();
    super.onStart();
  }

  ;

  @Override protected void onStop() {
    // TODO Auto-generated method stub
    startScanThread(false);
    if (mRealmResults != null) {
      mRealmResults.removeAllChangeListeners();
    }
    super.onStop();
  }

  private void exportExcel(final List<BlueBean> beanList) {
    if (beanList == null || beanList.size() == 0) {
      showToast("数据为空");
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH:mm:ss");
    final String fileName = sdf.format(new Date());
    Observable.just(beanList)
            .flatMap(new Func1<List<BlueBean>, Observable<Boolean>>() {
              @Override public Observable<Boolean> call(List<BlueBean> list) {
                try {
                  File file = Environment.getExternalStorageDirectory();
                  File parentFile = new File(file, "11zbyBlueScan");
                  ZzExcelCreator creator = ZzExcelCreator.getInstance()
                          .createExcel(parentFile.getPath(), fileName)  //生成excel文件
                          .createSheet("mac");        //生成sheet工作表

                  for (int i = 0; i < list.size(); i++) {
                    creator.openSheet(0).fillContent(0, i, list.get(i).getName(), null);
                    creator.openSheet(0).fillContent(1, i, list.get(i).getMac(), null);
                  }
                  creator.close();
                  return Observable.just(true);
                } catch (IOException e) {
                  e.printStackTrace();
                } catch (WriteException e) {
                  e.printStackTrace();
                }
                return Observable.just(false);
              }
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Boolean>() {
              @Override public void onCompleted() {

              }

              @Override public void onError(Throwable throwable) {

              }

              @Override public void onNext(Boolean b) {
                if (b) {
                  showToast("导出" + beanList.size() + "条\n" + "11zbyBlueScan/" + fileName + ".xls");
                } else {
                  showToast("导出失败");
                }
              }
            });
  }

  /**
   * 蓝牙搜索线程
   *
   * @param onOff 开始或 停止搜索
   */
  private synchronized void startScanThread(boolean onOff) {
    if (onOff) {
      mBtnScan.setText("停止搜索");
      mBtnScan.setTextColor(getResources().getColor(R.color.green));

      isScanWork = true;
      scanThread = new Thread(new ScanRunnable());
      scanThread.start();
    } else {
      mBtnScan.setText("开始搜索");
      mBtnScan.setTextColor(getResources().getColor(R.color.text_normal));

      isScanWork = false;
      if (scanThread != null) {
        scanThread.interrupt();
        scanThread = null;
      }
    }
  }

  private void showToast(String str) {
    if (mToast == null) {
      mToast = Toast.makeText(this, str, Toast.LENGTH_LONG);
    }
    mToast.setText(str);
    mToast.setDuration(Toast.LENGTH_LONG);
    mToast.show();
  }

  private void showToast(int res) {
    showToast(getString(res));
  }

  /**
   * //     * 获得打开的蓝牙适配器
   * //
   */
  private boolean enableBTAdapter() {
    if (btAdapter == null) {
      BluetoothManager bm = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
      if (bm == null) {
        Toast.makeText(this, R.string.bluetooth_not_support, Toast.LENGTH_LONG);
        return false;
      }
      btAdapter = bm.getAdapter();
      if (btAdapter == null) {
        Toast.makeText(this, R.string.bluetooth_not_support, Toast.LENGTH_LONG);
        return false;
      }
    }
    if (!btAdapter.enable()) {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(intent, 11);
      return false;
    }
    return true;
  }

  private void foundDevice(BluetoothDevice device) {
    if (filter.containsKey(device.getAddress())) {
      Log.v(TAG, "过滤蓝牙设备 " + " mac: " + device.getAddress() + device.getName());
      return;
    }
    filter.put(device.getAddress(), System.currentTimeMillis());
    Log.v(TAG, "对比蓝牙设备  : " + device.getAddress() + " " + device.getName());
    BlueBean bin;
    for (int i = 0; i < mBeanList.size(); i++) {
      bin = mBeanList.get(i);
      if (bin.getMac().equals(device.getAddress())) {
        return;
      }
    }
    if (!TextUtils.isEmpty(filterName)) {
      if (TextUtils.isEmpty(device.getName())) {
        return;
      }
      //名字不对的去除
      if (device.getName() != null && !device.getName()
              .replace(" ", "")
              .toLowerCase()
              .contains(filterName.toLowerCase())) {
        return;
      }
    }
    bin = new BlueBean();
    bin.setMac(device.getAddress());
    bin.setName(device.getName());
    BlueDao.saveOrUpdate(bin);
    Observable.just(bin)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<BlueBean>() {
              @Override public void call(BlueBean blueBean) {
                mBeanList.add(0, blueBean);
                mAdapter.notifyItemInserted(0);
                mTvScanSize.setText(String.valueOf(mBeanList.size()));
              }
            });
  }

  class ScanRunnable implements Runnable {

    @Override public void run() {
      // TODO Auto-generated method stub
      boolean isScan = true;
      Log.v(TAG, "开始搜索线程");
      //BluetoothLeScanner
      while (isScan) {
        Log.v(TAG, "循环搜索");

        if (btAdapter != null && btAdapter.isEnabled()) {
          Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable throwable) {
              throwable.printStackTrace();
            }

            @Override public void onNext(String s) {
              mBeanList.clear();
              filter.clear();
              mAdapter.notifyDataSetChanged();
              mTvScanSize.setText("0");
            }
          });
          btAdapter.startLeScan(scanCallBack);
          //BluetoothLeScanner scanner = btAdapter.getBluetoothLeScanner();
          //scanner.startScan(leCallback);
        }
        try {
          Thread.sleep(scan_time);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          isScan = false;
          break;
        } finally {
          if (btAdapter != null) {
            btAdapter.stopLeScan(scanCallBack);
          }
        }
      }
      isScanWork = false;
      Observable.just("")
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<String>() {
                @Override public void call(String s) {
                  mBtnScan.setTextColor(getResources().getColor(R.color.text_normal));
                  mBtnScan.setText("开始搜索");
                }
              });
      Log.v(TAG, "搜索线程停止");
    }
  }
}
