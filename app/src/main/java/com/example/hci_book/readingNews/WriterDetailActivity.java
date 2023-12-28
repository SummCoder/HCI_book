package com.example.hci_book.readingNews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.Communication;

/**
 * @author SummCoder
 * @date 2023/12/22 19:54
 */
public class WriterDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_communicationDetailBack;
    private TextView tv_communityDetailTitle;
    private TextView tv_communityDetailDate;
    private TextView tv_communityDetailContent;
    private int communicationId;
    private DBHelper mHelper;
    private TextView tv_detailTitle;
    private TextView tv_newsClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicationdetail);
        iv_communicationDetailBack = findViewById(R.id.iv_communicationDetailBack);
        iv_communicationDetailBack.setOnClickListener(this);
        tv_communityDetailTitle = findViewById(R.id.tv_communityDetailTitle);
        tv_communityDetailDate = findViewById(R.id.tv_communityDetailDate);
        tv_communityDetailContent = findViewById(R.id.tv_communityDetailContent);
        tv_detailTitle = findViewById(R.id.tv_detailTitle);
        tv_newsClass = findViewById(R.id.tv_newsClass);
        Intent intent = getIntent();
        communicationId = intent.getIntExtra("communicationInfo", 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
//        showNum = mHelper.getBookCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(communicationId);
        Communication communication = mHelper.queryWriterById(communicationId);
        tv_communityDetailTitle.setText(communication.title);
        tv_communityDetailDate.setText(communication.communicationDate);
        tv_communityDetailContent.setText(communication.content);
        tv_detailTitle.setText("作家专区");
        tv_newsClass.setText("作家专区");

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_communicationDetailBack){
            finish();
        }
    }
}
