package com.example.hci_book;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.hci_book.book.BookActivity;
import com.example.hci_book.book.BookDetailActivity;
import com.example.hci_book.book.NewBooksActivity;
import com.example.hci_book.bookList.BookListActivity;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.BookBean;
import com.example.hci_book.help.HelpActivity;
import com.example.hci_book.home.CollectionActivity;
import com.example.hci_book.home.HistoryCommentActivity;
import com.example.hci_book.readingNews.ReadingNewsActivity;
import com.example.hci_book.search.SearchActivity;
import com.example.hci_book.search.SearchPageActivity;
import com.example.hci_book.topList.TopListActivity;
import com.example.hci_book.user.LoginActivity;
import com.example.hci_book.util.UserUtils;
import com.google.android.material.navigation.NavigationView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnBannerListener {
    private Button bt_search;
    private Button bt_top;
    private Button bt_activity;
    private Button bt_book;

    private Button bt_showAllNewBooks;
    private Banner banner;
    private List<Integer> banner_newBook;
    private DBHelper mHelper;

    private List<BookBean> bookBeans = new ArrayList<>();

    private List<Map<String, Object>> recommendBooklist;

    private final int showNum = 10;
    private ListView lv_recommended;
    private ImageView iv_toTop;
    private ImageView iv_expand;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        最顶部的四个按钮
        bt_search = findViewById(R.id.bt_search);
        bt_top = findViewById(R.id.bt_top);
        bt_activity = findViewById(R.id.bt_activity);
        bt_book = findViewById(R.id.bt_book);
//        展示全部新书列表按钮
        bt_showAllNewBooks = findViewById(R.id.bt_showAllNewBooks);
//        按钮添加点击事件
        bt_search.setOnClickListener(this);
        bt_top.setOnClickListener(this);
        bt_activity.setOnClickListener(this);
        bt_book.setOnClickListener(this);
        bt_showAllNewBooks.setOnClickListener(this);
        banner = findViewById(R.id.banner_newBooks);

        lv_recommended = findViewById(R.id.lv_recommended);
        iv_toTop = findViewById(R.id.iv_toTop);
        iv_toTop.setOnClickListener(this);
        iv_expand = findViewById(R.id.iv_expand);
        iv_expand.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.navigation_view);
        et_search = findViewById(R.id.et_search);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = et_search.getText().toString();
                    performSearch(searchText); // 执行搜索操作
                    return true;
                }
                return false;
            }
        });

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_collections) {
                    Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_historyComment) {
                    Intent intent = new Intent(MainActivity.this, HistoryCommentActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_help) {
                    Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_logout) {
                    UserUtils.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        if (!UserUtils.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


//        轮播图
        initBannerData();
        banner.setAdapter(new BannerImageAdapter<Integer>(banner_newBook) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });

        banner.isAutoLoop(true);
        banner.setIndicator(new CircleIndicator(this));
        banner.setScrollBarFadeDuration(1000);
        banner.setLoopTime(4000);

        int color = ContextCompat.getColor(MainActivity.this, R.color.theme);
        banner.setIndicatorSelectedColor(color);
        banner.setIndicatorNormalColor(Color.GRAY);
        banner.setOnBannerListener(this);
        banner.start();

    }

    // 执行搜索操作的方法
    private void performSearch(String searchText) {
        // 在这里执行搜索操作
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("bookName", searchText);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
//        多个点击事件
        if (view.getId() == R.id.bt_search){
            Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_top) {
            Intent intent = new Intent(MainActivity.this, TopListActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_activity) {
            Intent intent = new Intent(MainActivity.this, ReadingNewsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_book) {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_showAllNewBooks) {
            Intent intent = new Intent(MainActivity.this, NewBooksActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_toTop) {
//            回到顶部
            lv_recommended.smoothScrollToPosition(0);
        } else if (view.getId() == R.id.iv_expand) {
            drawerLayout.openDrawer(GravityCompat.START); // 打开侧边展开栏
        }
    }


    private void initBannerData(){
        banner_newBook = new ArrayList<>();
        banner_newBook.add(R.drawable.banner1);
        banner_newBook.add(R.drawable.banner2);
        banner_newBook.add(R.drawable.banner3);
    }

//    为轮播图设计点击事件
    @Override
    public void OnBannerClick(Object data, int position) {   //  监听每一个图片的点击事件
        navigateToBookDetail(position + 1);
    }

    private void navigateToBookDetail(int id) {
        // 创建一个Intent，跳转至图书详情页面，携带信息为图书id
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookInfo", id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
        System.out.println(bookBeans.size());
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
        updateUI(bookBeans);
        et_search.setText("");
        View headerView = navigation_view.getHeaderView(0);
        TextView tv_headUsername = headerView.findViewById(R.id.tv_headUsername);
        tv_headUsername.setText(MyApplication.getInstance().infoMap.get("username"));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initData() {
        if(bookBeans.size() == 0){
            for (int i = 10; i < 10 + showNum; i++) {
                BookBean bookBean = mHelper.queryByBookId(i + 1);
                bookBeans.add(bookBean);
            }
        }
    }

    private void reloadData() {
        for (int i = 10; i < 10 + showNum; i++) {
            BookBean bookBean = mHelper.queryByBookId(i + 1);
            bookBeans.set(i - 10, bookBean);
        }
    }

    private void updateUI(List<BookBean> bookBeans) {
        recommendBooklist = new ArrayList<>();
        for (BookBean bookBean : bookBeans) {
            Map<String, Object> map = new HashMap<>();
            map.put("cover", bookBean.cover);
            map.put("bookName", bookBean.bookName);
            map.put("author", bookBean.author);
            map.put("bookContent", bookBean.bookContent);
            recommendBooklist.add(map);
        }

        String[] from = {"cover", "bookName", "author", "bookContent"};
        int[] to = {R.id.iv_cover, R.id.tv_bookName, R.id.tv_author, R.id.tv_bookContent};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, recommendBooklist, R.layout.item_newbook, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_cover = view.findViewById(R.id.iv_cover);
                String avatarUrl = (String) recommendBooklist.get(position).get("cover");
                // 使用Glide加载图片
                Glide.with(MainActivity.this)
                        .load(avatarUrl)
                        .into(iv_cover);

                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 获取被点击的item的数据
                    BookBean bookBean = bookBeans.get(position);
                    // 执行跳转操作
                    navigateToBookDetail(bookBean.id);
                });

                String pattern = "\\[(\\S+)]";
                Pattern regex = Pattern.compile(pattern);
                SpannableString spannableString = new SpannableString((String) recommendBooklist.get(position).get("author"));
                // 进行正则匹配并设置国籍部分为红色
                Matcher matcher = regex.matcher((String) recommendBooklist.get(position).get("author"));
                while (matcher.find()) {
                    int startIndex = matcher.start(1) - 1;
                    int endIndex = matcher.end(1) + 1;

                    int color = ContextCompat.getColor(MainActivity.this, R.color.orange);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                TextView tv_author = view.findViewById(R.id.tv_author);
                tv_author.setText(spannableString);

                return view;
            }
        };

        lv_recommended.setAdapter(simpleAdapter);
    }


}