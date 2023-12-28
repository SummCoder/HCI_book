package com.example.hci_book.topList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;
import com.example.hci_book.book.BookActivity;

/**
 * @author SummCoder
 * @date 2023/12/20 18:57
 * 几个榜单的图书的排行
 */
public class TopListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_topListBack;
    private LinearLayout linear_chineseArt;
    private LinearLayout linear_foreignArt;
    private LinearLayout linear_chineseNovel;
    private LinearLayout linear_foreignNovel;
    private LinearLayout linear_history;
    private LinearLayout linear_biography;
    private LinearLayout linear_movie;
    private LinearLayout linear_drama;
    private LinearLayout linear_whodunit;
    private LinearLayout linear_fiction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplist);
        iv_topListBack = findViewById(R.id.iv_topListBack);
        iv_topListBack.setOnClickListener(this);
        linear_chineseArt = findViewById(R.id.linear_chineseArt);
        linear_foreignArt = findViewById(R.id.linear_foreignArt);
        linear_chineseNovel = findViewById(R.id.linear_chineseNovel);
        linear_foreignNovel = findViewById(R.id.linear_foreignNovel);
        linear_history = findViewById(R.id.linear_history);
        linear_biography = findViewById(R.id.linear_biography);
        linear_movie = findViewById(R.id.linear_movie);
        linear_drama = findViewById(R.id.linear_drama);
        linear_whodunit = findViewById(R.id.linear_whodunit);
        linear_fiction = findViewById(R.id.linear_fiction);

        linear_chineseArt.setOnClickListener(this);
        linear_foreignArt.setOnClickListener(this);
        linear_chineseNovel.setOnClickListener(this);
        linear_foreignNovel.setOnClickListener(this);
        linear_history.setOnClickListener(this);
        linear_biography.setOnClickListener(this);
        linear_movie.setOnClickListener(this);
        linear_drama.setOnClickListener(this);
        linear_whodunit.setOnClickListener(this);
        linear_fiction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_topListBack){
            finish();
        } else if (view.getId() == R.id.linear_chineseArt) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 2);
            intent.putExtra("topName", "中国文学Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_foreignArt) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 3);
            intent.putExtra("topName", "外国文学Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_chineseNovel) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 4);
            intent.putExtra("topName", "中国小说Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_foreignNovel) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 5);
            intent.putExtra("topName", "外国小说Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_history) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 6);
            intent.putExtra("topName", "历史类图书Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_biography) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 7);
            intent.putExtra("topName", "传记类图书Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_movie) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 8);
            intent.putExtra("topName", "电影类Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_drama) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 9);
            intent.putExtra("topName", "戏剧类Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_whodunit) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 10);
            intent.putExtra("topName", "推理类Top10");
            startActivity(intent);
        } else if (view.getId() == R.id.linear_fiction) {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("classification", 11);
            intent.putExtra("topName", "科幻类Top10");
            startActivity(intent);
        }
    }
}
