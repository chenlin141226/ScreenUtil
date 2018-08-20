package com.example.user.screenutil.startpage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.user.screenutil.MainActivity;
import com.example.user.screenutil.R;
import com.example.user.screenutil.utils.SharedPreferencesUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class WelcomeActivity extends AppCompatActivity {

    private static final int[] imgs = {R.drawable.welcomimg1, R.drawable.welcomimg2,
            R.drawable.welcomimg3, R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7, R.drawable.welcomimg8,
            R.drawable.welcomimg9, R.drawable.welcomimg10,
            R.drawable.welcomimg11, R.drawable.welcomimg12};
    @BindView(R.id.iv_entry)
    ImageView iv_entry;

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean isFirst = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        //如果是第一次，进入引导页
        if (isFirst) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //如果不是第一次正常显示
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        StartMainActivity();
    }

    private void StartMainActivity() {
        //Timer 是发送一次 interval 是周期性发送
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        iv_entry.setImageResource(imgs[random.nextInt(imgs.length)]);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          startAnim();
                      }
                  });

    }

    private void startAnim() {
        //进入MainActivity的动画
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_entry,"scaleX",1f,SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_entry,"scaleY",1f,SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();
       set.addListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationEnd(Animator animation) {
               Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       });
    }
}
