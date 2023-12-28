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
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.BookBean;
import com.example.hci_book.entity.Comment;
import com.example.hci_book.search.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SummCoder
 * @date 2023/12/24 12:44
 */
public class HistoryCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_commentsBack;
    private ListView lv_historyComments;
    private DBHelper mHelper;

    private List<Map<String, Object>> commentList;
    private TextView tv_noneComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        iv_commentsBack = findViewById(R.id.iv_commentsBack);
        lv_historyComments = findViewById(R.id.lv_historyComments);
        iv_commentsBack.setOnClickListener(this);
        tv_noneComment = findViewById(R.id.tv_noneComment);
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
        List<Comment> commentList = mHelper.queryCommentsByUsername(MyApplication.getInstance().infoMap.get("username"));
        if(commentList.size() == 0){
            tv_noneComment.setVisibility(View.VISIBLE);
        }else {
            tv_noneComment.setVisibility(View.GONE);
        }
        updateUI(commentList);
    }

    private void updateUI(List<Comment> comments) {
        commentList = new ArrayList<>();
        for (Comment comment : comments) {
            Map<String, Object> map = new HashMap<>();
            BookBean bookBean = mHelper.queryByBookId(comment.bookId);
            map.put("id", comment.id);
            map.put("cover", bookBean.cover);
            map.put("commentName", bookBean.bookName);
            map.put("commentContent", comment.commentContent);
            commentList.add(map);
        }
        String[] from = {"cover", "commentName", "commentContent"};
        int[] to = {R.id.iv_commentCover, R.id.tv_commentName, R.id.tv_commentContent};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, commentList, R.layout.item_historycomment, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_cover = view.findViewById(R.id.iv_commentCover);
                String avatarUrl = (String) commentList.get(position).get("cover");
                // 使用Glide加载图片
                Glide.with(HistoryCommentActivity.this)
                        .load(avatarUrl)
                        .into(iv_cover);

                ImageView iv_historyCommentDelete = view.findViewById(R.id.iv_historyCommentDelete);
                iv_historyCommentDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("确定要删除这条评论吗？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                mHelper.deleteComment((Integer) commentList.get(position).get("id"));
                                commentList.remove(position); // 从数据源中移除对应位置的评论
                                notifyDataSetChanged(); // 通知适配器数据已更改
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                // 用户点击取消，不执行任何操作
                            })
                            .show();
                });
                return view;
            }
        };
        lv_historyComments.setAdapter(simpleAdapter);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_commentsBack){
            finish();
        }
    }
}
