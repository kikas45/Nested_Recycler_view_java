package com.example.nested_recyclerview_sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class GroupAdapter  extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Message> arrayList_groupAdapter;

    public GroupAdapter(Context context, ArrayList<Message> arrayList_groupAdapter) {
        this.context = context;
        this.arrayList_groupAdapter = arrayList_groupAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList_groupAdapter.get(position).name);
        Glide.with(context)
                .load(arrayList_groupAdapter.get(position).getImageurl()).fitCenter().into(holder.imageView);


        LinearLayoutManager linearLayoutManager_member = new LinearLayoutManager(context.getApplicationContext());
        holder.recyclerView_memebr.setLayoutManager(linearLayoutManager_member);
        holder.recyclerView_memebr.setHasFixedSize(true);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference().child("Group");

        ArrayList<ChildData> arrayList_Member = new ArrayList<>();



        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    arrayList_Member.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChildData message = new ChildData();
                        message.setImageurl(snapshot.child("image").getValue().toString());
                        message.setName(snapshot.child("name").getValue().toString());
                        arrayList_Member.add(message);
                    }

                    Collections.shuffle(arrayList_Member);

                    MemberAdapter adapter = new MemberAdapter(arrayList_Member, context);


                    holder.recyclerView_memebr.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    //  Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList_groupAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView_memebr;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_name_group);
            recyclerView_memebr = itemView.findViewById(R.id.member_recyclerView);
            imageView = itemView.findViewById(R.id.eachParentIV);
        }
    }
}
