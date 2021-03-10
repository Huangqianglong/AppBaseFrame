package com.hql.appbaseframe.base.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.hql.appbaseframe.R;


/**
 * @author ly-huangql
 * <br /> Create time : 2021/2/26
 * <br /> Description :
 */
public class AnimUtil {


//    ObjectAnimator mObjectAnimator;
//    TimeInterpolator mTimeInterpolator;

    private static class AnimUtilHolder {
        private static AnimUtil mInstance = new AnimUtil();
    }

    public static AnimUtil getInstance() {
        return AnimUtilHolder.mInstance;
    }

    public void enterAnimation(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", -330f, 0f);
        TimeInterpolator timeInterpolator = new AccelerateDecelerateInterpolator();
        objectAnimator.setInterpolator(timeInterpolator);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0 ,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator,alpha);
        animatorSet.setDuration( view.getContext().getResources().getInteger(R.integer.card_flip_time_full)*2);
        animatorSet.start();
        //LoggerUtil.d("hql"," 执行动画 enterAnimation："+System.currentTimeMillis());
    }

    public void showCardDown(View view) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -30f, 0f);
        TimeInterpolator mTimeInterpolator = new DecelerateInterpolator();
        translationY.setInterpolator(mTimeInterpolator);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0 ,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationY,alpha);
        animatorSet.setDuration(600);
        animatorSet.start();
    }
}
