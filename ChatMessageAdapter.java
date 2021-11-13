package com.example.yochef.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yochef.Model.ChatMessage;
import com.example.yochef.R;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private static final int MY_MESSAGE = 0;
    private static final int BOT_MESSAGE = 1;

    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        super(context, R.layout.user_query_layout, data);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage item = getItem(position);

        if (item.isMine() && !item.isImage()) {
            return MY_MESSAGE;
        }
        else  {
            return BOT_MESSAGE;
        }
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {

        int viewType = getItemViewType(position);

        if (viewType == MY_MESSAGE) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.user_query_layout, parent, false);

            TextView textView = itemView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());

        }
        else if (viewType == BOT_MESSAGE) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.bots_reply_layout, parent, false);

            TextView textView =  itemView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }

        itemView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });
        return itemView;
    }
}
