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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.Calendar;

import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.api.WorkEntryService;
import it.spot.android.timespot.api.request.WorkEntryNewRequest;
import it.spot.android.timespot.api.response.WorkEntryNewResponse;
import it.spot.android.timespot.databinding.ActivityWorkEntryNewBinding;
import it.spot.android.timespot.storage.IStorage;
import it.spot.android.timespot.storage.Storage;
import it.spot.android.timespot.support.GUIUtils;
import it.spot.android.timespot.support.OnRevealAnimationListener;
import it.spot.android.timespot.support.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_work_entry_new, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            save();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
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

    private void save() {

        IStorage storage = Storage.init(this);

        WorkEntryNewRequest request = new WorkEntryNewRequest()
                .set_client("54e306309f11ec0b003510e6")
                .set_performedBy(storage.getLoggedUser().get_id())
                .set_project("57a2f1a20e3c530f006f49a3")
                .setDescription(mBinding.editDescription.getText().toString())
                .setAmount(Float.valueOf(mBinding.editTime.getText().toString()))
                .setPerformedAt(Utils.Date.formatDateInServerFormat(Calendar.getInstance()));

        TimeEndpoint.getInstance(this)
                .create(WorkEntryService.class)
                .create(storage.getCurrentOrganizationId(), request)
                .enqueue(new Callback<WorkEntryNewResponse>() {

                    @Override
                    public void onResponse(Call<WorkEntryNewResponse> call, Response<WorkEntryNewResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(WorkEntryNewActivity.this, "saved - " + response.body().get_id(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(WorkEntryNewActivity.this, String.valueOf(response.code()) + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkEntryNewResponse> call, Throwable t) {
                        Toast.makeText(WorkEntryNewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // endregion
}
