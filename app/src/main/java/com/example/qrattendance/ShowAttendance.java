package com.example.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowAttendance extends AppCompatActivity {
    private RecyclerView recycler_view;
    private String rollno;
    private  Sharedpref pref;
    private TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        tv1=(TextView) findViewById(R.id.textName);
        tv2=(TextView) findViewById(R.id.textRollNo);
        Log.d("coming to activity", "true");
        Bundle extras = getIntent().getExtras();
        String fromWhere=null;
        if (extras != null) {
            fromWhere = extras.getString("fromwhere");
            Log.d("gettingdata", "true");
            //The key argument here must match that used in the other activity
        }
        Log.d("data", fromWhere);
        if(fromWhere.equals("student"))
        {
            Log.d("studentdata","succesful" );
            pref = new Sharedpref(getApplicationContext());
            HashMap<String, String> user = pref.getuserdetails();
            String name = user.get(Sharedpref.KEY_NAME);
            String year = user.get(Sharedpref.KEY_year);
            String roll =user.get(Sharedpref.KEY_rollno);
            rollno=roll;
            tv1.setText("Student name - "+name);
            tv2.setText("Roll no - " +roll);
            Log.d("studentdata1","succesful" );
            usingRecyclerView(rollno);
            Log.d("studentdata2","succesful" );
        }
        else if(fromWhere.equals("admin"))
        {

        }
        else
        {

        }

    }
    public void usingRecyclerView(final String rollno)
    {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_Expand);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        final DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students studentdata=snapshot.child("students").child(rollno).getValue(students.class);
                final String yearno = studentdata.getYear();
                final String branch = studentdata.getBranch();
                Log.d("year",yearno);
                Log.d("branch",branch);
                final List<ParentList> Parent = new ArrayList<>();
                for(final DataSnapshot ds: snapshot.child("attendance").child(yearno).child(branch).child(rollno).getChildren())
                {
                    if(ds.getKey().equals("rollno:"))
                        continue;
                    final String ParentKey=ds.getKey().toString();
                    final String attendanceCount = ds.getChildrenCount()+"";
                    Log.d("month",ds.getKey().toString());
                    DatabaseReference rootref1 = FirebaseDatabase.getInstance().getReference();
                    rootref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final List<ChildList> Child = new ArrayList<>();
                            for(DataSnapshot ds1: snapshot.child("attendance").child(yearno).child(branch).child(rollno).child(ParentKey).getChildren())
                            {
                                final String ChildValue = "  Date: "+ds1.getKey().toString() +"                    Time: "+ ds1.getValue().toString();
                                Log.d("dateAndtime",ChildValue);
                                Child.add(new ChildList(ChildValue));
                                Log.d("date","added");
                            }
                            Parent.add(new ParentList("Month: "+ds.getKey().toString()+"              TA:- "+attendanceCount, Child));
                            DocExpandableRecyclerAdapter adapter = new DocExpandableRecyclerAdapter(Parent);
                            recycler_view.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}