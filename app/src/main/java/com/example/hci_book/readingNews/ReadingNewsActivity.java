package com.example.hci_book.readingNews;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hci_book.Adapter.FragmentAdapter;
import com.example.hci_book.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * @author SummCoder
 * @date 2023/12/20 18:59
 * 读书资讯、包括作家动态，新书发布会，读书交流会
 */
public class ReadingNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivReadingNewsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readingnews);
        ivReadingNewsBack = findViewById(R.id.iv_ReadingNewsBack);
        ivReadingNewsBack.setOnClickListener(this);

        TabLayout tl = findViewById(R.id.tabLayout);
        ViewPager2 vp2_news = findViewById(R.id.vp2_news);
        vp2_news.setAdapter(new FragmentAdapter(this));
        TabLayoutMediator tab = new TabLayoutMediator(tl, vp2_news, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("作家专区");
                        break;
                    case 1:
                        tab.setText("直播活动");
                        break;
                    case 2:
                        tab.setText("共读交流");
                        break;
                }
            }
        });
        tab.attach();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_ReadingNewsBack){
            finish();
        }
    }
}
