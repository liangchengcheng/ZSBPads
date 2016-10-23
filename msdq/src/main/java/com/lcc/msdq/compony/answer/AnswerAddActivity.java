package com.lcc.msdq.compony.answer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.lcc.base.BaseActivity;
import com.lcc.entity.AnswerAdd;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.PhotoPickerActivity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.ComAnswerAddPresenter;
import com.lcc.mvp.presenter.TestAnswerAddPresenter;
import com.lcc.mvp.presenter.impl.ComAnswerAddPresenterImpl;
import com.lcc.mvp.presenter.impl.TestAnswerAddPresenterImpl;
import com.lcc.mvp.view.ComAnswerAddView;
import com.lcc.utils.CoCoinToast;
import com.lcc.utils.FileUtil;
import com.lcc.utils.HTMLContentUtil;
import com.lcc.view.edit.editor.SEditorData;
import com.lcc.view.edit.editor.SortRichEditor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;
import zsbpj.lccpj.yasuo.Luban;
import zsbpj.lccpj.yasuo.OnCompressListener;

public class AnswerAddActivity extends BaseActivity implements View.OnClickListener, OnCompressListener,
        ComAnswerAddView {
    public static final int REQUEST_CODE_PICK_IMAGE = 1023;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private static final String newFile = Environment.getExternalStorageDirectory().getPath() + "/com.lcc.mstdq/";
    private File mCurrentPhotoFile;
    private List<File> files = new ArrayList<>();
    private ComAnswerAddPresenter presenter;
    private AnswerAdd answerAdd = new AnswerAdd();
    private String fid;
    private int pic_num;

    private TextView tvSort;
    private SortRichEditor editor;
    private ImageView ivGallery, ivCamera;
    private Button btnPosts;
    private SimpleArcDialog mDialog;

    public Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ComAnswerAddPresenterImpl(this);
        fid = getIntent().getStringExtra("fid");
        answerAdd.setFid(fid);

        tvSort = (TextView) findViewById(R.id.tv_sort);
        editor = (SortRichEditor) findViewById(R.id.richEditor);
        ivGallery = (ImageView) findViewById(R.id.iv_gallery);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        btnPosts = (Button) findViewById(R.id.btn_posts);
        tvSort.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        btnPosts.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.answer_add_activity;
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private void dealEditData(final List<SEditorData> editList) {
        if (editList == null || editList.size() == 0) {
            FrameManager.getInstance().toastPrompt("暂无数据");
            closeDialog();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String html = HTMLContentUtil.getContent(editList);
                answerAdd.setAnswer(html);
                files = HTMLContentUtil.getFiles(editList);
                if (files != null && files.size() > 0) {
                    pic_num = files.size();
                    compressWithLs(files);
                } else {
                    answerAdd.setAnswer(html);
                    presenter.ComAnswerAdd(answerAdd, files);
                }
            }
        }).start();
    }

    private void openCamera() {
        try {
            PHOTO_DIR.mkdirs();
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH:mm:ss");
        String author = DataManager.getUserName();
        return author + dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (editor.isSort()) tvSort.setText("排序");
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            String[] photoPaths = data.getStringArrayExtra(PhotoPickerActivity.INTENT_PHOTO_PATHS);
            File filepath = new File(newFile);
            if (!filepath.exists()) {
                try {
                    filepath.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String[] new_photoPaths = new String[photoPaths.length];
            for (int i = 0; i < photoPaths.length; i++) {
                String author = DataManager.getUserName();
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
                String new_file = newFile + author + dateFormat.format(date) + i + ".jpg";
                FileUtil.copyFile(photoPaths[i], new_file);
                new_photoPaths[i] = new_file;
            }
            editor.addImageArray(new_photoPaths);
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            editor.addImage(mCurrentPhotoFile.getAbsolutePath());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sort:
                if (editor.sort()) {
                    tvSort.setText("完成");
                } else {
                    tvSort.setText("排序");
                }
                break;
            case R.id.iv_gallery:
                startActivityForResult(new Intent(this, PhotoPickerActivity.class), REQUEST_CODE_PICK_IMAGE);
                break;
            case R.id.iv_camera:
                openCamera();
                break;
            case R.id.btn_posts:
                adding();
                List<SEditorData> editList = editor.buildEditData();
                dealEditData(editList);
                break;
        }
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
        FrameManager.getInstance().toastPrompt("提交成功");
    }

    @Override
    public void addFail() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("提交失败");
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void compressWithLs(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            Luban.get(this).load(files.get(i)).putGear(Luban.THIRD_GEAR).setCompressListener(this).launch();
        }
    }

    @Override
    public void onComStart() {

    }

    @Override
    public void onSuccess(File file) {
        pic_num--;
        if (pic_num == 0) {
            FrameManager.getInstance().toastPrompt("开始上传");
            presenter.ComAnswerAdd(answerAdd, files);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void checkToken() {
        getToken();
    }
}

