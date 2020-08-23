package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private Button btn1;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        btn1 = findViewById(R.id.add);
        result = findViewById(R.id.result);

    }

    public void add(View view) {
        int n1, n2;
        n1 = getNumber(num1);
        n2 = getNumber(num2);

        int r = n1 + n2;
        result.setText(String.valueOf(r));
    }

    private int getNumber(EditText text) {
        int num = 0;
        try {
            num = Integer.parseInt(String.valueOf(text.getText()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }


}