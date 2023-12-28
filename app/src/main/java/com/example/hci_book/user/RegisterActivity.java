package com.example.hci_book.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.util.UserUtils;

/**
 * @author SummCoder
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    // 实现注册界面的逻辑和布局
    private EditText et_username_register;
    private EditText et_password1;
    private EditText et_password2;

    private DBHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btn_register = findViewById(R.id.btn_register);
        TextView tv_register = findViewById(R.id.tv_register);
        et_username_register = findViewById(R.id.et_username_register);
        et_password1 = findViewById(R.id.et_password_register);
        et_password2 = findViewById(R.id.et_password_register2);
        btn_register.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        et_username_register.addTextChangedListener(new CheckUsername(et_username_register, 10));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_register){
            String register_username = et_username_register.getText().toString();
            String password_first = et_password1.getText().toString();
            String password_second = et_password2.getText().toString();
            if(register_username.equals("")){
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if(password_first.equals("") || password_second.equals("")){
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password_second.equals(password_first)){
                Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isRegister = false;
            if(mHelper.queryByUserName(register_username) == null){
                mHelper.insertUser(register_username, password_first);
                isRegister = true;
            }
            if(isRegister){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("username", register_username);
                bundle.putString("password", password_first);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }else {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            // 返回进行登录
            finish();
        }
    }

    private class CheckUsername implements TextWatcher {
        public CheckUsername(EditText et_username_register, int max_length) {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String register_username = editable.toString();
            boolean UserExist = UserUtils.getUser(register_username);
            if(UserExist){
                Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
            }
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

