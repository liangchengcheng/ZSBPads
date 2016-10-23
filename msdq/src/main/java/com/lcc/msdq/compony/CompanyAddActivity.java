package com.lcc.msdq.compony;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lcc.AppConstants;
import com.lcc.base.BaseActivity;
import com.lcc.entity.CompanyDescription;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.AreaDialogFragment;
import com.lcc.msdq.test.answer.photo.UILImageLoader;
import com.lcc.msdq.test.answer.photo.UILPauseOnScrollListener;
import com.lcc.mvp.presenter.ComDesAddPresenter;
import com.lcc.mvp.presenter.impl.ComDesAddPresenterImpl;
import com.lcc.mvp.view.ComDesAddView;
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
import java.util.Map;
import java.util.regex.Pattern;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
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
 * Description:  CompanyAddActivity
 */
public class CompanyAddActivity extends BaseActivity implements ComDesAddView, View.OnClickListener,
        OnCompressListener, AreaDialogFragment.CodeListener, AreaDialogFragment.StringListener {
    public FunctionConfig functionConfig;
    public static final String NAME = "name";
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private String name;
    private Pattern intPattern = Pattern.compile("^[-\\+]?[\\d]*\\.0*$");
    private List<File> files = new ArrayList<>();
    private ComDesAddPresenter presenter;
    private static final String newFile = Propertity.newFile;
    private String location;

    private EditText tv_position;
    private EditText editText_name, editText_phone, editText_add, editText_summary;
    private SimpleArcDialog mDialog;
    private ImageView iv_question_des;
    private CompanyDescription companyDescription;

    @Override
    protected int getLayoutView() {
        return R.layout.com_des_add;
    }

    @Override
    protected void initView() {
        presenter = new ComDesAddPresenterImpl(this);
        companyDescription = new CompanyDescription();
        iv_question_des = (ImageView) findViewById(R.id.iv_question_des);
        iv_question_des.setOnClickListener(this);
        findViewById(R.id.tv_add_pic).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        tv_position = (EditText) findViewById(R.id.tv_position);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_phone = (EditText) findViewById(R.id.editText_phone);
        editText_add = (EditText) findViewById(R.id.editText_add);
        editText_summary = (EditText) findViewById(R.id.editText_summary);
        initData();
    }

    private void initData() {
        name = getIntent().getStringExtra(NAME);
        editText_name.setText(name);
        initPic();
    }

    private void initPic() {
        ThemeConfig themeConfig = ThemeConfig.GREEN;
        functionConfig = new FunctionConfig.Builder().setEnableCamera(true).setEnableEdit(true)
                .setEnableCrop(true).setEnableRotate(true).setCropSquare(true).setEnablePreview(true)
                .setMutiSelectMaxSize(1).build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new UILImageLoader(), themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true)).build();
        GalleryFinal.init(coreConfig);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    public String getString(Map map, String key, String defaultValue) {
        Object obj = map.get(key);
        return obj == null ? defaultValue : (obj instanceof Number &&
                intPattern.matcher(obj.toString()).matches() ?
                String.valueOf(Long.valueOf(((Number) obj).longValue())) : obj.toString());
    }

    @Override
    public void adding() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在提交数据...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void addSuccess() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("发布成功");
    }

    @Override
    public void addFail() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("发布失败");
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showPopMenu() {
        initImageLoader(CompanyAddActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(CompanyAddActivity.this.findViewById(R.id.rootview), Gravity.BOTTOM, 0, 0);
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
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                window.dismiss();
            }
        });

        view.findViewById(R.id.ll_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                window.dismiss();
            }
        });
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
                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                    if (resultList != null && resultList.size() > 0) {
                        files = new ArrayList<>();
                        File file = new File(resultList.get(0).getPhotoPath());
                        files.add(file);
                        compressWithLs(files);
                    }
                }

                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    Toast.makeText(CompanyAddActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_question_des:
            case R.id.tv_add_pic:
                showPopMenu();
                break;

            case R.id.buttonSignUp:
                if (TextUtils.isEmpty(editText_name.getText().toString()) ||
                        TextUtils.isEmpty(editText_phone.getText().toString()) ||
                        TextUtils.isEmpty(editText_add.getText().toString()) ||
                        TextUtils.isEmpty(editText_summary.getText().toString())) {
                    FrameManager.getInstance().toastPrompt("请将数据补充完整");
                    return;
                }

                adding();
                companyDescription.setAuthor("");
                companyDescription.setCompany_name(editText_name.getText().toString());
                companyDescription.setCompany_phone(editText_phone.getText().toString());
                companyDescription.setLocation(location + editText_add.getText().toString());
                companyDescription.setCompany_description(editText_summary.getText().toString());
                presenter.ComDesAdd(companyDescription, files);
                break;

            case R.id.btn_search:
                showLoginDialog();
                break;
        }
    }

    /**
     * 压缩图片
     */
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
        companyDescription.setCompany_image(server_image);

        ImageManager.getInstance().loadLocalImage(CompanyAddActivity.this,
                file.getAbsolutePath(), iv_question_des);
    }

    @Override
    public void onError(Throwable e) {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void showLoginDialog() {
        AreaDialogFragment dialog = new AreaDialogFragment();
        dialog.setOnCodeListener(this);
        dialog.setOnStringListener(this);
        dialog.show(getFragmentManager(), "loginDialog");
    }

    @Override
    public void onCodeInputComplete(String message) {
        companyDescription.setAreaid(message);
    }

    @Override
    public void onStringInputComplete(String message) {
        if (!TextUtils.isEmpty(message)) {
            String[] location_str = message.split("=");
            companyDescription.setProvince(location_str[0]);
            companyDescription.setCity(location_str[1]);
            companyDescription.setArea(location_str[2]);
            location = message.replace("=", "");
            tv_position.setText(message.replace("=", ""));
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
