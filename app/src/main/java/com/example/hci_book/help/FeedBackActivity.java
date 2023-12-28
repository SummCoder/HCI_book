package com.example.hci_book.help;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hci_book.R;

/**
 * @author SummCoder
 * @date 2023/12/23 22:12
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_feedbackBack;
    private Button bt_commit;
    private EditText et_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        iv_feedbackBack = findViewById(R.id.iv_feedbackBack);
        iv_feedbackBack.setOnClickListener(this);
        bt_commit = findViewById(R.id.bt_commit);
        et_feedback = findViewById(R.id.et_feedback);
        bt_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_feedbackBack){
            finish();
        } else if (view.getId() == R.id.bt_commit) {
            Toast.makeText(this, "您的反馈已经收到啦！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
