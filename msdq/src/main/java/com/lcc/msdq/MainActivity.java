package com.lcc.msdq;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.github.johnpersano.supertoasts.SuperToast;
import com.lcc.base.BaseActivity;
import com.lcc.frame.Propertity;
import com.lcc.frame.update.UpdateApkTask;
import com.lcc.msdq.area.AreaDialogFragment;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.compony.CompanyIndexFragment;
import com.lcc.msdq.index.IndexFragment;
import com.lcc.msdq.personinfo.PersonInfoIndexFragment;
import com.lcc.msdq.test.TestIndexFragment;
import com.lcc.utils.CoCoinToast;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.view.LoginDialog;
import com.lcc.view.bottombar.BottomBar;
import com.lcc.view.bottombar.BottomBarFragment;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import java.io.File;

import de.greenrobot.event.EventBus;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;

public class MainActivity extends BaseActivity {
    private BottomBar mBottomBar;
    private long firstTime;
    private static final int DAY = 60 * 60 * 24;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mBottomBar = BottomBar.attach(MainActivity.this, savedInstanceState);
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(IndexFragment.newInstance(), R.drawable.ic_home_black_24dp, "主页"),
                new BottomBarFragment(TestIndexFragment.newInstance(), R.drawable.ic_receipt_black_24dp, "面试资料"),

                new BottomBarFragment(PersonInfoIndexFragment.newInstance(), R.drawable.ic_perm_identity_black_24dp, "个人中心")
        );

        //new BottomBarFragment(CompanyIndexFragment.newInstance(), R.drawable.ic_search_black_24dp, "公司真题"),
        String updateTime = SharePreferenceUtil.getUpdateTime();
        if (!TextUtils.isEmpty(updateTime)) {
            String localtime = TimeUtils.StrTime(System.currentTimeMillis());
            if ((Long.parseLong(localtime) - Long.parseLong(updateTime)) / (60 * 60 * 24) > 7) {
                updateAPK();
            }
        } else {
            updateAPK();
        }

        //申请6.0的权限
        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_CONTACTS)
                .request();

//      LoginDialog dialog =new LoginDialog(MainActivity.this);
//      dialog.show();

        File file_dir = new File(Propertity.newFile);
        if (!file_dir.exists()) {
            file_dir.mkdirs();
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean Open() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.index;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    public void onEvent(Integer event) {
        switch (event) {
            case 0x02:
                this.recreate();
                break;
        }
    }

    public void updateAPK() {
        UpdateApkTask task = new UpdateApkTask(MainActivity.this, false);
        task.detectionVersionInfo();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {
                FrameManager.getInstance().toastPrompt("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                exitApp();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public void exitApp() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }



}
