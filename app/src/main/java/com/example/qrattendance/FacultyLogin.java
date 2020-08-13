package com.example.qrattendance;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;

public class FacultyLogin extends AppCompatActivity {

    private Button loginButton;
    private EditText etRollNumber, etPassword;
    private ProgressDialog loadingBar;
    private TextView AdminLink,NotAdminLink;
    private  String dbname ="students";
    private Sharedpref preff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        loginButton = (Button) findViewById(R.id.login_btn);
        etRollNumber = (EditText) findViewById(R.id.login_roll_number_input);
        etPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(FacultyLogin.this);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        preff =new Sharedpref((getApplicationContext()));
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String roll = etRollNumber.getText().toString();
                 String pw = etPassword.getText().toString();
                 if(isEmpty(etRollNumber.getText().toString()) || isEmpty(etPassword.getText().toString())  )
                     Toast.makeText(FacultyLogin.this,"Please enter your username and password..",Toast.LENGTH_SHORT).show();

                 else
                     {

                     loadingBar.setTitle("Authenticating Account");
                     loadingBar.setMessage("Please wait, " +
                                    "while we are checking the credentials.");
                     loadingBar.setCanceledOnTouchOutside(false);
                     loadingBar.show();
                     Authenticateaccount(roll,pw);

                 }
             }
         });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                dbname = "students";
                clearet();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                dbname="admin";
                clearet();
            }
        });
    }

    private void Authenticateaccount(final String rollno, final String pw) {
        if (dbname.equals("admin")) {
            if (rollno.equals("170104031") && pw.equals("mypassword")) {
                Toast.makeText(FacultyLogin.this, "Welcome Admin, you are logged in Successfully..", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Intent intent = new Intent( FacultyLogin.this, AdminPanel.class);
                startActivity(intent);
                etPassword.setText("");
            }
            else
            {
                Toast.makeText(FacultyLogin.this, "Incorrect Password or RollNo.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }
        else
            {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
             RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot.child("students").child(rollno).exists())
                  {
                      students studentdata=snapshot.child("students").child(rollno).getValue(students.class);
                     if(studentdata.getRollno().equals(rollno) && studentdata.getPassword().equals(pw))
                     {
                         loadingBar.dismiss();
                         preff.createLoginSession(studentdata.getPhone(), pw,rollno,studentdata.getName(),studentdata.getBranch(),
                                 studentdata.getYear(),studentdata.getSemester());
                         Intent intent = new Intent(FacultyLogin.this,StudentHomePage.class);
                         startActivity(intent);

                         clearet();
                     }
                     else
                     {
                         Toast.makeText(FacultyLogin.this, "Incorrect Roll no. or Password", Toast.LENGTH_SHORT).show();
                         loadingBar.dismiss();
                         clearet();
                     }

                  }
                  else
                  {
                      Toast.makeText(FacultyLogin.this, "Account with this " + rollno + " roll number do not exists.", Toast.LENGTH_SHORT).show();
                      loadingBar.dismiss();
                      clearet();
                  }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

        }


    }

    private void clearet() {
        etPassword.setText("");
        etRollNumber.setText("");
    }

}
