package guominchuxing.tsbooking.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.other.HousingstockActivity;
import guominchuxing.tsbooking.adapter.CurrMonthAdapter;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.HistoryBeans;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.fragment.LazyFragment;
import guominchuxing.tsbooking.util.DateUtils;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/19.
 */

public class CurMonthFragment extends LazyFragment {
    private static final String TAG = "CurMonthFragment";
    /**每一页展示多少条数据*/
    private static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;
    private Subscription subscription;
    private CurrMonthAdapter adapter;
    private String token;
    private String date;
    @BindView(R.id.rv_today)
    RecyclerView rv_today;
    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private FragmentListener fragmentListener = null;
    /**
     * 回调接口
     */
    public interface FragmentListener{
     void TotalText(String text);
    }

    @Override
    public void onAttach(Context context) {
        fragmentListener = (FragmentListener) context;
        super.onAttach(context);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        try {
            token = SharefereUtil.getValue(getActivity(), "token", "");
            setContentView(R.layout.fragment_inc);

            initview();
            //
            getHistoryVoucher(NUM_OF_PAGE,currentPage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 初始化组件
     */
    private void initview() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        rv_today.setLayoutManager(layoutManager);



    }

    /**
     *
     * @param numOfPage
     * @param currentPage
     */
    private void getHistoryVoucher(int numOfPage, int currentPage) {
        date = DateUtils.format(new Date(),"yyyy-MM-dd");
        String firstCurrentMonth = DateUtils.format(DateUtils.getFirstCurrentMonth(new Date()),"yyyy-MM-dd");
        subscription = ApiManager.getDefaut().getHistoryVoucher(
                token,firstCurrentMonth,date,Const.ODYCREATETIME,Const.APP,Const.METHOD)
                .compose(RxUtil.<ResultBean<List<HistoryBeans>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<List<HistoryBeans>>>() {
                               @Override
                               public void call(ResultBean<List<HistoryBeans>> resultBean) {
//                                   Log.e(TAG,"subscription"+ historyBean.toString());
                                   try {
                                       int code = resultBean.getCode();
                                       String msg = resultBean.getMsg();
                                       if (code == 200){
                                           final List<HistoryBeans> dataList = resultBean.getData();
                                           fragmentListener.TotalText(dataList.size() +"");
                                           if (dataList != null && dataList.size()>0){
                                               rl_empty.setVisibility(View.GONE);
                                           }else {
                                               rl_empty.setVisibility(View.VISIBLE);
                                               tv_empty.setText("暂无验证订单");
                                           }
                                           adapter = new CurrMonthAdapter(getActivity(),dataList);
                                           rv_today.setAdapter(adapter);
                                           adapter.setOnItemClickListener(new CurrMonthAdapter.onRecyclerViewItemClickListener() {
                                              @Override
                                              public void onItemClick(View v, String tag, int position) {
                                                  Intent intent = new Intent(getActivity(),HousingstockActivity.class);
                                                   HistoryBeans data = dataList.get(position);
                                                   intent.putExtra("data", (Serializable) data);
                                                   startActivity(intent);
                                              }
                                          });
                                       }else {
                                           rl_empty.setVisibility(View.VISIBLE);
                                           tv_empty.setText("暂无验证订单");
                                       }
                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }
                               }
                           }
                        , new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                rl_empty.setVisibility(View.VISIBLE);
                                tv_empty.setText("暂无验证订单");
                            }
                        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription !=null){
            subscription.unsubscribe();
        }
    }
}


