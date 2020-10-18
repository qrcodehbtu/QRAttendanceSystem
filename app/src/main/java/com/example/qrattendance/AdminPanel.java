package com.example.qrattendance;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminPanel extends AppCompatActivity  {
    private Spinner st_branch,st_year;
    private String branch ="";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String yearno="";
    private  Button searchattendance;
    private  String[] branchesname = { "SELECT BRANCH" ,"Computer Science", "Information Technology", "Chemical Engineering", "Mechnanical Engineering", "Civil Engineering" ,
            "Paint Technology", " Plastic Technology" };
    private String [] year = { "YEAR","1","2","3","4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Spinner  st_year =(Spinner) findViewById(R.id.st_year);
        Spinner st_branch = (Spinner)  findViewById(R.id.stbranch);
        searchattendance = (Button) findViewById(R.id.check_attendance);
        setspinnerview(st_branch,st_year);
        searchattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
  private  void validate()
 {
        if(branch.equals("SELECT BRANCH") )
     Toast.makeText(this, "Select Your branch.", Toast.LENGTH_SHORT).show();
     else if(yearno.equals("YEAR"))
     Toast.makeText(this, "Select Your Year.", Toast.LENGTH_SHORT).show();
     else 
         showclass();
 }

    private void showclass() {
        final DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<StudentList> mstudentList = new ArrayList<>();
            for(final DataSnapshot ds: snapshot.child("attendance").child(yearno).child(branch).getChildren())
            {
                students studentdata=snapshot.child("students").child(ds.getKey().toString()).getValue(students.class);
                final String studentName = studentdata.getName();
                mstudentList.add(new StudentList(ds.getKey().toString(), studentName));
            }
                mRecyclerView = findViewById(R.id.showBranch);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(AdminPanel.this);
                mAdapter = new StudentAdapter(mstudentList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setspinnerview(Spinner spin,Spinner st_year)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_view_text, branchesname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch=   branchesname[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, R.layout.simple_view_text, year);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        st_year.setAdapter(adapter_year);
        st_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearno=year[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}