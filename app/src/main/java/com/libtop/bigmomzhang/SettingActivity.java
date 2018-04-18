package com.libtop.bigmomzhang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.libtop.bigmomzhang.utils.SharedPrefsStrListUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    public static final String LOW_ZHI = "low_zhi";
    public static final String LOW_DISCUSS = "low_discuss";

    @BindView(R.id.num_zhi)
    AppCompatEditText numZhi;
    @BindView(R.id.num_discuss)
    AppCompatEditText numDiscuss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInjectContentView(R.layout.activity_setting);
        initView();
        String zhi = SharedPrefsStrListUtil.getStringValue(App.getContext(),LOW_ZHI,"80");
        String discuss = SharedPrefsStrListUtil.getStringValue(App.getContext(),LOW_DISCUSS,"5");
        numZhi.setText(zhi);
        numDiscuss.setText(discuss);
    }

    private void initView() {
        Toolbar toolbarDetail = (Toolbar) findViewById(R.id.toolbar_detail);
        toolbarDetail.setTitle("设置");
        toolbarDetail.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarDetail.setTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home://actionbar的左侧图标的点击事件处理
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String zhi = numZhi.getText().toString();
        String discuss = numDiscuss.getText().toString();
        if (TextUtils.isEmpty(zhi)
                || TextUtils.isEmpty(discuss)){
            Toast.makeText(this,"值不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.valueOf(zhi) > 100 || Integer.valueOf(zhi) < 0){
            Toast.makeText(this,"请输入0-100的最低值百分数",Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.valueOf(discuss)< 0){
           Toast.makeText(this,"请输入大于0的最低值评论数",Toast.LENGTH_SHORT).show();
           return;
        }
        SharedPrefsStrListUtil.putStringValue(App.getContext(),LOW_ZHI, zhi);
        SharedPrefsStrListUtil.putStringValue(App.getContext(),LOW_DISCUSS, discuss);
        Toast.makeText(this,"重启应用生效",Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
