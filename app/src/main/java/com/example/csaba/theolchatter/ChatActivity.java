package com.example.csaba.theolchatter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.csaba.theolchatter.LoginActivity.socket;
import static com.example.csaba.theolchatter.LoginActivity.username;

public class ChatActivity extends AppCompatActivity {
    public List<MessageDTO> messageList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final MessageDTO messageDTO = new MessageDTO();
        final Gson gson = new Gson();

        RecyclerView recyclerView = findViewById(R.id.msg_list);
        recyclerView.setAdapter(new Adapter(messageList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button send = findViewById(R.id.bt_send_message);
        final EditText message = findViewById(R.id.et_message);

        new Thread() {

            @Override
            public void run() {
                BufferedReader bufferedReader;

                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    String message;
                    while ((message = bufferedReader.readLine()) != null) {
                        JsonParser jsonParser = new JsonParser();
                        JsonObject jsonObject = (JsonObject) jsonParser.parse(message);
                        MessageDTO messageModel = gson.fromJson(jsonObject.toString(), MessageDTO.class);
                        messageList.add(messageModel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                messageDTO.setMessage(message.getText().toString());
                messageDTO.setDate(timestamp.toString());
                messageDTO.setUsername(username);
                messageList.add(messageDTO);

                try {
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(messageDTO.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                message.setText("");
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        List<MessageDTO> messageList;

        Adapter(List<MessageDTO> messageList) {
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

            ViewHolder(View itemView) {
                super(itemView);
                tvUsername = itemView.findViewById(R.id.msg_username);
                tvDate = itemView.findViewById(R.id.msg_date);
                tvMessage = itemView.findViewById(R.id.msg_message);
            }
        }
    }
}

