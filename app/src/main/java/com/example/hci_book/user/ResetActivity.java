package com.example.hci_book.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.User;
import com.example.hci_book.util.UserUtils;

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_reset_password1;
    private EditText et_reset_password2;
    private EditText et_reset_username;
    private DBHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        et_reset_username = findViewById(R.id.et_reset_username);
        et_reset_password1 = findViewById(R.id.et_reset_password1);
        et_reset_password2 = findViewById(R.id.et_reset_password2);
    }

    @Override
    public void onClick(View view) {
        String reset_username = et_reset_username.getText().toString();
        String reset_password_first = et_reset_password1.getText().toString();
        String reset_password_second = et_reset_password2.getText().toString();

        if(reset_username.equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(reset_password_first.equals("") || reset_password_second.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!reset_password_second.equals(reset_password_first)){
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isReset = false;
        if(mHelper.queryByUserName(reset_username) != null){
            mHelper.resetPassword(new User(reset_username, reset_password_first));
            isReset = true;
        }
        if(isReset){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", reset_username);
            bundle.putString("password", reset_password_first);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else {
            Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }
}