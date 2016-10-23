package com.lcc.msdq.description.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lcc.AppConstants;
import com.lcc.base.BaseActivity;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.test.answer.photo.UILImageLoader;
import com.lcc.msdq.test.answer.photo.UILPauseOnScrollListener;
import com.lcc.mvp.presenter.UserEditPresenter;
import com.lcc.mvp.presenter.impl.UserEditPresenterImpl;
import com.lcc.mvp.view.UserEditView;
import com.lcc.utils.FileUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.greenrobot.event.EventBus;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.LogUtils;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;
import zsbpj.lccpj.yasuo.Luban;
import zsbpj.lccpj.yasuo.OnCompressListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  UserEditActivity（更新用户的资料）
 */
public class UserEditActivity extends BaseActivity implements View.OnClickListener, UserEditView,
        RadioGroup.OnCheckedChangeListener,OnCompressListener {
    //昵称 性别 签名 邮箱
    private EditText edit_nickname, et_qm, et_email;
    private ImageView iv_question_des;
    private ImageView iv_save;
    private SimpleArcDialog mDialog;
    private RadioButton rb_nan, rb_nv;
    private RadioGroup radioSex;
    private TextView tv_phone;

    public FunctionConfig functionConfig;
    public static final String USERINFO = "userinfo";
    private List<File> files = new ArrayList<>();
    private UserEditPresenter userEditPresenter;
    private UserInfo userInfo = new UserInfo();
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private static final String newFile = Propertity.newFile;
    private boolean edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        rb_nan = (RadioButton) findViewById(R.id.rb_nan);
        rb_nv = (RadioButton) findViewById(R.id.rb_nv);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);
        radioSex.setOnCheckedChangeListener(this);

        ThemeConfig themeConfig = ThemeConfig.GREEN;
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(8)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new UILImageLoader(), themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);

        userInfo = (UserInfo) getIntent().getSerializableExtra(USERINFO);

        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        edit_nickname = (EditText) findViewById(R.id.edit_nickname);
        et_qm = (EditText) findViewById(R.id.et_qm);
        et_email = (EditText) findViewById(R.id.et_email);
        iv_question_des = (ImageView) findViewById(R.id.iv_question_des);
        iv_question_des.setOnClickListener(this);
        iv_save = (ImageView) findViewById(R.id.iv_save);
        iv_save.setOnClickListener(this);
        userEditPresenter = new UserEditPresenterImpl(this);

        String et = userInfo.getUser_image().toString();
        if (!TextUtils.isEmpty(et)) {
            ImageManager.getInstance().loadCircleImage(UserEditActivity.this,
                    userInfo.getUser_image(),
                    iv_question_des);
        }else {
            ImageManager.getInstance().loadResImage(UserEditActivity.this,
                    R.drawable.default_user_logo,
                    iv_question_des);
        }

        if (!TextUtils.isEmpty(userInfo.getNickname())) {
            edit_nickname.setText(userInfo.getNickname());
        }

        if (userInfo.getXb().equals("女")) {
            rb_nv.setChecked(true);
        }

        if (!TextUtils.isEmpty(userInfo.getQm())) {
            et_qm.setText(userInfo.getQm());
        }

        if (!TextUtils.isEmpty(userInfo.getEmail())) {
            et_email.setText(userInfo.getEmail());
        }
        tv_phone.setText(userInfo.getPhone());
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_user_info;
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                    if (resultList != null && resultList.size() > 0) {
                        files = new ArrayList<>();
                        File file = new File(resultList.get(0).getPhotoPath());
                        files.add(file);
                        compressWithLs(files);
                    }
                }

                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    Toast.makeText(UserEditActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            };

    private void showPopMenu() {
        initImageLoader(UserEditActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(UserEditActivity.this.findViewById(R.id.rootview), Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        view.findViewById(R.id.ll_qx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        view.findViewById(R.id.ll_xc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig,
                        mOnHanlderResultCallback);
                window.dismiss();
            }
        });

        view.findViewById(R.id.ll_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig,
                        mOnHanlderResultCallback);
                window.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_question_des:
                showPopMenu();
                break;

            case R.id.iv_save:
                sendUserInfo();
                break;

            case R.id.guillotine_hamburger:
                close();
                break;
        }
    }

    private void sendUserInfo() {
        String nickname = edit_nickname.getText().toString();
        String qm = et_qm.getText().toString();
        String email = et_email.getText().toString();

        if (TextUtils.isEmpty(nickname)) {
            FrameManager.getInstance().toastPrompt("昵称不能为空");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            userInfo.setEmail("");
        } else {
            if (checkEmail(email)) {
                userInfo.setEmail(email);
            } else {
                FrameManager.getInstance().toastPrompt("邮箱格式不正确");
                return;
            }
        }

        if (TextUtils.isEmpty(qm)) {
            userInfo.setQm("");
        } else {
            userInfo.setQm(qm);
        }

        userInfo.setNickname(nickname);
        userEditPresenter.userEdit(userInfo, files);
    }

    @Override
    public void showLoading() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在修改身份信息...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void UserEditFail(String msg) {
        closeDialog();
        FrameManager.getInstance().toastPrompt("提交信息失败" + msg);
    }

    @Override
    public void UserEditSuccess() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("提交信息成功");
        EventBus.getDefault().post(0x02);
        DataManager.editUser(userInfo);
        edit = true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_nan) {
            userInfo.setXb("男");
        } else {
            userInfo.setXb("女");
        }
    }

    public static boolean checkEmail(String email) {
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onEventMainThread() {
        Log.e("lcc", "1");
    }

    private void compressWithLs(List<File> files) {
        Luban.get(this).load(files.get(0)).putGear(Luban.THIRD_GEAR).setCompressListener(this).launch();
    }

    @Override
    public void onComStart() {

    }

    @Override
    public void onSuccess(File file) {
        files = new ArrayList<>();
        String author = DataManager.getUserName();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
        String filename = author + dateFormat.format(date) + ".jpg";
        String new_file = newFile + filename;
        FileUtil.copyFile(file.getAbsolutePath(), new_file);

        String server_image = AppConstants.ImagePath + filename;
        File file1 = new File(new_file);
        files.add(file1);
        userInfo.setUser_image(server_image);

        ImageManager.getInstance().loadCircleLocalImage(UserEditActivity.this,
                file.getAbsolutePath(), iv_question_des);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close(){
        if (edit){
            Intent intent =new Intent();
            this.setResult(101, intent);
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
