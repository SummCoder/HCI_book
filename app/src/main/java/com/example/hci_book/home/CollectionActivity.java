package com.example.hci_book.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hci_book.MyApplication;
import com.example.hci_book.R;
import com.example.hci_book.book.BookDetailActivity;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.BookBean;
import com.example.hci_book.entity.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SummCoder
 * @date 2023/12/24 12:44
 */
public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_collectionBack;
    private List<Map<String, Object>> bookList;
    private ListView lv_collection;
    private DBHelper mHelper;
    private TextView tv_noneCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        iv_collectionBack = findViewById(R.id.iv_collectionBack);
        iv_collectionBack.setOnClickListener(this);
        lv_collection = findViewById(R.id.lv_collection);
        tv_noneCollection = findViewById(R.id.tv_noneCollection);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_collectionBack){
            finish();
        }
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
    public void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        List<BookBean> bookBeanList = mHelper.queryCollectionByUsername(MyApplication.getInstance().infoMap.get("username"));
        if(bookBeanList.size() == 0){
            tv_noneCollection.setVisibility(View.VISIBLE);
        }else {
            tv_noneCollection.setVisibility(View.GONE);
        }
        updateUI(bookBeanList);
    }

    private void updateUI(List<BookBean> bookBeans) {
        bookList = new ArrayList<>();
        for (BookBean bookBean : bookBeans) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", bookBean.id);
            map.put("cover", bookBean.cover);
            map.put("bookName", bookBean.bookName);
            map.put("author", bookBean.author);
            map.put("bookContent", bookBean.bookContent);
            bookList.add(map);
        }
        String[] from = {"cover", "bookName", "author", "bookContent"};
        int[] to = {R.id.iv_collectionCover, R.id.tv_collectionBookName, R.id.tv_collectionAuthor, R.id.tv_collectionBookContent};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, bookList, R.layout.item_collection, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_cover = view.findViewById(R.id.iv_collectionCover);
                String avatarUrl = (String) bookList.get(position).get("cover");
                // 使用Glide加载图片
                Glide.with(CollectionActivity.this)
                        .load(avatarUrl)
                        .into(iv_cover);

                ImageView iv_collectionDelete = view.findViewById(R.id.iv_collectionDelete);
                iv_collectionDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("确定要移除该书吗？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                mHelper.deleteCollection((Integer) bookList.get(position).get("id"), MyApplication.getInstance().infoMap.get("username"));
                                bookList.remove(position); // 从数据源中移除对应位置的评论
                                notifyDataSetChanged(); // 通知适配器数据已更改
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                // 用户点击取消，不执行任何操作
                            })
                            .show();
                });


                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 获取被点击的item的数据
                    BookBean bookBean = bookBeans.get(position);
                    // 执行跳转操作
                    navigateToBookDetail(bookBean);
                });

                return view;
            }
        };
        lv_collection.setAdapter(simpleAdapter);
    }

    private void navigateToBookDetail(BookBean bookBean) {
        // 创建一个Intent，跳转至图书详情页面，携带信息为图书id
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookInfo", bookBean.id);
        startActivity(intent);
    }


}
