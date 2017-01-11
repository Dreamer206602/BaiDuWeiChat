package com.booboomx.baiduweichat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.booboomx.baiduweichat.Constant;
import com.booboomx.baiduweichat.R;

public class MainActivity extends AppCompatActivity {

    //          SHA1: 1E:5F:31:A5:ED:1E:76:78:CD:0A:B0:D2:46:C9:5C:C7:8F:80:10:BC

    //  keytool -v  -list -keystore /Users/booboomx/Android/As_WorkSpace/BaiDuWeiChat/baiduwechat.jks

    private Button mBtnLocation;
    private TextView mTvLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnLocation= (Button) findViewById(R.id.btn_location);
        mTvLocation= (TextView) findViewById(R.id.tv_content);

        mBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, LocationActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constant.REQUEST_CODE:
                if(resultCode==Constant.RESULT_CODE){

                    String position=data.getStringExtra("position");
                    mTvLocation.setText(position);

                }
                break;
        }
    }
}
