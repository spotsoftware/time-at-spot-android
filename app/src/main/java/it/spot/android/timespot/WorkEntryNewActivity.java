package it.spot.android.timespot;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import it.spot.android.timespot.databinding.ActivityWorkEntryNewBinding;
import it.spot.android.timespot.support.GUIUtils;
import it.spot.android.timespot.support.OnRevealAnimationListener;

/**
 * @author a.rinaldi
 */
public class WorkEntryNewActivity
        extends AppCompatActivity {

    private ActivityWorkEntryNewBinding mBinding;

    // region Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_work_entry_new);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        } else {
            initViews();
        }
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            GUIUtils.animateRevealHide(this, mBinding.contentWrapper, android.R.color.white, mBinding.animatedFabButton.getWidth() / 2,
                    new OnRevealAnimationListener() {

                        @Override
                        public void onRevealHide() {
                            mBinding.content.setVisibility(View.INVISIBLE);
                            WorkEntryNewActivity.super.onBackPressed();
                        }

                        @Override
                        public void onRevealShow() {

                        }
                    });
        } else {
            super.onBackPressed();
        }
    }

    // endregion

    // region Private methods

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow(mBinding.contentWrapper);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        GUIUtils.animateRevealShow(this, mBinding.contentWrapper, mBinding.animatedFabButton.getWidth() / 2, R.color.colorAccent,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }

    private void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(WorkEntryNewActivity.this, android.R.anim.fade_in);
                animation.setDuration(300);
                mBinding.content.startAnimation(animation);
                mBinding.content.setVisibility(View.VISIBLE);
                mBinding.content.setBackgroundColor(Color.WHITE);
                mBinding.editDescription.setMovementMethod(new ScrollingMovementMethod());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(getResources().getInteger(R.integer.animation_duration));
    }

    // endregion
}
