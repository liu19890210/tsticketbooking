package guominchuxing.tsbooking.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/19.
 */

public class OtherDateFragment extends LazyFragment {
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
    private TadayFragment.FragmentListener fragmentListener = null;
    /**
     * 回调接口
     */
    public interface FragmentListener{
        void TotalText(String total);
    }

    @Override
    public void onAttach(Context context) {
        fragmentListener = (TadayFragment.FragmentListener) context;
        super.onAttach(context);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        try {
            token = SharefereUtil.getValue(getActivity(), "token", "");
            setContentView(R.layout.fragment_inc);
            initview();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        Log.e(TAG,"onResumeLazy()");

    }

    /**
     * 初始化组件
     */
    private void initview() {
        rv_today.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

      /**
     * 初始化日期
     */
    private void initDate() {
        Calendar c = Calendar.getInstance();
        DoubleDatePickerDialog datePickerDialog =  new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth
            ) {
                StringBuilder sb1 = new StringBuilder();
                String checkedTimeFrom = sb1.append(startYear)
                        .append("-")
                        .append((startMonthOfYear+1)<10 ? "0"+(startMonthOfYear+1):(startMonthOfYear+1))
                        .append("-")
                        .append(startDayOfMonth<10 ? "0"+startDayOfMonth : startDayOfMonth).toString();

                StringBuilder sb2 = new StringBuilder();
                String checkedTimeTo = sb2.append(startYear)
                        .append("-")
                        .append((startMonthOfYear+1)<10 ? "0"+(startMonthOfYear+1):(startMonthOfYear+1))
                        .append("-")
                        .append(startDayOfMonth<10 ? "0"+(startDayOfMonth+1) : (startDayOfMonth+1)).toString();
                Log.e(TAG,checkedTimeFrom+"==>"+checkedTimeTo);
                getHistoryVoucher(checkedTimeFrom,checkedTimeTo,NUM_OF_PAGE,currentPage);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true);
        datePickerDialog.show();

    }

    /**
     *
     * @param checkedTimeFrom
     * @param checkedTimeTo
     * @param numOfPage
     * @param currentPage
     */
    private void getHistoryVoucher(String checkedTimeFrom,String checkedTimeTo,int numOfPage, int currentPage) {

//                StringBuffer sb = new StringBuffer();
//        String url =
//                sb.append(Const.BASEIP)
//                        .append("voucher/list.do?")
//                        .append("checkedTimeFrom=")
//                        .append(checkedTimeFrom)
//                        .append("&checkedTimeTo=")
//                        .append(checkedTimeTo)
//                        .append("&token=")
//                        .append(token)
//                        .append("&app=")
//                        .append(Const.APP)
//                        .toString();
//        Log.e(TAG, url);
//        OkHttpClient client = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, response.body().string());
//
//            }
//        });



        subscription = ApiManager.getDefaut().getHistoryVoucher(
                token,checkedTimeFrom,checkedTimeTo,Const.ODYCREATETIME,Const.APP,Const.METHOD)
                .compose(RxUtil.<ResultBean<List<HistoryBeans>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<List<HistoryBeans>>>() {
                               @Override
                               public void call(ResultBean<List<HistoryBeans>> resultBean) {
//                                   Log.e(TAG,"subscription"+ historyBean.toString());
                                   try {
                                    final List<HistoryBeans> list = resultBean.getData();
                                       int code = resultBean.getCode();
                                       String msg = resultBean.getMsg();
                                       if (code == 200){
//                                           final List<HistoryBean.Data> dataList = historyBean.getData();
                                           if (list != null && list.size()>0){
                                               rl_empty.setVisibility(View.GONE);
                                           }else {
                                               rl_empty.setVisibility(View.VISIBLE);
                                               tv_empty.setText("暂无验证订单");
                                           }
                                           adapter.notifyDataSetChanged();
                                           fragmentListener.TotalText(list.size()+" ");
                                           adapter = new TodayAdapter(getActivity(),list);
                                           rv_today.setAdapter(adapter);
                                           adapter.setOnItemClickListener(new TodayAdapter.onRecyclerViewItemClickListener() {
                                               @Override
                                               public void onItemClick(View v, String tag, int position) {
                                                   Intent intent = new Intent(getActivity(),HousingstockActivity.class);
                                                   HistoryBeans data = list.get(position);
                                                   intent.putExtra("data", data);
                                                   startActivity(intent);
                                               }
                                           });
                                       }else {
//                                           tostShow(msg,0);
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
