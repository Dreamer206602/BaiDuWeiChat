package com.booboomx.baiduweichat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.booboomx.baiduweichat.R;
import com.booboomx.baiduweichat.ui.adapter.SearchPositionAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用百度地图仿微信定位(搜索页面)
 */
public class SearchPositionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_search_send)
    TextView tvSearchSend;
    @Bind(R.id.lv_locator_search_position)
    ListView mListView;
    @Bind(R.id.pb_location_search_load_bar)
    ProgressBar progressBar;

    @Bind(R.id.fl_search_back)
    FrameLayout mFrameLayout;

    private ArrayList<SuggestionResult.SuggestionInfo> datas;
    private SearchPositionAdapter mAdapter;

    /**
     * 建议查询
     */
    private SuggestionSearch mSuggestionSearch;


    /**
     * 获取搜索的内容
     */
    OnGetSuggestionResultListener mSuggestionResultListener=new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {

            progressBar.setVisibility(View.GONE);
            if(suggestionResult==null||suggestionResult.getAllSuggestions()==null){
                Toast.makeText(SearchPositionActivity.this,"没有找到内容",Toast.LENGTH_SHORT).show();
                return;
            }

            if(datas!=null){
                datas.clear();
                for(SuggestionResult.SuggestionInfo s:suggestionResult.getAllSuggestions()){
                    datas.add(s);
                }
                mAdapter.notifyDataSetChanged();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_position);
        ButterKnife.bind(this);


        mSuggestionSearch=SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(mSuggestionResultListener);

        datas=new ArrayList<>();
        mAdapter=new SearchPositionAdapter(this,datas);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(this);
        tvSearchSend.setOnClickListener(this);



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.setSelectSearchItemIndex(position);
        mAdapter.notifyDataSetChanged();

        Intent intent=new Intent();
        intent.putExtra("LatLng",datas.get(position).pt);
        setResult(RESULT_OK,intent);
        SearchPositionActivity.this.finish();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_search_send:

                if(!TextUtils.isEmpty(etSearch.getText().toString())){

                    Log.i("ssssssssss", "onClick: "+etSearch.getText().toString());
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(etSearch.getText().toString()).city("中国"));

                }else{
                    Toast.makeText(SearchPositionActivity.this,"请输入地点",Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }
}
