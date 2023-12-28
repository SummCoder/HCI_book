package com.example.hci_book.book;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.BookBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SummCoder
 * @date 2023/12/21 10:27
 */
public class BookActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Map<String, Object>> bookList;
    private ListView lv_books;
    private ImageView ivBookBack;
    private DBHelper mHelper;
    private List<BookBean> bookBeans = new ArrayList<>();
    private final int showNum = 10;

    private int classification;
    private String topName;
    private TextView tv_Books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        lv_books = findViewById(R.id.lv_books);
        ivBookBack = findViewById(R.id.iv_BookBack);
        tv_Books = findViewById(R.id.tv_Books);
        ivBookBack.setOnClickListener(this);
        Intent intent = getIntent();
        classification = intent.getIntExtra("classification", 0);
        topName = intent.getStringExtra("topName");

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
//        showNum = mHelper.getBookCount();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
        updateUI(bookBeans);
    }

    private void initData() {
        if(bookBeans.size() == 0){
            for (int i = classification * 10; i < classification * 10 + showNum; i++) {
                BookBean bookBean = mHelper.queryByBookId(i + 1);
                bookBeans.add(bookBean);
            }
        }
    }

    private void reloadData() {
        for (int i = classification * 10; i < classification * 10 + showNum; i++) {
            BookBean bookBean = mHelper.queryByBookId(i + 1);
            bookBeans.set(i - classification * 10, bookBean);
        }
    }


    private void updateUI(List<BookBean> bookBeans) {
        if(topName != null){
            tv_Books.setText(topName);
        }
        bookList = new ArrayList<>();
        for (BookBean bookBean : bookBeans) {
            Map<String, Object> map = new HashMap<>();
            map.put("cover", bookBean.cover);
            map.put("bookName", bookBean.bookName);
            map.put("author", bookBean.author);
            map.put("bookContent", bookBean.bookContent);
            bookList.add(map);
        }

        String[] from = {"cover", "bookName", "author", "bookContent"};
        int[] to = {R.id.iv_cover, R.id.tv_bookName, R.id.tv_author, R.id.tv_bookContent};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, bookList, R.layout.item_newbook, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_cover = view.findViewById(R.id.iv_cover);
                String avatarUrl = (String) bookList.get(position).get("cover");
                // 使用Glide加载图片
                Glide.with(BookActivity.this)
                        .load(avatarUrl)
                        .into(iv_cover);

                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 获取被点击的item的数据
                    BookBean bookBean = bookBeans.get(position);
                    // 执行跳转操作
                    navigateToBookDetail(bookBean);
                });

                String pattern = "\\[(\\S+)]";
                Pattern regex = Pattern.compile(pattern);
                SpannableString spannableString = new SpannableString((String) bookList.get(position).get("author"));
                // 进行正则匹配并设置国籍部分为红色
                Matcher matcher = regex.matcher((String) bookList.get(position).get("author"));
                while (matcher.find()) {
                    int startIndex = matcher.start(1) - 1;
                    int endIndex = matcher.end(1) + 1;
                    int color = ContextCompat.getColor(BookActivity.this, R.color.orange);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                TextView tv_author = view.findViewById(R.id.tv_author);
                tv_author.setText(spannableString);

                return view;
            }
        };

        lv_books.setAdapter(simpleAdapter);
    }




    private void navigateToBookDetail(BookBean bookBean) {
        // 创建一个Intent，跳转至图书详情页面，携带信息为图书id
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookInfo", bookBean.id);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_BookBack){
            finish();
        }
    }
}
