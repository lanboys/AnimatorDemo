package com.bing.lan.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler mHandler = new Handler();
    private RelativeLayout mRelativeLayout;
    private FrameLayout animation_viewGroup;
    private Drawable mDrawable;
    private int mAnimationDuration = 5000;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        mTextView = (TextView) findViewById(R.id.tv);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();

                int[] end_location = new int[2];
                fab.getLocationInWindow(end_location);

                int[] start_location = new int[2];
                mTextView.getLocationInWindow(start_location);

                // start_location[0]---- 48----
                //         ---- start_location[1]---- 870----
                //         ---- end_location[0]---- 864----
                //         ---- end_location[1]---- 1704----

                start_location[0] = 100;
                start_location[1] = 1000;
                end_location[0] = 600;
                end_location[1] = 1500;

                doAnimator(start_location, end_location, null);

                //showAnimation(view);

            }
        });

        mDrawable = getResources().getDrawable(R.drawable.adddetail);
        createAnimLayout(this);
    }

    private void showAnimation(View view) {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, -2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("onAnimation", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("onAnimation", "onAnimationEnd");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(set);
    }

    public void createAnimLayout(Activity activity) {

        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(activity);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        // animLayout.setBackgroundResource(R.color.yellow12);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);

        animation_viewGroup = animLayout;
    }

    /**
     * 动画效果设置
     *
     * @param start_location 起始位置
     */
    @SuppressLint("NewApi")
    public void doAnimator(int[] start_location, int[] end_location, final ShopCarAnimationListener listener) {

        final ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(mDrawable);
        imageView.setAlpha(0.6f);
        animation_viewGroup.addView(imageView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = start_location[0];
        lp.topMargin = start_location[1];
        imageView.setPadding(5, 5, 5, 5);
        imageView.setLayoutParams(lp);

        //int x = end_location[0] - start_location[0];
        //int y = end_location[1] - start_location[1];

        int endX = end_location[0] - start_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标

        Log.e("doAnimator()", "----start_location[0]----" + start_location[0] + "----");
        Log.e("doAnimator()", "----start_location[1]----" + start_location[1] + "----");
        Log.e("doAnimator()", "----end_location[0]----" + end_location[0] + "----");
        Log.e("doAnimator()", "----end_location[1]----" + end_location[1] + "----");

        List<Animator> animators = new ArrayList<>();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.2f, 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.2f, 0.6f);

        //http://blog.csdn.net/gzejia/article/details/51063564
        //加速器
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "translationX", 0, endX);
        translationX.setInterpolator(new LinearInterpolator());

        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0, 100, 0, 100, 0, endY);
        //ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0, endY);

        //translationY.setInterpolator(new AnticipateInterpolator());
        translationY.setInterpolator(new ShopCarInterpolator());
        // translationY.setInterpolator(new CycleInterpolator(12));
        //translationY.setInterpolator(new LinearInterpolator());
        //translationY.setInterpolator(new DecelerateInterpolator());

        translationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                Log.e("onAnimationUpdate()", "----distance----" + (distance + 1000.0f) + "----");
            }
        });

        //translationX.setRepeatCount(ValueAnimator.INFINITE);
        //translationX.setRepeatMode(ValueAnimator.REVERSE);
        //translationX.setDuration(mAnimationDuration);

        //animators.add(scaleX);
        //animators.add(scaleY);
        animators.add(translationY);
        animators.add(translationX);

        //translationY.start();
        //translationX.start();

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        final boolean[] isEnd = {false};
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //
        //        while (!isEnd[0]) {
        //            try {
        //                Thread.sleep(30);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //
        //            if (imageView != null) {
        //                int[] end_location = new int[2];
        //                imageView.getLocationInWindow(end_location);
        //                // Log.e("run()", "----end_location[0]----" + end_location[0] + "----");
        //                Log.e("run()", "----end_location[1]----" + end_location[1] + "----");
        //            }
        //        }
        //    }
        //}).start();

        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isEnd[0] = true;
                long duration = animation.getDuration();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        mAnimatorSet.setDuration(mAnimationDuration);
        mAnimatorSet.playTogether(animators);

        mAnimatorSet.start();
    }

    //public void startMove() {
    //    ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
    //    animator.setDuration(3 * 1000);
    //    animator.setInterpolator(new DecelerateInterpolator());
    //    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    //        @Override
    //        public void onAnimationUpdate(ValueAnimator animation) {
    //            float distance = (float) animation.getAnimatedValue();
    //            //tan[0]是邻边 tan[1]是对边
    //            pathMeasure.getPosTan(distance, pos, tan);
    //            postInvalidate();
    //        }
    //    });
    //    animator.start();
    //}
}
