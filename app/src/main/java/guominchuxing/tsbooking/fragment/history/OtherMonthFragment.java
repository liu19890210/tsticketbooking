package guominchuxing.tsbooking.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.other.HousingstockActivity;
import guominchuxing.tsbooking.adapter.TodayAdapter;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.HistoryBeans;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.fragment.LazyFragment;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.DoubleDatePickerDialog;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/19.
 */

public class OtherMonthFragment extends LazyFragment {
    private static final String TAG = "OtherDateFragment";
    /**每一页展示多少条数据*/
    private static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;
    private Subscription subscription;
    private TodayAdapter adapter;
    private String token;
    private String date;
    @BindView(R.id.rv_today)
    RecyclerView rv_today;
    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private CurMonthFragment.FragmentListener fragmentListener = null;

    /**
     * 接口回调
     */
    public interface FragmentListener{
      void TotalText(String text);
    }

    @Override
    public void onAttach(Context context) {
       fragmentListener = (CurMonthFragment.FragmentListener) context;
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
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
     * 初始化日期
     */
    private void initDate() {
        Calendar c = Calendar.getInstance();
       new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth
            ) {
//                String textString = String.format("", startYear,
//                        startMonthOfYear + 1, startDayOfMonth);
                StringBuilder sb1 = new StringBuilder();
                String checkedTimeFrom = sb1.append(startYear)
                        .append("-")
                        .append((startMonthOfYear+1) < 10 ? "0"+(startMonthOfYear+1):(startMonthOfYear+1))
                        .append("-").append("01").toString();
                StringBuilder sb2 = new StringBuilder();
                String checkedTimeTo = sb2.append(startYear)
                        .append("-")
                        .append((startMonthOfYear+2) < 10 ? "0"+(startMonthOfYear+2):(startMonthOfYear+2))
                        .append("-").append("01").toString();
                getHistoryVoucher(checkedTimeFrom,checkedTimeTo,NUM_OF_PAGE,currentPage);
                Log.e(TAG,checkedTimeFrom + "=-==-="+checkedTimeTo);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();

    }

    /**
     *
     * @param checkedTimeFrom
     * @param checkedTimeTo
     * @param numOfPage
     * @param currentPage
     */
    private void getHistoryVoucher(String checkedTimeFrom, String checkedTimeTo, int numOfPage, int currentPage) {
        subscription = ApiManager.getDefaut().getHistoryVoucher(
                token,checkedTimeFrom,checkedTimeTo,Const.ODYCREATETIME,Const.APP,Const.METHOD)
                .compose(RxUtil.<ResultBean<List<HistoryBeans>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<List<HistoryBeans>>>() {
                               @Override
                               public void call(ResultBean<List<HistoryBeans>> resultBean) {
                                   Log.e(TAG,"subscription"+ resultBean.toString());
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
                                           adapter = new TodayAdapter(getActivity(),dataList);
                                           rv_today.setAdapter(adapter);
                                           adapter.setOnItemClickListener(new TodayAdapter.onRecyclerViewItemClickListener() {
                                               @Override
                                               public void onItemClick(View v, String tag, int position) {
                                                   Intent intent = new Intent(getActivity(),HousingstockActivity.class);
                                                   HistoryBeans data = dataList.get(position);
                                                   intent.putExtra("data", data);
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
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        initDate();
        Log.e(TAG,"onFragmentStartLazy()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription !=null){
            subscription.unsubscribe();
        }
    }
}
