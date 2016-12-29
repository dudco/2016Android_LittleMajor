package com.example.dudco.and_sojoun_pro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Bubble bubble;
    private static final int OVERAY_REQ_PERMISSION = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubbleManager.getInstance().init(getApplicationContext());

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.canDrawOverlays(MainActivity.this)){
                    if(!BubbleManager.getInstance().isSowing())
                        BubbleManager.getInstance().addBubble();
                    else
                        BubbleManager.getInstance().removeBubble();
//                    else
//                        bubble.removeBubble();
                }else{
                    requestPermission(OVERAY_REQ_PERMISSION);
                }
            }
        });
    }

    public void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case OVERAY_REQ_PERMISSION:{
                    if(!Util.canDrawOverlays(this)){
                        Toast.makeText(this, "권한 설정이 필요합니다!", Toast.LENGTH_SHORT).show();
                    }else{
                        BubbleManager.getInstance().addBubble();
                    }
                }
            }
        }
    }
}
