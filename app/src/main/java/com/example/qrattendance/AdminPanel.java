package com.example.qrattendance;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminPanel extends AppCompatActivity  {

    private Button AddStudentButton,GenerateQR;
    private ImageView studentqr;
    private Uri imageUri;
    private EditText InputName, InputPhoneNumber, InputRollno;
    private ProgressDialog loadingBar;
   private String branch="";
   private String yearno="";
   private String semno="";
    private  Spinner spin,st_year,st_sem;
  private  String[] branchesname = { "SELECT BRANCH" ,"Computer Science", "Information Technology", "Chemical Engineering", "Mechnanical Engineering", "Civil Engineering" ,
             "Paint Technology", " Plastic Technology" };
   private String [] year = { "YEAR","1","2","3","4"};
    private String [] semester = {  "SEM" ,"1","2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner  st_year =(Spinner) findViewById(R.id.student_year);
        Spinner st_sem = (Spinner)  findViewById(R.id.student_sem);
        AddStudentButton = (Button) findViewById(R.id.add_student);
        InputName = (EditText) findViewById(R.id.student_name);
        InputRollno = (EditText) findViewById(R.id.student_rollno);
        InputPhoneNumber = (EditText) findViewById(R.id.student_phone);
        studentqr = (ImageView) findViewById(R.id.qr_image);
        GenerateQR =(Button) findViewById(R.id.generate_qr);
              setspinnerview(spin,st_year,st_sem);
          GenerateQR.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  qrgenerate();
              }
          });

          AddStudentButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                    Addstudent();
              }
          });

    }

    private void Addstudent() {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String rollno = InputRollno.getText().toString();
        String password ="1234567890";

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write student name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone) && phone.length()!=10)
        {
            Toast.makeText(this, "Please write student Phone no. correctly...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(rollno) && rollno.length()!=9)
            Toast.makeText(this, "Please write correct Roll No...", Toast.LENGTH_SHORT).show();
        else if(branch.equals("SELECT BRANCH") )
            Toast.makeText(this, "Please Select the branch..", Toast.LENGTH_SHORT).show();
        else if(yearno.equals("YEAR"))
            Toast.makeText(this, "Please Select the year..", Toast.LENGTH_SHORT).show();
        else if(semno.equals("SEM"))
            Toast.makeText(this, "Please Select the semester..", Toast.LENGTH_SHORT).show();
        else
        {
            loadingBar.setTitle("Adding Student");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateRollno(name, phone, password,rollno,branch,yearno,semno);
        }

    }

    private void ValidateRollno(final String name, final String phone, final String password,final  String rollno, final String branch,final String yearno,final  String semno) {

    }

    private void qrgenerate() {
    }


    void setspinnerview(Spinner spin,Spinner st_year,Spinner st_sem)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchesname);
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
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
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
        ArrayAdapter<String> adapter_sem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semester);
        adapter_sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        st_sem.setAdapter(adapter_sem);
        st_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 semno=semester[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}