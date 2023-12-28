package com.example.hci_book.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;
import com.example.hci_book.book.BookActivity;

/**
 * @author SummCoder
 * @date 2023/12/20 18:53
 * 根据分类查找图书
 */
public class SearchPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        ImageView iv_searchBack = findViewById(R.id.iv_searchBack);
        TextView tv_findHistory = findViewById(R.id.tv_findHistory);
        TextView tv_findBiography = findViewById(R.id.tv_findBiography);
        TextView tv_findMovie = findViewById(R.id.tv_findMovie);
        TextView tv_findDrama = findViewById(R.id.tv_findDrama);
        TextView tv_findWhodunit = findViewById(R.id.tv_findWhodunit);
        TextView tv_findFiction = findViewById(R.id.tv_findFiction);
        iv_searchBack.setOnClickListener(this);
        tv_findHistory.setOnClickListener(this);
        tv_findBiography.setOnClickListener(this);
        tv_findMovie.setOnClickListener(this);
        tv_findDrama.setOnClickListener(this);
        tv_findWhodunit.setOnClickListener(this);
        tv_findFiction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        设计点击事件并传递必要信息，使得图书列表呈现不同内容
        if (view.getId() == R.id.iv_searchBack){
            finish();
        } else if (view.getId() == R.id.tv_findHistory) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "历史类");
            intent.putExtra("classification", 6);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_findBiography) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "传记类");
            intent.putExtra("classification", 7);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_findMovie) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "电影类");
            intent.putExtra("classification", 8);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_findDrama) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "戏剧类");
            intent.putExtra("classification", 9);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_findWhodunit) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "推理类");
            intent.putExtra("classification", 10);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_findFiction) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("topName", "科幻类");
            intent.putExtra("classification", 11);
            startActivity(intent);
        }
    }
}
