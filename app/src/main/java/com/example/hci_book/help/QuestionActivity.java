package com.example.hci_book.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;

/**
 * @author SummCoder
 * @date 2023/12/23 21:23
 */
public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_questionBack;
    private TextView tv_questionTitle;
    private TextView tv_questionTitle2;
    private TextView tv_questionContent;
    private int questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        iv_questionBack = findViewById(R.id.iv_questionBack);
        iv_questionBack.setOnClickListener(this);
        tv_questionTitle = findViewById(R.id.tv_questionTitle);
        tv_questionTitle2 = findViewById(R.id.tv_questionTitle2);
        tv_questionContent = findViewById(R.id.tv_questionContent);
        Intent intent = getIntent();
        questionId = intent.getIntExtra("questionId", 0);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_questionBack){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(questionId == 0){
            tv_questionTitle.setText("首页推荐是根据什么推荐的？");
            tv_questionTitle2.setText("首页推荐是根据什么推荐的？");
            tv_questionContent.setText("首页内容是根据读者评价的好评率以及评论数量，读过的人推荐的指数来进行推荐的。");
        } else if (questionId == 1) {
            tv_questionTitle.setText("如何发表自己对于书籍的评价？");
            tv_questionTitle2.setText("如何发表自己对于书籍的评价？");
            tv_questionContent.setText("点击书籍，进入书籍详情页点击右下角的写作按钮即可进入写评论页面。");
        } else if (questionId == 2) {
            tv_questionTitle.setText("如何删除自己的书评？");
            tv_questionTitle2.setText("如何删除自己的书评？");
            tv_questionContent.setText("可以在发表过评论的书籍详情页找到发表过的评论，点击删除按钮将其删除，也可以在个人中心，查看历史评论，选择进行删除。");
        } else if (questionId == 3) {
            tv_questionTitle.setText("如何进入用户页面？");
            tv_questionTitle2.setText("如何进入用户页面？");
            tv_questionContent.setText("在首页点击右下角的首页按钮即可进入。");
        } else if (questionId == 4) {
            tv_questionTitle.setText("如何添加自己感兴趣的书籍？");
            tv_questionTitle2.setText("如何添加自己感兴趣的书籍？");
            tv_questionContent.setText("点击书籍，进入详情页面，如果感兴趣点击感兴趣按钮添加到书单。");
        } else if (questionId == 5) {
            tv_questionTitle.setText("如何查看所有自己感兴趣的书籍？");
            tv_questionTitle2.setText("如何查看所有自己感兴趣的书籍？");
            tv_questionContent.setText("在用户主页查看用户感兴趣的书单列表。");
        }
    }
}
