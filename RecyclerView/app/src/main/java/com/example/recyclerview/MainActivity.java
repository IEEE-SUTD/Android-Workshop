package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItem> itemList = new ArrayList<>();
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));
        itemList.add(new ListItem("Intro", "About Me"));
        itemList.add(new ListItem("Education", "School Related Activities and Academics"));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}