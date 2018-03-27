package com.example.csaba.theolchatter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        List<MessageDTO> fakeMessageList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUsername("phatfenis");
        messageDTO.setDate("29.02.2020 17:25:30");
        messageDTO.setMessage("These nuts");
        fakeMessageList.add(messageDTO);

        RecyclerView recyclerView = findViewById(R.id.msg_list);
        recyclerView.setAdapter(new Adapter(fakeMessageList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        List<MessageDTO> messageList;

        public Adapter(List<MessageDTO> messageList) {
            this.messageList = messageList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.im_item_sent, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MessageDTO messageDTO = messageList.get(position);
            holder.tvUsername.setText(messageDTO.getUsername());
            holder.tvDate.setText(messageDTO.getDate());
            holder.tvMessage.setText(messageDTO.getMessage());
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvUsername;
            TextView tvDate;
            TextView tvMessage;

            public ViewHolder(View itemView) {
                super(itemView);
                tvUsername = itemView.findViewById(R.id.msg_username);
                tvDate = itemView.findViewById(R.id.msg_date);
                tvMessage = itemView.findViewById(R.id.msg_message);
            }
        }
    }
}
