package com.example.dudco.and_sojoun_pro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dudco on 2016. 12. 29..
 */

public class ChatAcitivity extends Activity {

    public static Activity chatActivity;
    List<String> chats = new ArrayList<>();
    ListView chatList;
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatActivity = ChatAcitivity.this;

        chatList = (ListView) findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.edit);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chats.add(editText.getText().toString());
                editText.setText("");
                chatList.setAdapter(new MyAdapter());
            }
        });

        chatList.setAdapter(new MyAdapter());

    }

    public static void fFinish(){
        chatActivity.finish();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return chats.size();
        }

        @Override
        public Object getItem(int position) {
            return chats.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatbox, null);

            TextView text = (TextView) view.findViewById(R.id.text_item);
            text.setText(chats.get(position));

            return view;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BubbleManager.getInstance().setChatSowing(false);
    }
}
