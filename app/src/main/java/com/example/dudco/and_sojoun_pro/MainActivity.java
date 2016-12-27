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

        bubble = new Bubble(this);

        BitmapDrawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.mj, getTheme());
        }
        Bitmap bitmap = drawable.getBitmap();
        bubble.setImageBitmap(bitmap);
        bubble.setWindowManager((WindowManager) getSystemService(WINDOW_SERVICE));

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (canDrawOverlays(MainActivity.this)){
                    if(!bubble.isRunning())
                        bubble.addBubble();
                    else
                        bubble.removeBubble();
                }else{
                    requestPermission(OVERAY_REQ_PERMISSION);
                }
            }
        });
    }

    public static boolean canDrawOverlays(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }else{
            return Settings.canDrawOverlays(context);
        }
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
                    if(!canDrawOverlays(this)){
                        Toast.makeText(this, "권한 설정이 필요합니다!", Toast.LENGTH_SHORT).show();
                    }else{
                        bubble.addBubble();
                    }
                }
            }
        }
    }
}
