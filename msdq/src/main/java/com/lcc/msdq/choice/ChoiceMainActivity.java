package com.lcc.msdq.choice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import com.lcc.adapter.ChoiceAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import java.util.Arrays;
import java.util.List;
import zsbpj.lccpj.frame.FrameManager;

public class ChoiceMainActivity extends BaseActivity {

    private String[] name = {"编辑","会计","文员","产品经理","人事","客服","销售","平面设计","程序员"};
    private String [] arr;
    private ChoiceAdapter adapter;
    private AutoCompleteTextView autotext;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void initView() {
        autotext =(AutoCompleteTextView) findViewById(R.id.autotext);
        arr=getResources().getStringArray(R.array.zhiye);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        autotext.setAdapter(arrayAdapter);

        GridView gird_view = (GridView) this.findViewById(R.id.gv_gz);
        adapter=new ChoiceAdapter(ChoiceMainActivity.this, Arrays.asList(name));
        gird_view.setAdapter(adapter);
        gird_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autotext.setText(name[position]);
                adapter.setSelectItem(position);
                adapter.notifyDataSetInvalidated();
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!contains(arr,autotext.getText().toString())){
                   FrameManager.getInstance().toastPrompt("对不起，暂不支持此职业");
               }else {
                   startActivity(new Intent(ChoiceMainActivity.this, MainActivity.class));
                   finish();
               }
            }
        });
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_choice;
    }

    public static boolean contains(String[] stringArray, String source) {
        List<String> tempList = Arrays.asList(stringArray);
        if(tempList.contains(source)) {
            return true;
        } else {
            return false;
        }
    }
}
