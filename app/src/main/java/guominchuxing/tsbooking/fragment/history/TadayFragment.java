package guominchuxing.tsbooking.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
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
import guominchuxing.tsbooking.util.DateUtils;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
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

public class TadayFragment extends LazyFragment {

    private static final String TAG = "TadayFragment";
    /**每一页展示多少条数据*/
//    private static final int NUM_OF_PAGE = 20;
//    private int currentPage = 1;
    private boolean isLoading;
    private Subscription subscription;
    private TodayAdapter adapter;
    private String token;
    private String date;
    private LinearLayoutManager layoutManager;
    private List<HistoryBeans> dataList;
    boolean isLoadingMore = false;
    @BindView(R.id.rv_today)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private FragmentListener fragmentListener = null;


    public interface FragmentListener{
        void TotalText(String total);
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
            //
//            getHistoryVoucher(NUM_OF_PAGE,currentPage);
            initview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        getHistoryVoucher();
    }
    /**
     * 初始化组件
     */
    private void initview() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    /**
     *
     */
    private void getHistoryVoucher() {
        date = DateUtils.format(new Date(),"yyyy-MM-dd");
        String tomorrowDate = DateUtils.format(DateUtils.getTomorrowDate(new Date()),"yyyy-MM-dd");


          subscription = ApiManager.getDefaut().getHistoryVoucher(
          token,date,tomorrowDate,Const.ODYCREATETIME,Const.APP,Const.METHOD)
                .compose(RxUtil.<ResultBean<List<HistoryBeans>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<List<HistoryBeans>>>() {
                               @Override
                               public void call(ResultBean<List<HistoryBeans>> resultBean) {
                                   Log.e(TAG,token+"subscription"+ resultBean.getCode()+"---M--"+resultBean.getMsg()+"--ddd--"+resultBean.getData().toString());
                                 try {
                                     int code = resultBean.getCode();
                                     String msg = resultBean.getMsg();
                                    final List<HistoryBeans> dataList = resultBean.getData();
                                     fragmentListener.TotalText(dataList.size()+"");
                                     if (code == 200){
                                         if (dataList.size() == 0 || dataList.contains("")){
                                             rl_empty.setVisibility(View.VISIBLE);
                                             tv_empty.setText("今天暂无验证订单");
                                         }
                                         adapter = new TodayAdapter(getActivity(),dataList);
                                         mRecyclerView.setAdapter(adapter);
                                         initview();
                                         adapter.setOnItemClickListener(new TodayAdapter.onRecyclerViewItemClickListener() {
                                             @Override
                                             public void onItemClick(View v, String tag, int position) {
                                                 Intent intent = new Intent(getActivity(),HousingstockActivity.class);
                                                 HistoryBeans data = dataList.get(position);
                                                 intent.putExtra("data", data);
                                                 startActivity(intent);
                                             }
                                         });
//                                      Log.e(TAG,dataList.size()+"");
                                     }else {

                                         rl_empty.setVisibility(View.VISIBLE);
                                         tv_empty.setText("今天暂无验证订单");

                                     }
                                 }catch (Exception e){
                                     e.printStackTrace();
                                 }
                               }
                           }
                        , new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
//                                tostShow("数据加载失败",0);
                                rl_empty.setVisibility(View.VISIBLE);
                                tv_empty.setText("今天暂无验证订单");
                            }
                        });

//
//        StringBuffer sb = new StringBuffer();
//        String url =
//                sb.append(Const.BASEIP)
//                        .append("voucher/list.do?")
//                        .append("checkedTimeFrom=")
//                        .append(date)
//                        .append("&checkedTimeTo=")
//                        .append(tomorrowDate )
//                        .append("&odyCreateTime=")
//                        .append(Const.ODYCREATETIME)
//                        .append("&method=")
//                        .append(Const.METHOD)
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
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
//        Log.e(TAG,"onResumeLazy()");
//        getHistoryVoucher(NUM_OF_PAGE,currentPage);
    }


//    private void getMoreWechatData(int numOfPage, int currentPage) {
//        date = DateUtils.format(new Date(),"yyyy-MM-dd");
//        String TomorrowDate = DateUtils.format(DateUtils.getTomorrowDate(new Date()),"yyyy-MM-dd");
//     subscription = ApiManager.getDefaut().getHistoryVoucher(
//                token,date,TomorrowDate,Const.ODYCREATETIME,Const.APP,Const.METHOD)
//                .compose(RxUtil.<HistoryBean>rxSchedulerHelper())
//                .subscribe(new Action1<HistoryBean>() {
//                               @Override
//                               public void call(HistoryBean historyBean) {
//                                   Log.e(TAG,"subscription"+ historyBean.toString());
//                                   try {
//                                       int code = historyBean.getCode();
//                                       String msg = historyBean.getMsg();
////                                     String total = historyBean.getTotal();
////                                     String pageSize = historyBean.getPageSize();
//                                       if (code == 200){
//                                           dataList = historyBean.getData();
//                                           fragmentListener.TotalText(dataList.size()+"");
//                                           Log.e(TAG,dataList.size()+"");
//                                       }else {
//                                           tostShow(msg,0);
//                                       }
//                                   }catch (Exception e){
//                                       e.printStackTrace();
//                                   }
//                               }
//                           }
//                        , new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                tostShow("数据加载失败",0);
//                            }
//                        });
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription !=null){
            subscription.unsubscribe();
        }
    }

}
