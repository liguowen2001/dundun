package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import edu.hebut.dundun.entity.RemindText;
import edu.hebut.dundun.entity.RemindTextAdapter;

public class RemindTextSettingActivity extends AppCompatActivity {

    private String TAG = "RemindTextSettingActivity";

    private List<RemindText> remindTexts = new ArrayList<RemindText>();

    private RemindTextAdapter remindTextAdapter;

    /**
     * 监听点击删除
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "delete" + v.getTag().toString());
            alert(Integer.parseInt(v.getTag().toString()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_text_setting);
        initList();
        onAddClick();
        onTitleClick();
    }

    /**
     * 初始化文案list
     */
    private void initList() {
        getRemindTexts();
        remindTextAdapter = new RemindTextAdapter(RemindTextSettingActivity.this,
                R.layout.remind_text_item, remindTexts, onClickListener);
        ListView listView = findViewById(R.id.remindTextListView);
        listView.setAdapter(remindTextAdapter);
    }

    /**
     * 获取推送文案
     */
    private void getRemindTexts() {
        getAllRemindTexts();
    }

    /**
     * 删除推送文案
     *
     * @param position 文案序号
     */
    private void delete(int position) {
        deleteRemindText(remindTexts.get(position).getId());
        remindTexts.remove(remindTexts.get(position));
        remindTextAdapter.notifyDataSetChanged();
    }


    private void onAddClick() {
        View add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    /**
     * 添加文案
     *
     * @param text
     */
    private void add(String text) {
        RemindText remindText = new RemindText();
        remindText.setTitle("自定义提醒文案");
        remindText.setText(text);
        saveRemindText(remindText);
        remindTexts.add(remindText);
        Log.d(TAG, text);
        remindTextAdapter.notifyDataSetChanged();
    }

    private void saveRemindText(RemindText remindText) {
        remindText.save();
        Log.d(TAG, "saveData");
    }

    private void deleteRemindText(Long id) {
        LitePal.delete(RemindText.class, id);
        Log.d(TAG, "delete");
    }

    private void getAllRemindTexts() {
        this.remindTexts = LitePal.findAll(RemindText.class);
    }

    /**
     * 弹窗提醒
     *
     * @param position 文案序号
     */
    private void alert(int position) {
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setTitle("确定删除吗")
                .setIcon(R.drawable.smile)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        alertDialog2.show();
        alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#96CBFD"));
        alertDialog2.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#96CBFD"));
    }

    private void showEditDialog() {

        final EditText edit = new EditText(RemindTextSettingActivity.this);

        AlertDialog builder = new AlertDialog.Builder(RemindTextSettingActivity.this)
                .setTitle("请输入")
                .setIcon(R.drawable.smile)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edit.getText().toString();
                        add(text);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setView(edit)
                .create();
        builder.show();
        builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#96CBFD"));
        builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#96CBFD"));
    }

    public void onTitleClick() {
        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.pen32) {
            @Override
            public void performAction(View view) {
                Log.d(TAG, "clickPen");
                Intent intent = new Intent(RemindTextSettingActivity.this, BaseInformationSelect.class);
                startActivity(intent);
            }
        });
        titleBar.setLeftImageResource(R.drawable.icon_back_white);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindTextSettingActivity.this.finish();
            }
        });
    }
}