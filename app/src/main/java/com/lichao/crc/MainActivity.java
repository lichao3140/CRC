package com.lichao.crc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_input;
    private TextView tv_result;
    private Button btn_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ed_input = findViewById(R.id.ed_input);
        tv_result = findViewById(R.id.tv_result);
        btn_key = findViewById(R.id.btn_key);

        btn_key.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_key:
                String input = ed_input.getText().toString();
                String result = CrcUtil.getCRC16(input);
                tv_result.setText(input + result);
                break;
        }
    }
}