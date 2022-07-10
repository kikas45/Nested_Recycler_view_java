package com.example.nested_recyclerview_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHoler_memberr> {
    ArrayList<ChildData> arrayList_memember;
    Context conextM;

    public MemberAdapter(ArrayList<ChildData> arrayList_memember, Context conextM) {
        this.arrayList_memember = arrayList_memember;
        this.conextM = conextM;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHoler_memberr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
        return new MemberAdapter.ViewHoler_memberr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHoler_memberr holder, int position) {
        ///set member TextView


        holder.textView.setText(arrayList_memember.get(position).getName());

        holder.textView.setText(arrayList_memember.get(position).name);
        Glide.with(conextM)
                .load(arrayList_memember.get(position).getImageurl()).fitCenter().into(holder.ivChild);

    }

    @Override
    public int getItemCount() {
        return arrayList_memember.size();
    }

    public class ViewHoler_memberr extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView ivChild;
        public ViewHoler_memberr(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name_member);
            ivChild = itemView.findViewById(R.id.ivChild);
        }
    }
}
