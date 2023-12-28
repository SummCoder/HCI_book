package com.example.hci_book.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hci_book.readingNews.BroadCastFragment;
import com.example.hci_book.readingNews.CommunicationFragment;
import com.example.hci_book.readingNews.WriterFragment;

/**
 * @author SummCoder
 * @date 2023/12/22 14:17
 */
public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new WriterFragment();
            case 1:
                return new BroadCastFragment();
            default:
                return new CommunicationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
