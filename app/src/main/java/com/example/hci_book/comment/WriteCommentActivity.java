package com.example.hci_book.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.MyApplication;
import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.Comment;

/**
 * @author SummCoder
 * @date 2023/12/21 20:10
 * 书写评论的页面
 */
public class WriteCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_cancel;
    private EditText et_comment;
    private Button bt_publish;
    private DBHelper mHelper;

    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecomment);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(this);
        et_comment = findViewById(R.id.et_comment);
        bt_publish = findViewById(R.id.bt_publish);
        bt_publish.setOnClickListener(this);
        Intent intent = getIntent();
        bookId = intent.getIntExtra("bookId", 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_cancel){
            finish();
        } else if (view.getId() == R.id.bt_publish) {
//            数据库中插入该条评论，返回书籍详情页
            String commentContent = et_comment.getText().toString();
            long result = mHelper.insertComment(new Comment(bookId, MyApplication.getInstance().infoMap.get("username"), commentContent));
            System.out.println(result);
            if(result != 0){
                Toast.makeText(this, "发布书评成功！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "发布书评失败！", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
