package com.example.qrattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class FacultyLogin extends AppCompatActivity {

    private Button loginButton;
    private EditText etPhoneNumber, etPassword;
    private ProgressDialog loadingBar;
    private TextView AdminLink,NotAdminLink;
    private  String dbname ="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        loginButton = (Button) findViewById(R.id.login_btn);
        etPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        etPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(isEmpty(etPhoneNumber.getText().toString()) || isEmpty(etPassword.getText().toString())  )
                 {
                     Toast.makeText(FacultyLogin.this,"Please enter your username and password..",Toast.LENGTH_SHORT).show();
                 }
                 else {
                     String phone = etPhoneNumber.getText().toString();
                     String pw = etPassword.getText().toString();
                     loadingBar.setTitle("Authenticating Account");
                     loadingBar.setMessage("Please wait, while we are checking the credentials.");
                     loadingBar.setCanceledOnTouchOutside(false);
                     loadingBar.show();
                     Authenticateaccount(phone,pw);

                 }
             }
         });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                dbname = "Users";
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

    private void Authenticateaccount(String phone, String pw) {
        if (dbname.equals("admin")) {
            if (phone.equals("170104031") && pw.equals("mypassword")) {
                Toast.makeText(FacultyLogin.this, "Welcome Admin, you are logged in Successfully..", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Intent intent = new Intent( FacultyLogin.this, AdminPanel.class);
                startActivity(intent);
                etPassword.setText("");
            }
            else
            {
                Toast.makeText(FacultyLogin.this, "Nikal Laude !! Pahli fursat mein nikal", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }
        if (dbname.equals("Users")){
            Toast.makeText(FacultyLogin.this," RUKO ZARA SABAR KARO !!",Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }


    }

    private void clearet() {
        etPassword.setText("");
        etPhoneNumber.setText("");
    }

}
