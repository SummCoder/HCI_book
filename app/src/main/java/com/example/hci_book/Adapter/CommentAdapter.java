package com.example.hci_book.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_book.MyApplication;
import com.example.hci_book.R;
import com.example.hci_book.database.DBHelper;
import com.example.hci_book.entity.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author SummCoder
 * @date 2023/12/21 23:20
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private DBHelper mHelper;
    private List<Comment> commentList;

    private static int[] avatarArray = {R.drawable.avatar0, R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3};


    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        holder.ivAvatar.setImageResource(avatarArray[randomNumber]);
        holder.tvUsername.setText(comment.username);
        holder.tvCommentContent.setText(comment.commentContent);
        if(Objects.equals(comment.username, MyApplication.getInstance().infoMap.get("username"))){
            holder.ivDeleteComment.setVisibility(View.VISIBLE);
            holder.ivDeleteComment.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("确定要删除这条评论吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            mHelper = DBHelper.getInstance(v.getContext());
                            mHelper.openReadLink();
                            mHelper.openWriteLink();
                            mHelper.deleteComment(comment.id);
                            commentList.remove(position); // 从数据源中移除对应位置的评论
                            notifyDataSetChanged(); // 通知适配器数据已更改
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            // 用户点击取消，不执行任何操作
                        })
                        .show();
            });
        } else {
            holder.ivDeleteComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUsername;
        TextView tvCommentContent;
        ImageView ivDeleteComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_commentAvatar);
            tvUsername = itemView.findViewById(R.id.tv_commentUsername);
            tvCommentContent = itemView.findViewById(R.id.tv_commentContent);
            ivDeleteComment = itemView.findViewById(R.id.iv_deleteComment);
        }
    }
}

