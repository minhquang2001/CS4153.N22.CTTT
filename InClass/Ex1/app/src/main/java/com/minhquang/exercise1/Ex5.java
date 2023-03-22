package com.minhquang.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Ex5 extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMath, tvResult;
    private Button btnC, btnOpen, btnClose, btnDiv, btn7, btn8, btn9, btnMul, btn4, btn5, btn6, btnSub, btn1, btn2, btn3, btnPlus, btn0, btnDot, btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMath = findViewById(R.id.tvMath);
        tvResult = findViewById(R.id.tvResult);
        assignID(btnC,R.id.btnC);
        assignID(btnOpen, R.id.btnOpen);
        assignID(btnClose, R.id.btnClose);
        assignID(btnDiv, R.id.btnDiv);
        assignID(btn0,R.id.btn0);
        assignID(btn1,R.id.btn1);
        assignID(btn2,R.id.btn2);
        assignID(btn3,R.id.btn3);
        assignID(btn4,R.id.btn4);
        assignID(btn5,R.id.btn5);
        assignID(btn6,R.id.btn6);
        assignID(btn7,R.id.btn7);
        assignID(btn8,R.id.btn8);
        assignID(btn9,R.id.btn9);
        assignID(btnMul, R.id.btnMul);
        assignID(btnSub, R.id.btnSub);
        assignID(btnPlus, R.id.btnPlus);
        assignID(btnDot, R.id.btnDot);
        assignID(btnResult, R.id.btnResult);


    }
    void assignID(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String data1 = tvMath.getText().toString();

        if (buttonText.equals("=")){
            tvMath.setText(tvResult.getText());
            return;
        }
        if (buttonText.equals("C")){
            tvMath.setText("");
            tvResult.setText("0");
            return;
        }
        else {
            data1 = data1 + buttonText;
        }
        tvMath.setText(data1);
        String finalResult = getResult(data1);
        if (!finalResult.equals("Err")){
            tvResult.setText(finalResult);
        }
    }
    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable= context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }
        catch (Exception e){
            return "Err";
        }
    }

}