package com.example.mpostest;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_referno;
    private Button btn_revoke, btn_consume, btn_query;

    private final String packageName = "com.masget.mgchat.mpos";
    private final String clzName = "com.masget.mgchat.mpos.pay.PayDispatcherActivity";
    private Button btn_load;
    private EditText et_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        calc();
    }

    private void calc() {
        BigDecimal yCount = new BigDecimal(0);//年统计
        for (int m = 1; m <= 12; m++) {
            BigDecimal mCount = new BigDecimal(0);//月统计
            if (m < 8) {//小于8月份单数为大月
                if (m % 2 == 1) {//单月
                    for (int d = 1; d <= 31; d++) {
                        String c = m + "." + d;//月份+日期
                        BigDecimal decimal = new BigDecimal(Double.parseDouble(c));
                        mCount = mCount.add(decimal);
                    }
                    Log.e("calc", "月份:" + m + "\t 统计:" + mCount.doubleValue());
                } else {
                    if (m == 2) {//2月
                        for (int d = 1; d <= 28; d++) {//按照平年算
                            String c = m + "." + d;//月份+日期
                            BigDecimal decimal = new BigDecimal(Double.parseDouble(c));
                            mCount = mCount.add(decimal);
                        }
                        Log.e("calc", "月份:" + m + "\t 统计:" + mCount.doubleValue());
                    } else {
                        for (int d = 1; d <= 30; d++) {//小月 30天
                            String c = m + "." + d;//月份+日期
                            BigDecimal decimal = new BigDecimal(Double.parseDouble(c));
                            mCount = mCount.add(decimal);
                        }
                        Log.e("calc", "月份:" + m + "\t 统计:" + mCount.doubleValue());
                    }
                }
            } else {
                if (m % 2 == 1) {//单月 后半年为小月
                    for (int d = 1; d <= 30; d++) {
                        String c = m + "." + d;//月份+日期
                        BigDecimal decimal = new BigDecimal(Double.parseDouble(c));
                        mCount = mCount.add(decimal);
                    }
                    Log.e("calc", "月份:" + m + "\t 统计:" + mCount.doubleValue());
                } else {//双月 为大月
                    for (int d = 1; d <= 31; d++) {
                        String c = m + "." + d;//月份+日期
                        BigDecimal decimal = new BigDecimal(Double.parseDouble(c));
                        mCount = mCount.add(decimal);
                    }
                    Log.e("calc", "月份:" + m + "\t 统计:" + mCount.doubleValue());
                }
            }
            yCount = yCount.add(mCount);
        }
        Log.e("calc", "年统计: " + yCount.doubleValue());

    }

    private void setListener() {
        btn_consume.setOnClickListener(this);
        btn_revoke.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_load.setOnClickListener(this);
    }

    private void initView() {
        btn_revoke = (Button) findViewById(R.id.btn_revoke);
        btn_consume = (Button) findViewById(R.id.btn_consume);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_load = (Button) findViewById(R.id.btn_load);
        et_referno = (EditText) findViewById(R.id.et_referno);
        et_key = (EditText) findViewById(R.id.et_key);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_consume:
                try {//防止找不到类 报异常
                    intent = new Intent();
                    ComponentName component = new ComponentName(packageName, clzName);
                    intent.setComponent(component);
                    JSONObject json = new JSONObject();
                    json.put("actiontype", 0);
                    json.put("money", "0.01");
                    json.put("othermoney", "0");
                    intent.putExtra("params", json.toString());
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_query:
                try {//防止找不到类 报异常
                    intent = new Intent();
                    ComponentName component = new ComponentName(packageName, clzName);
                    intent.setComponent(component);
                    JSONObject json = new JSONObject();
                    json.put("actiontype", 2);
                    intent.putExtra("params", json.toString());
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_revoke:
                String referno = et_referno.getText().toString().trim();
                if (TextUtils.isEmpty(referno)) {
                    Toast.makeText(MainActivity.this, "参考号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {//防止找不到类 报异常
                    intent = new Intent();
                    ComponentName component = new ComponentName(packageName, clzName);
                    intent.setComponent(component);
                    JSONObject json = new JSONObject();
                    json.put("actiontype", 1);
                    json.put("referno", referno);
                    intent.putExtra("params", json.toString());
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_load:
                    load();
                break;
        }
    }

    private void load() {
        //加载主密钥 主密钥索引设为1
        public boolean  loadMainKey(String tmk){
            int ret=-1;
            try {
                MaxqManager maxqManager=new MaxqManager();
                maxqManager.open();
                //maxqManager.downloadKey(1,1,1,)
                byte tmkBytes[]=Packet8583Util.hexStringToByte(tmk);
                byte result[]={0};
                byte resultlen[]={0};
                maxqManager.deleteKey(4,1,result,resultlen);
                ret=maxqManager.downloadKey(4, 1, 1, tmkBytes, tmkBytes.length, result, resultlen);
                String str1=new String(result);
                String str2=new String(resultlen);
                LogUtil.e("maindata",new String(result));
                maxqManager.close();
            }catch (Exception e){
                return false;
            }
            return ret==0;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            //消费成功
            Bundle extras = data.getExtras();
            String result = extras.getString("result");
            Log.e("detail", "onActivityResult " + result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
