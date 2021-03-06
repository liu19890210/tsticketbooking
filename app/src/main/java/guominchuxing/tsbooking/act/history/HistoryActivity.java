package guominchuxing.tsbooking.act.history;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.adapter.ViewPageFragAdapter;
import guominchuxing.tsbooking.fragment.history.OtherDateFragment;
import guominchuxing.tsbooking.fragment.history.TadayFragment;
import guominchuxing.tsbooking.fragment.history.YesterdayFragment;

/**
 * Created by admin on 2017/4/18.
 */

public class HistoryActivity extends BaseActivity implements TadayFragment.FragmentListener{
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPageFragAdapter adapter;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_rg_title)
    TextView tv_rg_title;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tab_history_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_history_main)
    ViewPager mViewPager;
    String[] tabTitle = new String[]{"今天","昨天","其他日期"};
    @Override
    protected int getLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
         initView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void initView() {
        tv_title.setText("历史验证");
        tv_rg_title.setText("按月查看");
        tv_rg_title.setOnClickListener(this);
        iv_back.setImageResource(R.mipmap.back);
        rl_back.setOnClickListener(this);
        tv_rg_title.setOnClickListener(this);


        fragmentList.add(new TadayFragment());
        fragmentList.add(new YesterdayFragment());
        fragmentList.add(new OtherDateFragment());
        adapter = new ViewPageFragAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);


        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.getTabAt(2).setText(tabTitle[2]);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_rg_title:
                intent(HistoryMonthActivity.class);
                finish();
                break;
            default:
        }
    }

    @Override
    public void TotalText(String total) {
        if (total !=null){
            tv_num.setText(total);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
