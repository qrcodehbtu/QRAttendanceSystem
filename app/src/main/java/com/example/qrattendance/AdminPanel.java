package com.example.qrattendance;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminPanel extends AppCompatActivity {
    Button generateQRCodeButton;
    Bitmap bitmap=null;
    ImageView qrview;
    EditText rollno;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        rollno = (EditText) findViewById(R.id.student_rollno);
        generateQRCodeButton = (Button) findViewById(R.id.generate_qr);
        qrview = (ImageView) findViewById(R.id.qr_image);
        final QRCodeGen qrgen = new QRCodeGen(200,200);
        generateQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = qrgen.generateQRCode(rollno.getText().toString());
                qrview.setImageBitmap(bitmap);
                bitmap=null;
            }
        });
    }
}