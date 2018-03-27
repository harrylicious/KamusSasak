package id.haqiqi_studio.kamussasak.Anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import id.haqiqi_studio.kamussasak.R;

/**
 * Created by Gentong on 01/02/2018.
 */

public class AnimationClasses extends Activity {
    private AccelerateInterpolator accelerator;
    private DecelerateInterpolator decelerator;

    public void setAnimationHyperToObject(View v, Context context) {
        Animation hyperjump = AnimationUtils.loadAnimation(context, R.anim.hyperspace_jump);
        hyperjump.setDuration(500);
        v.startAnimation(hyperjump);
    }

    public void FlipIt(ViewGroup viewGroup1, ViewGroup viewGroup2) {
        accelerator = new AccelerateInterpolator();
        decelerator = new DecelerateInterpolator();


        final ViewGroup visibleList ;
        final ViewGroup invisibleList ;

        if (viewGroup1.getVisibility() == View.GONE)

        {
            visibleList = viewGroup2;
            invisibleList = viewGroup1;
        } else

        {
            invisibleList = viewGroup2;
            visibleList = viewGroup1;
        }

        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY", -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }
}


