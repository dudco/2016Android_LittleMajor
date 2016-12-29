package com.example.dudco.and_sojoun_pro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by dudco on 2016. 12. 28..
 */

public class BubbleManager {
    private static BubbleManager instance = null;

    private Bubble bubble;
    private WindowManager windowmanager;
    private LayoutInflater inflater;
//    RelativeLayout container;
    private WindowManager.LayoutParams bubble_params = new WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT);
    private WindowManager.LayoutParams chat_params = new WindowManager.LayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT);

    private boolean isSowing = false;
    private boolean isChatSowing = false;

    static BubbleManager getInstance() {
        if(instance == null) instance = new BubbleManager();
        return instance;
    }

    private BubbleManager() {
    }

    void init(final Context context){
        windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        bubble_params.gravity = Gravity.TOP | Gravity.LEFT;
        bubble_params.width = (int)Util.Px2DP(context, 65.0f);
        bubble_params.height = (int)Util.Px2DP(context, 65.0f);
        bubble = new Bubble(context);
        bubble.setImageResource(R.drawable.mj);
        bubble.setParams(bubble_params);
        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isChatSowing) {
                    Intent intent = new Intent(context, ChatAcitivity.class);
                    intent.putExtra("height", bubble_params.height);
                    context.startActivity(intent);
                    isChatSowing = true;
                }else{
                    ChatAcitivity.fFinish();
                    isChatSowing = false;
                }
            }
        });
    }

    public boolean isChatSowing() {
        return isChatSowing;
    }

    public void setChatSowing(boolean chatSowing) {
        isChatSowing = chatSowing;
    }

    WindowManager getWindowmanager() {
        return windowmanager;
    }

    void addBubble(){
        if(bubble != null){
            if(!isSowing){
                windowmanager.addView(bubble, bubble_params);
                isSowing = true;
            }
        }
    }

    void removeBubble(){
        if(bubble != null){
            if(isSowing){
                windowmanager.removeView(bubble);
                isSowing = false;
            }
        }
    }

    public boolean isSowing() {
        return isSowing;
    }
}
