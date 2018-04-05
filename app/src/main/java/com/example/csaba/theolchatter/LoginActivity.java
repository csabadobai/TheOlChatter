package com.example.csaba.theolchatter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {
    private static final String IP = "192.168.0.102";
    private static final int PORT = 2014;
    public static String username;
    public static Socket socket;

    public void handleSocketMessage(String message) {
        Log.d("Login Activity", message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login);
        final EditText userNameField = findViewById(R.id.username);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userNameField.getText().toString();
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            socket = new Socket(IP, PORT);
                            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String line;
                            if ((line = bufferedReader.readLine()) != null) {
                                LoginActivity.this.handleSocketMessage(line);
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                                bufferedWriter.write(username);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                            }

                            if (socket.isConnected()){
                                Intent intent = new Intent(LoginActivity.this.getApplicationContext(), ChatActivity.class);
                                startActivity(intent);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
