package com.example.dudco.and_sojoun_pro;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by dudco on 2016. 12. 27..
 */

public class Bubble extends ImageView {
    private Context context;
    private MoveAnimator animator  = new MoveAnimator();

    private WindowManager.LayoutParams params;
    private OnClickListener mOnClickListener;

    public Bubble(Context context) {
        super(context);
        this.context = context;
    }

    public Bubble(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public Bubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bubble(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void startAni_ActionUp(){
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.bubble_click_up);
        animator.setTarget(this);
        animator.start();
    }

    public void startAni_ActionDown(){
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.bubble_click_down);
        animatorSet.setTarget(this);
        animatorSet.start();
    }


    private float initialTouchX;
    private float initialTouchY;
    private float initialX;
    private float initialY;
    private static final int TOUCH_TIME_THRESHOLD = 150;
    private long lastTouchDown;

    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                startAni_ActionDown();
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();

                lastTouchDown = System.currentTimeMillis();

                break;
            case MotionEvent.ACTION_UP:
                startAni_ActionUp();
                goToWall();

                if(System.currentTimeMillis() - lastTouchDown < TOUCH_TIME_THRESHOLD){
                    Log.d("dudco", "Touch!!");
                    goToTopLight();
                    mOnClickListener.onClick(this);
//                    context.startActivity(new Intent(context, ChatActivity.class));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (initialX + (int)(event.getRawX() - initialTouchX));
                int y = (int) (initialY + (int)(event.getRawY() - initialTouchY));

                params.x = x;
                params.y = y;
                BubbleManager.getInstance().getWindowmanager().updateViewLayout(this, params);
                break;
        }
        return true;
    }


    public void goToWall() {
        int middle = Util.getDivSize(context).get("width") / 2;
        float nearestXWall = params.x >= middle ? Util.getDivSize(context).get("width") - this.getWidth() : 0;
//        Log.d("dudco", "param.x : " + this.getX() + " middle : " + middle + "  width : " + width + "  View width");
//        Log.d("animator","start");
        animator.start(nearestXWall, params.y);
    }

    public void goToTopLight(){
        animator.start(Util.getDivSize(context).get("width") - this.getWidth(), 0.0f);
    }

    public void move(float deltaX, float deltaY) {
        params.x += deltaX;
        params.y += deltaY;
//        Log.d("move","start");
        BubbleManager.getInstance().getWindowmanager().updateViewLayout(this, params);
    }

    private class MoveAnimator implements Runnable {
        private Handler handler = new Handler(Looper.getMainLooper());
        private float destinationX;
        private float destinationY;
        private long startingTime;

        private void start(float x, float y) {
            this.destinationX = x;
            this.destinationY = y;
            startingTime = System.currentTimeMillis();
            handler.post(this);
        }

        @Override
        public void run() {
//            Log.d("run","start");
//            Log.d("rootView", rootview.getRootView().getParent()+"");
            if (getRootView() != null && getRootView().getParent() != null) {
                float progress = Math.min(1, (System.currentTimeMillis() - startingTime) / 400f);
                float deltaX = (destinationX -  params.x) * progress;
                float deltaY = (destinationY -  params.y) * progress;
                move(deltaX, deltaY);
                if (progress < 1) {
                    handler.post(this);
                }
            }
        }

        private void stop() {
            handler.removeCallbacks(this);
        }
    }


    public void setOnClickListener(OnClickListener clickListener){
        mOnClickListener = clickListener;
    }
}
