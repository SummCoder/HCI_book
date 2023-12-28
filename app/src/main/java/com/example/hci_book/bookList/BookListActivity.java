package com.example.hci_book.bookList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.hci_book.R;

/**
 * @author SummCoder
 * @date 2023/12/20 19:58
 * 书单推荐页面
 */
public class BookListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBookListBack;
    private CardView cv_bookList1;
    private CardView cv_bookList2;
    private CardView cv_bookList3;
    private CardView cv_bookList4;
    private CardView cv_bookList5;
    private CardView cv_bookList6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        ivBookListBack = findViewById(R.id.iv_BookListBack);
        ivBookListBack.setOnClickListener(this);
        cv_bookList1 = findViewById(R.id.cv_bookList1);
        cv_bookList2 = findViewById(R.id.cv_bookList2);
        cv_bookList3 = findViewById(R.id.cv_bookList3);
        cv_bookList4 = findViewById(R.id.cv_bookList4);
        cv_bookList5 = findViewById(R.id.cv_bookList5);
        cv_bookList6 = findViewById(R.id.cv_bookList6);
        cv_bookList1.setOnClickListener(this);
        cv_bookList2.setOnClickListener(this);
        cv_bookList3.setOnClickListener(this);
        cv_bookList4.setOnClickListener(this);
        cv_bookList5.setOnClickListener(this);
        cv_bookList6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_BookListBack){
            finish();
        } else if (view.getId() == R.id.cv_bookList1) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 0);
            startActivity(intent);
        } else if (view.getId() == R.id.cv_bookList2) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 1);
            startActivity(intent);
        } else if (view.getId() == R.id.cv_bookList3) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 2);
            startActivity(intent);
        } else if (view.getId() == R.id.cv_bookList4) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 3);
            startActivity(intent);
        } else if (view.getId() == R.id.cv_bookList5) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 4);
            startActivity(intent);
        } else if (view.getId() == R.id.cv_bookList6) {
            Intent intent = new Intent(this, BookListDetailActivity.class);
            intent.putExtra("bookListId", 5);
            startActivity(intent);
        }
    }
}
