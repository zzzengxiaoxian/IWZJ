package activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iwzj.ltkj.iwzj.R;

import org.w3c.dom.Text;

/**
 * Created by dell on 2016/10/12.
 */
public class Feedback extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText content;
    private TextView contentnumber,kfphone,submit;
    private static final int MAX_COUNT = 140;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        toolbar= (Toolbar) findViewById(R.id.toolbar_feedback);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        contentnumber= (TextView) findViewById(R.id.contentnumber);
        content= (EditText) findViewById(R.id.content);
        kfphone= (TextView) findViewById(R.id.kfphone);
        submit= (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Feedback.this, "点击了提交", Toast.LENGTH_SHORT).show();
            }
        });
        kfphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = kfphone.getText().toString().trim();
                Log.d("TAG", "phone is: " + phone);
                //1）直接拨打电话
//                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
//                startActivity(intent);
                //2）跳转到拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        content.addTextChangedListener(mTextWatcher);
        content.setSelection(content.length()); // 将光标移动最后一个字符后面
        setLeftCount();
    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*判断输入字符个数*/
private TextWatcher mTextWatcher = new TextWatcher() {

    private int editStart;

    private int editEnd;

    public void afterTextChanged(Editable s) {
        editStart = content.getSelectionStart();
        editEnd = content.getSelectionEnd();

        // 先去掉监听器，否则会出现栈溢出
        content.removeTextChangedListener(mTextWatcher);

        // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
        // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
        while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
            s.delete(editStart - 1, editEnd);
            editStart--;
            editEnd--;
        }
        content.setText(s);
        content.setSelection(editStart);

        // 恢复监听器
        content.addTextChangedListener(mTextWatcher);

        setLeftCount();
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

    }

};

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        contentnumber.setText(String.valueOf((MAX_COUNT - getInputCount()))+"字");
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(content.getText().toString());
    }

}
