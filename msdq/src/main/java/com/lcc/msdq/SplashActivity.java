package com.lcc.msdq;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lcc.App;
import com.lcc.msdq.login.LoginActivity;
import com.lcc.msdq.login.SignUpActivity;
import com.lcc.utils.SharePreferenceUtil;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zsbpj.lccpj.frame.ImageManager;

public class SplashActivity extends Activity implements View.OnClickListener {
    private LinearLayout ll_bottom_view;
    private String user_tk;
    private ImageView logo_inner_iv;
    boolean isShowingRubberEffect = false;
    private TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        App.addActivity(this);
        user_tk = SharePreferenceUtil.getUserTk();

        app_name = (TextView) findViewById(R.id.app_name);
        logo_inner_iv = (ImageView) findViewById(R.id.logo_inner_iv);
        ImageView ly = (ImageView) findViewById(R.id.ly);
        ll_bottom_view = (LinearLayout) findViewById(R.id.ll_bottom_view);
        findViewById(R.id.tv_toregister).setOnClickListener(this);
        findViewById(R.id.tv_tologin).setOnClickListener(this);
        FrameLayout reveal = (FrameLayout) findViewById(R.id.reveal);
        changePic(ly);
        if (TextUtils.isEmpty(user_tk)) {
            ll_bottom_view.setVisibility(View.VISIBLE);
        } else {
            ll_bottom_view.setVisibility(View.GONE);
        }
        initAnimation();
        //渐隐模式
        //getWelcomeView(reveal);
    }

    private void changePic(ImageView ly){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 6 && hour <= 12) {
            ImageManager.getInstance().loadResImage(SplashActivity.this, R.drawable.morning, ly);
        } else if (hour > 12 && hour <= 18) {
            ImageManager.getInstance().loadResImage(SplashActivity.this, R.drawable.afternoon, ly);
        } else {
            ImageManager.getInstance().loadResImage(SplashActivity.this, R.drawable.night, ly);
        }
    }

    private void getWelcomeView(View view) {
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                JumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    public void JumpNextPage() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_tologin:
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtra("from","welcome");
                startActivity(intent);
                break;

            case R.id.tv_toregister:
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void initAnimation() {
        startLogoInner1();
        startLogoOuterAndAppName();
    }

    private void startLogoInner1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        logo_inner_iv.startAnimation(animation);
    }

    private void startLogoOuterAndAppName() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.8 && !isShowingRubberEffect) {
                    isShowingRubberEffect = true;
                    startShowAppName();
                    finishActivity();
                } else if (fraction >= 0.95) {
                    valueAnimator.cancel();
                    startLogoInner2();
                }

            }
        });
        valueAnimator.start();
    }

    private void startShowAppName() {
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(app_name);
    }

    private void startLogoInner2() {
        YoYo.with(Techniques.RubberBand).duration(1000).playOn(logo_inner_iv);
    }


    private void finishActivity() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (TextUtils.isEmpty(user_tk)){
                            return;
                        }
                        SystemClock.sleep(1500);
                        Intent intent  = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

}
