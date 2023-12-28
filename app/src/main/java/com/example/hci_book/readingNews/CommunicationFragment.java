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
 * @date 2023/12/22 14:07
 * 共读交流会信息板块
 */
public class CommunicationFragment extends Fragment {

    private ListView lv_communication;
    private List<Map<String, Object>> communicationList;
    private DBHelper mHelper;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communication, null);
        lv_communication = view.findViewById(R.id.lv_communication);
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
        List<Communication> communications = mHelper.queryAllCommunications();
        updateUI(communications);
    }

    private void updateUI(List<Communication> communications) {
        communicationList = new ArrayList<>();
        for (Communication communication : communications) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", communication.title);
            map.put("date", communication.communicationDate);
            map.put("cover", communication.cover);
            map.put("background", communication.cover);
            communicationList.add(map);
        }

        String[] from = {"title", "date", "cover", "background"};
        int[] to = {R.id.tv_communicationName, R.id.tv_communicationDate, R.id.iv_communicationCover, R.id.iv_communicationBackground};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), communicationList, R.layout.item_communication, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView iv_communicationCover = view.findViewById(R.id.iv_communicationCover);
                ImageView iv_communicationBackground = view.findViewById(R.id.iv_communicationBackground);
                String avatarUrl = (String) communicationList.get(position).get("cover");
                // 使用Glide加载图片
                Glide.with(getContext())
                        .load(avatarUrl)
                        .into(iv_communicationCover);

                Glide.with(getContext())
                        .load(avatarUrl)
                                .into(iv_communicationBackground);

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

        lv_communication.setAdapter(simpleAdapter);
    }

    private void navigateToCommunicationDetail(Communication communication) {
        Intent intent = new Intent(getContext(), CommunicationDetailActivity.class);
        intent.putExtra("communicationInfo", communication.id);
        startActivity(intent);
    }

}
