package com.zby.bluescan;

import android.app.Activity;

/**
 * 设备搜索界面
 *
 * @author Administrator
 */
public class DeviceScanActivity extends Activity {

    private final static String TAG = DeviceScanActivity.class.getSimpleName();

//    /**
    //     * 蓝牙设备搜索时间   ，单位毫秒
    //     */
    //    private final int scan_time = 10000;
    //
    //    private final static String filterName = "";
    //
    //    private TextView tv_titie;
    //
    //    private List<DeviceBean> list;
    //    private DeviceScanAdapter adapter;
    //    private RecyclerView listView;
    //
    //    private BluetoothAdapter btAdapter;
    //
    //    private Map<String, Long> filter = new HashMap<String, Long>();
    //
    //    private final static int handler_adapter_refresh = 11;
    //    private final static int handler_adapter_update = 17;
    //    private final static int handler_adapter_add = 18;
    //    private final static int handler_scan_start = 12;
    //    private final static int handler_scan_stop = 13;
    //    /**
    //     * 原来名字是空， 就显示在列表中， 现在突然能获取到名字了， 但是名字不符合，
    //     */
    //    private final static int handler_device_unformat = 14;
    //    private final static int handler_scan_hint = 15;
    //    private final static int activity_openBluetooth = 100;// 打开蓝牙
    //
    //    /**
    //     * 没有搜索到设备的次数 ，  连续三次都没搜索到一个蓝牙设备，就提示开关一下蓝牙。
    //     */
    //    private int scanNullCount = 0;
    //
    //    /**
    //     * 本次搜索是否搜索到蓝牙，  不用list是因为list只对sogood有记录， 对其他蓝牙没有
    //     */
    //    private boolean hasScanDevice = false;
    //
    //    @Override protected void onCreate(Bundle savedInstanceState) {
    //        // TODO Auto-generated method stub
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_scan_list);
    //        initViews();
    //
    //        Window window = getWindow();
    //        window.setGravity(Gravity.BOTTOM);
    //        //背景阴影
    //        WindowManager.LayoutParams lp2 = getWindow().getAttributes();
    //        lp2.dimAmount = 0.5f;
    //        getWindow().setAttributes(lp2);
    //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    //        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//需要添加的语句
    //
    //    }
    //
    //    private Thread testThread;
    //    /**
    //     * 蓝牙设备搜索 监听
    //     */
    //    private BluetoothAdapter.LeScanCallback scanCallBack = new LeScanCallback() {
    //
    //        @Override public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
    //            // TODO Auto-generated method stub
    //            Log.d(TAG,
    //                    "发现蓝牙设备: " + Thread.currentThread().getName() + " ~~ " + (arg0.getName() == null
    //                            ? "" : arg0.getName()) + " " + arg0.getAddress());
    //            hasScanDevice = true;
    //            foundDevice(arg0, arg1);
    //        }
    //    };
    //
    //    private void initViews() {
    //        tv_titie = (TextView) findViewById(R.id.textView_title);
    //        tv_titie.setText(R.string.scaning);
    //
    //        listView = (RecyclerView) findViewById(R.id.recyclerView);
    //        listView.setLayoutManager(new LinearLayoutManager(this));
    //        list = new ArrayList<DeviceBean>();
    //        adapter = new DeviceScanAdapter(list);
    //        listView.setAdapter(adapter);
    //
    //        adapter.setOnItemClickListener(new DeviceScanAdapter.OnRecyclerViewItemClickListener() {
    //            @Override public void onItemClick(View view, DeviceBean data) {
    //                showToast("点击" + data.getName());
    //                Intent intent = getIntent();
    //                intent.putExtra("deviceMac", data.getDeviceAddress());
    //                intent.putExtra("deviceName", data.getBluetoothName());
    //                setResult(Activity.RESULT_OK, intent);
    //                finish();
    //            }
    //        });
    //        //testDevice();
    //    }
    //
    //    private Handler mHandler = new Handler() {
    //        public void handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case handler_adapter_refresh:
    //                    adapter.notifyDataSetChanged();
    //                    break;
    //                case handler_adapter_update:
    //                    adapter.notifyItemChanged(msg.arg1);
    //                    break;
    //                case handler_adapter_add:
    //                    DeviceBean bean = (DeviceBean) msg.obj;
    //                    synchronized (list) {
    //                        list.add(bean);
    //                        adapter.notifyItemInserted(list.size());
    //                    }
    //                    break;
    //                case handler_device_unformat:
    //
    //                    break;
    //                case handler_scan_hint:
    //                    showToast(R.string.check_bluetooth);
    //                    break;
    //            }
    //        }
    //    };
    //
    ////测试刷新效率问题
    ////    private void testDevice() {
    ////        testThread = new Thread(new Runnable() {
    ////            @Override public void run() {
    ////                BlueBean db;
    ////                Random random = new Random();
    ////
    ////                try {
    ////                    for (int i = 0; i < 20000; i++) {
    ////                        db = new BlueBean();
    ////                        db.setAddress(
    ////                                "01:02:03:04:" + random.nextInt(9) + "0:" + (random.nextInt(9)) + (i
    ////                                        % 10));
    ////                        db.setName("" + i);
    ////                        Thread.sleep(random.nextInt(10));
    ////                        foundDevice(db, i % 100);
    ////                    }
    ////                } catch (InterruptedException e) {
    ////                    e.printStackTrace();
    ////                }
    ////            }
    ////        });
    ////        testThread.start();
    ////    }
    //
    //    @Override protected void onDestroy() {
    //        super.onDestroy();
    //    }
    //
    //    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        // TODO Auto-generated method stub
    //        switch (requestCode) {
    //            case activity_openBluetooth:
    //                if (resultCode == Activity.RESULT_OK) {
    //                    startScanThread(true);
    //                }
    //                break;
    //        }
    //        super.onActivityResult(requestCode, resultCode, data);
    //    }
    //
    //    /**
    //     * 获得打开的蓝牙适配器
    //     */
    //    private boolean enableBTAdapter() {
    //        if (btAdapter == null) {
    //            BluetoothManager bm = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
    //            if (bm == null) {
    //                Toast.makeText(this, R.string.bluetooth_not_support, Toast.LENGTH_LONG);
    //                finish();
    //                return false;
    //            }
    //            btAdapter = bm.getAdapter();
    //            if (btAdapter == null) {
    //                Toast.makeText(this, R.string.bluetooth_not_support, Toast.LENGTH_LONG);
    //                finish();
    //                return false;
    //            }
    //        }
    //        if (!btAdapter.enable()) {
    //            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    //            startActivityForResult(intent, activity_openBluetooth);
    //            return false;
    //        }
    //        return true;
    //    }
    //
    //    @Override public void finish() {
    //        //if (testThread !=null) {
    //        //    testThread.interrupt();
    //        //    testThread = null;
    //        //}
    //        super.finish();
    //    }
    //
    //    /**
    //     * 发现了蓝牙设备
    //     */
    //    private void foundDevice(BluetoothDevice device, int arg1) {
    //        if (filter.containsKey(device.getAddress())) {
    //            long delayTime = System.currentTimeMillis() - filter.get(device.getAddress());
    //            if (delayTime < 2000) {
    //                Log.v(TAG, "过滤蓝牙设备 " + delayTime + " mac: " + device.getAddress());
    //                return;
    //            }
    //        }
    //        filter.put(device.getAddress(), System.currentTimeMillis());
    //        Log.v(TAG, "对比蓝牙设备  : " + device.getAddress() + " " + device.getName());
    //        DeviceBean bin;
    //        // TODO: 2017/6/19 这里加了线程锁，但是会导致卡顿。 不加线程锁，list又与adapter对应不上
    //        //synchronized (list) {
    //            for (int i = 0; i < list.size(); i++) {
    //                bin = list.get(i);
    //                if (bin.getDeviceAddress().equals(device.getAddress())) {
    //                    //if (bin.getBluetoothName() == null || bin.getBluetoothName().equals("")) {
    //                    //    //在list列表中，原来没名字， 现在又名字，但是不是sogood， 就要删除原来的列表
    //                    //    System.out.println("原来没有名字  现在又名字了" + device.getName());
    //                    //    if (device.getName() != null && !device.getName().replace(" ", "").equalsIgnoreCase(filterName)) {
    //                    //        list.remove(i);
    //                    //        refreshAdapter();
    //                    //        //mHandler.sendEmptyMessage(handler_device_unformat);
    //                    //        //mHandler.sendEmptyMessage(handler_adapter_refresh);
    //                    //    }
    //                    //    return;
    //                    //}
    //                    //原来没有名字， 但是现在有名字了， 就更新名字
    //                    if (!TextUtils.isEmpty(device.getName()) && TextUtils.isEmpty(bin.getBluetoothName())) {
    //                        bin.setBluetoothName(device.getName());
    //                        Message msg = new Message();
    //                        msg.what = handler_adapter_update;
    //                        msg.arg1 = i;
    //                        mHandler.sendMessage(msg);
    //                        //mHandler.sendEmptyMessage(handler_adapter_refresh);
    //                    }
    //                    return;
    //                }
    //            }
    //            //名字不对的去除
    //            //if (device.getName() != null && !device.getName()
    //            //        .replace(" ", "")
    //            //        .equalsIgnoreCase(filterName)) {
    //            //    return;
    //            //}
    //            bin = new DeviceBean();
    //            bin.setBluetoothName(device.getName() == null ? "" : device.getName());
    //            bin.setDeviceAddress(device.getAddress());
    //            bin.setRSSI(arg1);
    //            System.out.println("发现添加 " + list.size() + bin.getName() + " " + bin.getDeviceAddress());
    //            Message msg = new Message();
    //            msg.what = handler_adapter_add;
    //            msg.obj = bin;
    //            mHandler.sendMessage(msg);
    //        //}
    //    }
    //
    //
    //    @Override protected void onStart() {
    //        // TODO Auto-generated method stub
    //        if (enableBTAdapter()) {
    //            startScanThread(true);
    //        }
    //        super.onStart();
    //    }
    //
    //    @Override protected void onStop() {
    //        // TODO Auto-generated method stub
    //        startScanThread(false);
    //        super.onStop();
    //    }
    //
    //    public void btn_back(View v) {
    //        finish();
    //    }
    //
    //    /**
    //     * 蓝牙搜索线程
    //     *
    //     * @param onOff 开始或 停止搜索
    //     */
    //    private synchronized void startScanThread(boolean onOff) {
    //        if (onOff) {
    //            scanThread = new Thread(new ScanRunnable());
    //            scanThread.start();
    //        } else {
    //            if (scanThread != null) {
    //                scanThread.interrupt();
    //                scanThread = null;
    //            }
    //        }
    //    }
    //
    //    Thread scanThread;
    //
    //    class ScanRunnable implements Runnable {
    //
    //        @Override public void run() {
    //            // TODO Auto-generated method stub
    //            boolean isScan = true;
    //            Log.v(TAG, "开始搜索线程");
    //            while (isScan) {
    //                if (btAdapter != null && btAdapter.isEnabled()) {
    //                    hasScanDevice = false;
    //                    btAdapter.startLeScan(scanCallBack);
    //                }
    //                try {
    //                    Thread.sleep(scan_time);
    //                } catch (InterruptedException e) {
    //                    // TODO Auto-generated catch block
    //                    e.printStackTrace();
    //                    isScan = false;
    //                    break;
    //                } finally {
    //                    if (btAdapter != null) {
    //                        if (hasScanDevice) {//本次搜索没有搜到， 累计次数
    //                            scanNullCount++;
    //                            if (scanNullCount % 3 == 0) {
    //                                mHandler.sendEmptyMessage(handler_scan_hint);
    //                            }
    //                        } else {
    //                            scanNullCount = 0;
    //                        }
    //                        btAdapter.stopLeScan(scanCallBack);
    //                        hasScanDevice = false;
    //                    }
    //                }
    //            }
    //            Log.v(TAG, "搜索线程停止");
    //        }
    //    }
    //
    //    ;
    //
    //    private Toast mToast;
    //
    //    private void showToast(String str) {
    //        if (mToast == null) {
    //            mToast = Toast.makeText(this, str, Toast.LENGTH_LONG);
    //        }
    //        mToast.setText(str);
    //        mToast.setDuration(Toast.LENGTH_LONG);
    //        mToast.show();
    //    }
    //
    //    private void showToast(int res) {
    //        showToast(getString(res));
    //    }
}
