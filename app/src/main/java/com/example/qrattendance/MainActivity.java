package com.example.qrattendance;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button facultylogin,register;
 private  Sharedpref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facultylogin=(Button)findViewById(R.id.facultylog);
        register=(Button)findViewById(R.id.student_registeration);
        pref =new Sharedpref((getApplicationContext()));
        if(pref.isLoggedIn())
        {   Intent i=new Intent(MainActivity.this,StudentHomePage.class);
            startActivity(i);
            Toast.makeText(getBaseContext(), "Welcome " + pref.getusername(),
                    Toast.LENGTH_LONG).show();


        }


        facultylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacultyLogin.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentRegister.class);
                startActivity(intent);
            }
        });

    }
}
