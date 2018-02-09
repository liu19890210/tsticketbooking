package guominchuxing.tsbooking.act.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.adapter.NoticeAdapter;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.NoticeBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/20.
 */

public class NoticeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private NoticeAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List <NoticeBean.NoticeData> dataList;
    private Subscription subscription;
    @Override
    protected int getLayout() {
        return R.layout.activity_notice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initVIew();
            getNoticeList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initVIew() {
        tv_title.setText("公告");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);
        layoutManager =
                new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

    }
    private void getNoticeList(){
       String token = SharefereUtil.getValue(this,"token","");
        subscription = ApiManager.getDefaut().getNoticeList(Const.FORTYPE,Const.ODYCREATETIME_2,Const.TYPE_2,token,Const.APP)
                .compose(RxUtil.<NoticeBean>rxSchedulerHelper())
                .subscribe(new Action1<NoticeBean>() {
                    @Override
                    public void call(NoticeBean noticeBean) {
                        String title = null;
                        try {
                            int code = noticeBean.getCode();
                            if (200 == code){
                               dataList = noticeBean.getData();
                               title = dataList.get(dataList.size()-1).getTitle();
                            }else {
                                ToastShow(noticeBean.getMsg(),0);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            adapter = new NoticeAdapter(NoticeActivity.this,dataList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastShow("加载失败",0);
                    }
                });
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.unsubscribe();
        }
    }
}
