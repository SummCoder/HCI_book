package com.example.hci_book.book;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hci_book.Adapter.CommentAdapter;
import com.example.hci_book.MyApplication;
import com.example.hci_book.R;
import com.example.hci_book.comment.WriteCommentActivity;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.BookBean;
import com.example.hci_book.entity.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author SummCoder
 * @date 2023/12/19 23:40
 * 书本详情页面，包括书名、作者、主要内容，读者的评论
 */
public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_bookDetailBack;
    private RecyclerView rv_bookDetail;
    private int bookId;
    private DBHelper mHelper;
    private ImageView iv_bookDetailCover;
    private TextView tv_bookDetailName;
    private TextView tv_bookDetailAuthor;
    private TextView tv_bookDetailContent;
    private TextView tv_classification;
    private ImageView iv_toWrite;

    private static int[] avatarArray = {R.drawable.avatar0, R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3};
    private TextView tv_noComment;
    private ImageView iv_favorite;
    private Boolean isFavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        iv_bookDetailBack = findViewById(R.id.iv_bookDetailBack);
        iv_bookDetailBack.setOnClickListener(this);
        rv_bookDetail = findViewById(R.id.rv_bookDetail);
        Intent intent = getIntent();
        bookId = intent.getIntExtra("bookInfo", 1);
        iv_bookDetailCover = findViewById(R.id.iv_bookDetailCover);
        tv_bookDetailName = findViewById(R.id.tv_bookDetailName);
        tv_bookDetailAuthor = findViewById(R.id.tv_bookDetailAuthor);
        tv_bookDetailContent = findViewById(R.id.tv_bookDetailContent);
        tv_classification = findViewById(R.id.tv_classification);
        iv_toWrite = findViewById(R.id.iv_toWrite);
        iv_toWrite.setOnClickListener(this);
        tv_noComment = findViewById(R.id.tv_noComment);
        iv_favorite = findViewById(R.id.iv_favorite);
        iv_favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_bookDetailBack){
            finish();
        } else if (view.getId() == R.id.iv_toWrite) {
            // 跳转至写书评页面，携带信息为书籍的id信息，便于写入数据库加以对应
            Intent intent = new Intent(this, WriteCommentActivity.class);
            intent.putExtra("bookId", bookId);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_favorite) {
            if(isFavourite){
                mHelper.deleteCollection(bookId, MyApplication.getInstance().infoMap.get("username"));
                iv_favorite.setImageResource(R.drawable.ic_collection);
                isFavourite = false;
                Toast.makeText(this, "删除收藏成功！", Toast.LENGTH_SHORT).show();
            }else {
                mHelper.insertCollection(bookId, MyApplication.getInstance().infoMap.get("username"));
                iv_favorite.setImageResource(R.drawable.ic_collection2);
                isFavourite = true;
                Toast.makeText(this, "收藏书籍成功！", Toast.LENGTH_SHORT).show();
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
        initData();
    }
    @Override
    protected void onResume(){
        super.onResume();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        BookBean bookBean = mHelper.queryByBookId(bookId);
        // 使用Glide加载封面
        Glide.with(BookDetailActivity.this)
                .load(bookBean.cover)
                .into(iv_bookDetailCover);
        tv_bookDetailName.setText(bookBean.bookName);
        tv_bookDetailAuthor.setText("作者：" + bookBean.author);
        tv_bookDetailContent.setText(bookBean.bookContent);
        isFavourite = mHelper.queryCollection(bookBean.id, MyApplication.getInstance().infoMap.get("username"));
        if(isFavourite){
            iv_favorite.setImageResource(R.drawable.ic_collection2);
        } else {
            iv_favorite.setImageResource(R.drawable.ic_collection);
        }
        List<Comment> comments = mHelper.queryCommentsByBookId(bookId);
        if(comments.size() != 0){
            tv_noComment.setVisibility(View.GONE);
        }else {
            tv_noComment.setVisibility(View.VISIBLE);
        }
        updateUI(comments);
        System.out.println(comments);
        /** 假数据，一共120条数据
         *  1-10作为新书速递
         *  11-20为编辑推荐书籍
         *  21-30为中国文学top10
         *  31-40为外国文学top10
         *  41-50为中国小说top10
         *  51-60为外国小说top10
         *  61-70为历史类top10
         *  71-80为传记类top10
         *  81-90为电影类top10
         *  91-100为戏剧类top10
         *  101-110为推理类top10
         *  111-120为科幻类top10
         */
        if (bookId <= 10){
            tv_classification.setText("新书速递");
        } else if (bookId <= 20) {
            tv_classification.setText("编者力荐");
        } else if (bookId <= 30) {
            tv_classification.setText("中国文学");
        } else if (bookId <= 40) {
            tv_classification.setText("外国文学");
        } else if (bookId <= 50) {
            tv_classification.setText("中国小说");
        } else if (bookId <= 60) {
            tv_classification.setText("外国小说");
        } else if (bookId <= 70) {
            tv_classification.setText("历史类精品");
        } else if (bookId <= 80) {
            tv_classification.setText("传记类精品");
        } else if (bookId <= 90) {
            tv_classification.setText("电影类精品");
        } else if (bookId <= 100) {
            tv_classification.setText("戏剧类精品");
        } else if (bookId <= 110) {
            tv_classification.setText("推理类精品");
        } else if (bookId <= 120) {
            tv_classification.setText("科幻类精品");
        } else if (bookId <= 125) {
            tv_classification.setText("绿色的书");
        } else if (bookId <= 130) {
            tv_classification.setText("木心先生推荐书目");
        } else if (bookId <= 135) {
            tv_classification.setText("西方思想史必知事件书目集");
        } else if (bookId <= 140) {
            tv_classification.setText("英国图书馆馆主推荐童书");
        } else if (bookId <= 145) {
            tv_classification.setText("中国古代生活研究");
        } else if (bookId <= 150) {
            tv_classification.setText("中国古代生活研究");
        }
    }

    private void updateUI(List<Comment> comments) {
        CommentAdapter commentAdapter = new CommentAdapter(comments);
        rv_bookDetail.setAdapter(commentAdapter);
        rv_bookDetail.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;//禁止滑动
            }
        });
        rv_bookDetail.setFocusable(false); // 关闭默认聚焦
    }

}
