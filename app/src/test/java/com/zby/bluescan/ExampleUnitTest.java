package com.zby.bluescan;

import android.os.Environment;
import com.zby.bluescan.database.BlueBean;
import com.zby.bluescan.database.BlueDao;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        
        //List<BlueBean> beanList = new ArrayList<>();
        //beanList.add(new BlueBean("name1", "mac1"));
        //beanList.add(new BlueBean("name2", "mac2"));
        //beanList.add(new BlueBean("name3", "mac3"));
        //Observable.just(beanList)
        //        .flatMap(new Func1<List<BlueBean>, Observable<Boolean>>() {
        //            @Override public Observable<Boolean> call(List<BlueBean> list) {
        //                try {
        //                    File file = new File("/User/zhuj/Desktop");
        //                    File parentFile = new File(file, "11zbyBlueScan");
        //                    ZzExcelCreator creator = ZzExcelCreator.getInstance()
        //                            .createExcel(parentFile.getPath(), "test")  //生成excel文件
        //                            .createSheet("mac");        //生成sheet工作表
        //
        //                    for (int i = 0; i < list.size(); i++) {
        //                        creator.openSheet(0).fillContent(0, i + 1, list.get(i).getName(), null);
        //                        creator.openSheet(0).fillContent(1, i + 1, list.get(i).getMac(), null);
        //                    }
        //                    creator.close();
        //                    return Observable.just(true);
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                } catch (WriteException e) {
        //                    e.printStackTrace();
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //                return Observable.just(false);
        //            }
        //        }).subscribeOn(Schedulers.newThread())
        //        .subscribe(new Subscriber<Boolean>() {
        //            @Override public void onCompleted() {
        //
        //            }
        //
        //            @Override public void onError(Throwable throwable) {
        //
        //            }
        //
        //            @Override public void onNext(Boolean b) {
        //                if (b) {
        //                } else {
        //                }
        //            }
        //        });
        //Thread.sleep(10000);
    }


}