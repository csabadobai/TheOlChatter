package com.example.csaba.theolchatter;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    public List<UserDTO> userList = new ArrayList<>();
    public DrawerLayout drawerLayout;
    public RecyclerView.Adapter adapter;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        drawerLayout = findViewById(R.id.drawer_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new RecyclerView.Adapter(userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        NavigationView navigationView = findViewById(R.id.user_menu);

        ListView listView = findViewById(R.id.users_list);

        listView.getAdapter();
    }

}
