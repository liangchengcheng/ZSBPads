package com.lcc;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;

import com.lcc.db.test.DaoMaster;
import com.lcc.db.test.DaoSession;
import com.lcc.frame.net.okhttp.OkHttpClientManager;
import com.lcc.msdq.BuildConfig;
import com.lcc.utils.CoCoinUtil;
import com.squareup.okhttp.OkHttpClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.LogUtils;

public class App extends Application {

    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    public DaoSession daoSession;
    public SQLiteDatabase db;
    public DaoMaster.DevOpenHelper helper;
    public DaoMaster daoMaster;
    private OkHttpClient okHttpClient;

    private static final int CONNECT_TIMEOUT_MILLIS = 30 * 1000;
    private static final int READ_TIMEOUT_MILLIS = 30 * 1000;

    private static List<Activity> activityList = new LinkedList();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init(BuildConfig.LOG_DEBUG);
        FrameManager.setAppContext(this);
        FrameManager.getInstance().init();
        CoCoinUtil.init(this);
        initTypeface();
        setupDatabase();
        initOkHttp();

    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
    }

    /**
     * 创建数据库
     */
    private void setupDatabase() {
        helper = new DaoMaster.DevOpenHelper(this,"dtcj", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private void initOkHttp() {
        okHttpClient =
                OkHttpClientManager.getInstance().getOkHttpClient();
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * 添加Activity到容器中
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish
     */
    public static void exit() {
        for (Activity activity :activityList) {
            activity.finish();
        }
        //  System.exit(0);
    }

}
