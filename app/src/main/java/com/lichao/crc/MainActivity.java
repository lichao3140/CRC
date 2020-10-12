package com.lichao.crc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_input;
    private TextView tv_result;
    private Button btn_key, btn_copy;

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
        btn_copy = findViewById(R.id.btn_copy);

        btn_key.setOnClickListener(this);
        btn_copy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_key:
                String input = ed_input.getText().toString();
                String result = CrcUtil.getCRC16(input);
                tv_result.setText(input + result);
                break;
            case R.id.btn_copy:
                //获取剪贴板管理器
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", tv_result.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
                break;
        }
    }
}