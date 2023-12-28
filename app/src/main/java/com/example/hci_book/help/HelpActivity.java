package com.example.hci_book.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SummCoder
 * @date 2023/12/23 20:04
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_helpBack;
    private ListView lv_help;
    private List<Map<String, Object>> questionList;
    private static final String[] questions = {"首页推荐是根据什么推荐的？", "如何发表自己对于书籍的评价？", "如何删除自己的书评？", "如何进入用户页面？", "如何添加自己感兴趣的书籍？", "如何查看所有自己感兴趣的书籍？"};
    private List<Map<String, Object>> feedbackList;
    private static final String[] feedbacks = {"查找图书", "图书榜单", "图书资讯", "书单推荐", "新书速递", "个人主页"};
    private static final int[] icons = {R.drawable.search, R.drawable.top, R.drawable.activity, R.drawable.booklist, R.drawable.ic_newbook, R.drawable.ic_home};
    private ListView lv_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        iv_helpBack = findViewById(R.id.iv_helpBack);
        lv_help = findViewById(R.id.lv_help);
        lv_feedback = findViewById(R.id.lv_feedback);

        iv_helpBack.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        questionList = new ArrayList<>();
        for (String question : questions) {
            Map<String, Object> map = new HashMap<>();
            map.put("question", question);
            questionList.add(map);
        }

        String[] from = {"question"};
        int[] to = {R.id.tv_question};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, questionList, R.layout.item_help, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 执行跳转操作
                    navigateToQuestionDetail(position);
                });
                return view;
            }
        };
        lv_help.setAdapter(simpleAdapter);

        feedbackList = new ArrayList<>();
        for (int i = 0; i < feedbacks.length; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("icon", icons[i]);
            map.put("feedback", feedbacks[i]);
            feedbackList.add(map);
        }

        String[] from1 = {"icon", "feedback"};
        int[] to1 = {R.id.iv_feedback, R.id.tv_feedback};

        SimpleAdapter simpleAdapter1 = new SimpleAdapter(this, feedbackList, R.layout.item_feedback, from1, to1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 执行跳转操作
                    navigateToFeedbackDetail(position);
                });
                return view;
            }
        };
        lv_feedback.setAdapter(simpleAdapter1);

    }

    private void navigateToQuestionDetail(int position) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("questionId", position);
        startActivity(intent);
    }

    private void navigateToFeedbackDetail(int position) {
        Intent intent = new Intent(this, FeedBackActivity.class);
        intent.putExtra("feedbackId", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_helpBack){
            finish();
        }
    }
}
