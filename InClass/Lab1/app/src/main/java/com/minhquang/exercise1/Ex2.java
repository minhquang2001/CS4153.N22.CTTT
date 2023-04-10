package com.minhquang.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Ex2 extends AppCompatActivity {

    private Button btn;
    private EditText etxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);
        btn = findViewById(R.id.btn);
        etxt = findViewById(R.id.etxt);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + etxt.getText()));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
           }
       });
    }
}