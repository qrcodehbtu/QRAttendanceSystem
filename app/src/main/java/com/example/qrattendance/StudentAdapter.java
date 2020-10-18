package com.example.qrattendance;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<StudentList> mstudentList;
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public StudentViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.sRoll);
            mTextView2 = itemView.findViewById(R.id.sName);
        }
    }
    public StudentAdapter(ArrayList<StudentList> exampleList) {
        mstudentList = exampleList;
    }
    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        StudentViewHolder evh = new StudentViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        final StudentList currentItem = mstudentList.get(position);
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(),ShowAttendance.class);
                intent.putExtra("fromwhere","admin");
                intent.putExtra("sRoll",currentItem.getText1());
                intent.putExtra("sName",currentItem.getText2());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mstudentList.size();
    }
}

