package com.example.qrattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class StudentProfile extends AppCompatActivity {

    private TextView name,branch,rollno,phone,semester,styear,email;
    private  Sharedpref pref;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        name = (TextView)findViewById(R.id.student_name);
        email = (TextView)findViewById(R.id.student_email);
        branch = (TextView)findViewById(R.id.student_branch);
        rollno = (TextView)findViewById(R.id.student_roll);
        phone = (TextView)findViewById(R.id.student_phone);
        semester= (TextView)findViewById(R.id.student_semester);
        back = (ImageView)findViewById(R.id.img_back);
        styear = (TextView)findViewById(R.id.student_year);
        pref = new Sharedpref(getApplicationContext());
     HashMap<String, String> user = pref.getuserdetails();
        branch.setText(user.get(pref.KEY_Branch));
        name.setText(user.get(pref.KEY_NAME));
        phone.setText(user.get(pref.KEY_PHONE));
        email.setText(user.get(pref.KEY_rollno) +"@hbtu.ac.in");
        rollno.setText(user.get(pref.KEY_rollno));
        String year =user.get(pref.KEY_year);

        if(year.equals("1"))
            year+="st";
        if(year.equals("2"))
            year+="nd";
        if(year.equals("3"))
            year+="rd";
        if(year.equals("4"))
            year+="Final";
        styear.setText(year);
        int sem =Integer.parseInt(user.get(pref.KEY_year));
         sem = 2*sem-1;
        semester.setText(sem+" - "+ (sem+1));


      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
          }
      });
    }
}