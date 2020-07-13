package com.example.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class StudentRegister extends AppCompatActivity {
    private Button AddStudentButton,GenerateQR;
    private DatabaseReference productref;
    private StorageReference ProductImagesRef;
    private EditText InputName, InputPhoneNumber, InputRollno,Inputpassword,Inputconfirmpassword;
    private ProgressDialog loadingBar;
    private String branch="",rollno,name,phone,password,confirmpw;
    private String yearno="";
    private String semno="";
    private Spinner spin,st_year,st_sem;
    private  String[] branchesname = { "SELECT BRANCH" ,"Computer Science", "Information Technology", "Chemical Engineering", "Mechnanical Engineering", "Civil Engineering" ,
            "Paint Technology", " Plastic Technology" };
    private String [] year = { "YEAR","1","2","3","4"};
    private String [] semester = {  "SEM" ,"1","2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
         Spinner  st_year =(Spinner) findViewById(R.id.student_year);
        Spinner st_sem = (Spinner)  findViewById(R.id.student_sem);
        AddStudentButton = (Button) findViewById(R.id.register_student);
        InputName = (EditText) findViewById(R.id.student_name);
        InputRollno = (EditText) findViewById(R.id.student_rollno);
        Inputpassword=(EditText)findViewById(R.id.student_password) ;
        Inputconfirmpassword=(EditText)findViewById(R.id.student_passwordconfirm);
        InputPhoneNumber = (EditText) findViewById(R.id.student_phone);
        loadingBar = new ProgressDialog(this);
        setspinnerview(spin,st_year,st_sem);

        AddStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Addstudent();
            }
        });
    }

    private void Addstudent() {
        name = InputName.getText().toString();
        phone = InputPhoneNumber.getText().toString();
        rollno = InputRollno.getText().toString();
        password=Inputpassword.getText().toString();
        confirmpw=Inputconfirmpassword.getText().toString();


        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Write Your Name Correctly", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone) && phone.length()!=10)
            Toast.makeText(this, "Invalid Phone Number.", Toast.LENGTH_SHORT).show();
        else if (password.length()<6)
            Toast.makeText(this, "Password must be of Atleast 6 character", Toast.LENGTH_SHORT).show();
        else if(!password.equals(confirmpw))
            Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(rollno) && rollno.length()<6)
            Toast.makeText(this, "Invalid Roll Number.", Toast.LENGTH_SHORT).show();
        else if(branch.equals("SELECT BRANCH") )
            Toast.makeText(this, "Select Your branch.", Toast.LENGTH_SHORT).show();
        else if(yearno.equals("YEAR"))
            Toast.makeText(this, "Select Your Year.", Toast.LENGTH_SHORT).show();
        else if(semno.equals("SEM"))
            Toast.makeText(this, "Select the semester.", Toast.LENGTH_SHORT).show();
        else
        {

            loadingBar.setTitle("Adding Student");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatephoneNumber(rollno,name, phone, password,branch,yearno,semno);
        }

    }

    private void ValidatephoneNumber(final String rollno, final String name, final String phone, final String password, final String branch, final String yearno, final String semno) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(! (snapshot.child("students").child(rollno).exists() ) )
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("rollno" ,rollno);
                    userdataMap.put("branch",branch);
                    userdataMap.put("year",yearno);
                    userdataMap.put("semester",semno);
                    RootRef.child("students").child(rollno).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegister.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(StudentRegister.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(StudentRegister.this, "Student has already been registered...", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setspinnerview(Spinner spin,Spinner st_year,Spinner st_sem)
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
        ArrayAdapter<String> adapter_sem = new ArrayAdapter<String>(this,R.layout.simple_view_text, semester);
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