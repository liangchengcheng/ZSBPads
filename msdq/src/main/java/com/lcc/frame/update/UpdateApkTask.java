package com.lcc.frame.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Toast;

import com.lcc.AppConstants;
import com.lcc.entity.UpdateInfo;
import com.lcc.frame.Propertity;
import com.lcc.msdq.R;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.view.UpdateDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class UpdateApkTask {
    public static final String UPDATE_APK_URL = AppConstants.RequestPath.BASE_URL + "/service/getupdateinfo";
    public static final String APK_NAME = "msdq.apk";
    public static final String APKPAKEG_NAME = "com.lcc.msdq";
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private UpdateDialog mDialog;
    private Context mContext;

    public static final String HDSX_NMJK = Propertity.newFile;

    public UpdateApkTask(Context mContext, boolean mIsShowToast) {
        this.mContext = mContext;
    }

    public void detectionVersionInfo() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sb = "";
                try {
                    URL url = new URL(UPDATE_APK_URL);
                    URLConnection conn = url.openConnection();
                    InputStreamReader in = new InputStreamReader(
                            conn.getInputStream(), "utf-8");
                    BufferedReader br = new BufferedReader(in);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb += line;
                    }

                    in.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (TextUtils.isEmpty(sb)) {
                    return;
                }

                try {
                    UpdateInfo updateInfo = GsonUtils.changeGsonToBean(sb, UpdateInfo.class);

                    String code = updateInfo.getVersion_code();
                    int versionCode = Integer.parseInt(code);
                    if (versionCode > getVersionCode(mContext, APKPAKEG_NAME)) {
                        Message message = Message.obtain();
                        message.obj = updateInfo;
                        message.what = 3;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    mDialog.setTitle(msg.arg1);
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
                case 3:
                    UpdateInfo updateInfo = (UpdateInfo) msg.obj;
                    showUpdateDialog(updateInfo);
                    break;
            }
        }
    };

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog(String url) {
        mDialog = new UpdateDialog(mContext);
        mDialog.show();
        downloadAPK(url);
    }

    /**
     * 下载apk文件
     */
    private void downloadAPK(final String update_url) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // 获取sd卡存储的路径
                File tmpFile = new File(HDSX_NMJK);
                if (!tmpFile.exists()) {
                    tmpFile.mkdir();
                }
                URL url;
                try {
                    url = new URL(update_url);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File apkFile = new File(HDSX_NMJK, APK_NAME);
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    int numread;
                    while ((numread = is.read(buf)) > 0) {
                        count += numread;
                        // 计算进度条位置
                        Message msg = Message.obtain();
                        msg.arg1 = (int) (((float) count / length) * 100);
                        msg.what = DOWNLOAD;
                        // 更新进度
                        mHandler.sendMessage(msg);
                        // 写入文件
                        fos.write(buf, 0, numread);
                    }

                    if (numread <= 0) {
                        // 下载完成
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                    }

                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }
        }).start();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apk_file = new File(HDSX_NMJK + APK_NAME);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(apk_file), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    public static int getVersionCode(Context ctx, String packageName) {
        if (null == packageName || packageName.length() <= 0) {
            return -1;
        }

        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (Exception e) {
            return -1;
        }
    }

    private void showUpdateDialog(final UpdateInfo updateInfo) {
        new AlertDialog.Builder(mContext)
                .setTitle("升级新版本")
                .setMessage(Html.fromHtml(updateInfo.getVersion_info()))
                .setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDownloadDialog(updateInfo.getVersion_url());
                    }
                })
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String localtime = TimeUtils.StrTime(System.currentTimeMillis());
                        SharePreferenceUtil.setUpdateTime(localtime);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
