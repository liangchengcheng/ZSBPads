package com.lcc.msdq.compony.question;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lcc.AppConstants;
import com.lcc.base.BaseActivity;
import com.lcc.entity.ComTestAdd;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.test.answer.photo.UILImageLoader;
import com.lcc.msdq.test.answer.photo.UILPauseOnScrollListener;
import com.lcc.mvp.presenter.ComQuesAddPresenter;
import com.lcc.mvp.presenter.impl.ComQuesAddPresenterImpl;
import com.lcc.mvp.view.ComQuesAddView;
import com.lcc.utils.FileUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;
import zsbpj.lccpj.yasuo.Luban;
import zsbpj.lccpj.yasuo.OnCompressListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  添加公司问题的界面
 */
public class ComquestionAddActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, ComQuesAddView, OnCompressListener {
    public static final String ID = "id";
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private static final String newFile = Propertity.newFile;
    public FunctionConfig functionConfig;
    private ComTestAdd comTestAdd = new ComTestAdd();
    private List<String> dataset;
    private String fid;

    private ImageView iv_question_des;
    private SimpleArcDialog mDialog;
    private ComQuesAddPresenter presenter;
    private List<File> files = new ArrayList<>();
    private EditText editText_title;
    private EditText editText_summary;

    public static void startComquestionActivity(String id, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, ComquestionAddActivity.class);
        intent.putExtra(ID, id);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        fid = getIntent().getStringExtra(ID);
        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_summary = (EditText) findViewById(R.id.editText_summary);
        presenter = new ComQuesAddPresenterImpl(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        iv_question_des = (ImageView) findViewById(R.id.iv_question_des);
        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        dataset = new LinkedList<>(Arrays.asList(Propertity.OPTIONS.ZYZS, Propertity.OPTIONS.RSZS,
                Propertity.OPTIONS.QT));
        comTestAdd.setType(dataset.get(0));

        niceSpinner.attachDataSource(dataset);
        niceSpinner.setTextColor(Color.BLACK);
        niceSpinner.setLinkTextColor(Color.BLACK);
        niceSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.tv_add_pic).setOnClickListener(this);
        findViewById(R.id.iv_question_des).setOnClickListener(this);

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
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.company_question_add_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_question_des:
            case R.id.tv_add_pic:
                showPopMenu();
                break;

            case R.id.buttonSignUp:
                if (TextUtils.isEmpty(editText_title.getText().toString()) ||
                        TextUtils.isEmpty(editText_summary.getText().toString())) {
                    FrameManager.getInstance().toastPrompt("有未填写的数据");
                    return;
                }

                adding();
                comTestAdd.setAuthor("18813149871");
                comTestAdd.setCom_id(fid);
                comTestAdd.setSummary(editText_summary.getText().toString());
                comTestAdd.setTitle(editText_title.getText().toString());
                presenter.ComQuesAdd(comTestAdd, files);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        comTestAdd.setType(dataset.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showPopMenu() {
        initImageLoader(ComquestionAddActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(ComquestionAddActivity.this.findViewById(R.id.rootview),
                Gravity.BOTTOM, 0, 0);
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
                    Toast.makeText(ComquestionAddActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            };

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
        comTestAdd.setImg_url(server_image);

        ImageManager.getInstance().loadLocalImage(ComquestionAddActivity.this,
                file.getAbsolutePath(), iv_question_des);
    }

    @Override
    public void onError(Throwable e) {

    }

    private void compressWithLs(List<File> files) {
        Luban.get(this).load(files.get(0)).putGear(Luban.THIRD_GEAR).setCompressListener(this).launch();
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
