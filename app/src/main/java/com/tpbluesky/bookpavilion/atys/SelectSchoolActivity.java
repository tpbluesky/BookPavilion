package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于选择学校的Activity
 */
@ContentView(R.layout.activity_select_school)
public class SelectSchoolActivity extends BaseActivity {

    public static final String RESULT_KEY = "school";
    public final static int REQUEST_SCHOOL_CODE = 100;

    @ViewInject(R.id.lv_school)
    private ListView lv_school;

    private List<String> data = new ArrayList<String>();

    @Override
    protected void initViews() {
        loadSchoolData();
        final Intent intent = getIntent();
        lv_school.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
        lv_school.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra(RESULT_KEY, data.get(position));
                SelectSchoolActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void loadSchoolData() {
        data.clear();
        data.add("武汉科技大学(黄家湖校区)");
        data.add("华中科技大学");
        data.add("中南民族大学");
        data.add("武汉大学");
        data.add("华中师范大学");
        data.add("华中农业大学");
    }

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,SelectSchoolActivity.class));
    }
}
