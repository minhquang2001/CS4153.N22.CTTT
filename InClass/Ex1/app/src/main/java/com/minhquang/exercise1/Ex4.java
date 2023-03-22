package com.minhquang.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;



public class Ex4 extends AppCompatActivity {
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex4);
        text = findViewById(R.id.text);
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DPAD_CENTER)
                {
                    String message = text.getText().toString();
                    ad.setMessage(message);
                    ad.show();
                    return true;
                }
                return false;
            }
        });
    }
}