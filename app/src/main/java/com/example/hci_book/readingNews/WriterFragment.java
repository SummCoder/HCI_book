package com.example.hci_book.readingNews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.Communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SummCoder
 * @date 2023/12/22 14:04
 * 作家专场页面
 */
public class WriterFragment extends Fragment {

    private ListView lv_writer;
    private List<Map<String, Object>> writerList;
    private DBHelper mHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writer, null);
        lv_writer = view.findViewById(R.id.lv_writer);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 打开数据库读写连接
        mHelper = DBHelper.getInstance(getContext());
        mHelper.openReadLink();
        mHelper.openWriteLink();
        initData();
    }

    private void initData() {
        List<Communication> communications = mHelper.queryAllWriters();
        updateUI(communications);
    }


    private void updateUI(List<Communication> communications) {
        writerList = new ArrayList<>();
        for (Communication communication : communications) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", communication.title);
            map.put("date", communication.communicationDate);
            map.put("background", communication.cover);
            writerList.add(map);
        }

        String[] from = {"title", "date", "background"};
        int[] to = {R.id.tv_writerName, R.id.tv_writerDate, R.id.iv_communicationBackground};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), writerList, R.layout.item_writer, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_writerBackground = view.findViewById(R.id.iv_writerBackground);
                String avatarUrl = (String) writerList.get(position).get("background");

                Glide.with(getContext())
                        .load(avatarUrl)
                        .into(iv_writerBackground);

                // 为每个item设置点击事件
                view.setOnClickListener(v -> {
                    // 获取被点击的item的数据
                    Communication communication = communications.get(position);
                    // 执行跳转操作
                    navigateToCommunicationDetail(communication);
                });
                return view;
            }
        };

        lv_writer.setAdapter(simpleAdapter);
    }

    private void navigateToCommunicationDetail(Communication communication) {
        Intent intent = new Intent(getContext(), WriterDetailActivity.class);
        intent.putExtra("communicationInfo", communication.id);
        startActivity(intent);
    }

}
