package com.example.nested_recyclerview_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Message> messagesList;
    private GroupAdapter adapter;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //initilaze adpater
        recyclerView = findViewById(R.id.Group_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messagesList = new ArrayList<>();
        GetDataFromFirebase();


    }

    private void GetDataFromFirebase() {


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("Group");
            myRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    if (datasnapshot.exists()) {
                        messagesList.clear();


                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            Message message = new Message();
                            message.setImageurl(snapshot.child("image").getValue().toString());
                            message.setName(snapshot.child("name").getValue().toString());


                            GenericTypeIndicator<ArrayList<ChildData>> genericTypeIndicator =
                                    new GenericTypeIndicator<ArrayList<ChildData>>() {};

                            message.setChildData(snapshot.child("childData").getValue(genericTypeIndicator));

                            messagesList.add(message);
                        }

                        Collections.shuffle(messagesList);

                        adapter = new GroupAdapter(getApplication(), messagesList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else {
                        //  Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }





}