package com.example.hci_book.bookList;

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
import com.example.hci_book.book.BookActivity;
import com.example.hci_book.book.BookDetailActivity;
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
 * @date 2023/12/23 14:02
 */
public class BookListDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_bookListDetailBack;
    private TextView tv_bookListName;
    private int bookListId;

    private List<Map<String, Object>> bookList;
    private ListView lv_bookListBooks;

    private List<BookBean> bookBeans = new ArrayList<>();
    private final int showNum = 5;

    private DBHelper mHelper;
    private ImageView iv_bookListDetailBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklistdetail);
        iv_bookListDetailBack = findViewById(R.id.iv_bookListDetailBack);
        iv_bookListDetailBack.setOnClickListener(this);
        tv_bookListName = findViewById(R.id.tv_bookListName);
        lv_bookListBooks = findViewById(R.id.lv_bookListBooks);
        iv_bookListDetailBackground = findViewById(R.id.iv_bookListDetailBackground);
        Intent intent = getIntent();
        bookListId = intent.getIntExtra("bookListId", 0);
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
            for (int i = 120 + bookListId * 5; i < 120 + bookListId * 5 + showNum; i++) {
                BookBean bookBean = mHelper.queryByBookId(i + 1);
                bookBeans.add(bookBean);
            }
        }
    }

    private void reloadData() {
        for (int i = 120 + bookListId * 5; i < 120 + bookListId * 5 + showNum; i++) {
            BookBean bookBean = mHelper.queryByBookId(i + 1);
            bookBeans.set(i - (120 + bookListId * 5), bookBean);
            if(bookListId == 0){
                tv_bookListName.setText("绿色的书");
                iv_bookListDetailBackground.setImageResource(R.drawable.book11);
            } else if (bookListId == 1) {
                tv_bookListName.setText("木心先生推荐书目");
                iv_bookListDetailBackground.setImageResource(R.drawable.book12);
            } else if (bookListId == 2) {
                tv_bookListName.setText("西方思想必知事件书目集");
                iv_bookListDetailBackground.setImageResource(R.drawable.book13);
            } else if (bookListId == 3) {
                tv_bookListName.setText("英国图书馆馆长推荐童书");
                iv_bookListDetailBackground.setImageResource(R.drawable.book14);
            } else if (bookListId == 4) {
                tv_bookListName.setText("中国古代生活研究");
                iv_bookListDetailBackground.setImageResource(R.drawable.book15);
            } else if (bookListId == 5) {
                tv_bookListName.setText("译文经典·精装本");
                iv_bookListDetailBackground.setImageResource(R.drawable.book16);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_bookListDetailBack){
            finish();
        }
    }


    private void updateUI(List<BookBean> bookBeans) {
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
                Glide.with(BookListDetailActivity.this)
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

                    int color = ContextCompat.getColor(BookListDetailActivity.this, R.color.orange);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                TextView tv_author = view.findViewById(R.id.tv_author);
                tv_author.setText(spannableString);
                return view;
            }
        };

        lv_bookListBooks.setAdapter(simpleAdapter);
    }

    private void navigateToBookDetail(BookBean bookBean) {
        // 创建一个Intent，跳转至图书详情页面，携带信息为图书id
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookInfo", bookBean.id);
        startActivity(intent);
    }

}
