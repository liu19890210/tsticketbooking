//package guominchuxing.tsbooking.act.other;
//
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import butterknife.BindView;
//import guominchuxing.tsbooking.R;
//import guominchuxing.tsbooking.act.BaseActivity;
//import guominchuxing.tsbooking.adapter.ViewPageFragAdapter;
//import guominchuxing.tsbooking.api.ApiManager;
//import guominchuxing.tsbooking.bean.HotelSumBean;
////import guominchuxing.tsbooking.bean.SumBean;
//import guominchuxing.tsbooking.constant.Const;
//import guominchuxing.tsbooking.fragment.MonthFragment;
//import guominchuxing.tsbooking.fragment.TwoMonthFragment;
//import guominchuxing.tsbooking.fragment.WeekFragment;
//import guominchuxing.tsbooking.util.DateUtils;
//import guominchuxing.tsbooking.util.RxUtil;
//import guominchuxing.tsbooking.util.SharefereUtil;
////import guominchuxing.tbooking.view.RingPercentView;
//import guominchuxing.tsbooking.view.RoundProgressBar;
//import rx.Subscription;
//import rx.functions.Action1;
//
///**
// * Created by admin on 2017/4/20.
// */
//
//public class TongjiActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
//    private static final String TAG = "TongjiActivity";
//
//
//    private List<Fragment> fragmentList = new ArrayList<>();
//    private ViewPageFragAdapter adapter;
//    @BindView(R.id.tv_title)
//    TextView tv_title;
//    @BindView(R.id.iv_back)
//    ImageView iv_back;
////    @Bind(R.id.ringView)
////    RingPercentView ringView;
//    @BindView(R.id.vp_tongji_main)
//    ViewPager mViewPager;
//    @BindView(R.id.tv_pactNum)
//    TextView tv_pactNum;
//    @BindView(R.id.tv_yiyanNum)
//    TextView tv_yiyanNum;
//    @BindView(R.id.ll_qi)
//    LinearLayout ll_qi;
//    @BindView(R.id.ll_sanshi)
//    LinearLayout ll_sanshi;
//    @BindView(R.id.ll_jiushi)
//    LinearLayout ll_jiushi;
//    @BindView(R.id.textView0)
//    TextView textView0;
//    @BindView(R.id.textView1)
//    TextView textView1;
//    @BindView(R.id.textView2)
//    TextView textView2;
//    @BindView(R.id.tv_num0)
//    TextView tv_num0;
//    @BindView(R.id.tv_num1)
//    TextView tv_num1;
//    @BindView(R.id.tv_num2)
//    TextView tv_num2;
//    @BindView(R.id.cursor0)
//    ImageView iv_cursor0;
//    @BindView(R.id.cursor1)
//    ImageView iv_cursor1;
//    @BindView(R.id.cursor2)
//    ImageView iv_cursor2;
//
//    @BindView(R.id.roundProgressBar2)
//    RoundProgressBar roundProgressBar;
//    private Date date = new Date();
//    private Subscription subscription;
//    private String token;
//
//    @Override
//    protected int getLayout() {
//        return R.layout.activity_tongji;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            token = SharefereUtil.getValue(this,"token","");
//            initVIew();
//            getHotelSumDo();
//            getDaysSum();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    private void initVIew() {
//        tv_title.setText("统计报表");
//        iv_back.setImageResource(R.mipmap.back);
//        iv_back.setOnClickListener(this);
//        ll_qi.setOnClickListener(this);
//        ll_sanshi.setOnClickListener(this);
//        ll_jiushi.setOnClickListener(this);
//        iv_cursor0.setOnClickListener(this);
//        iv_cursor1.setOnClickListener(this);
//        iv_cursor2.setOnClickListener(this);
//
//        mViewPager.setCurrentItem(0);
//        changeFootSelectedState(0);
//        mViewPager.setOffscreenPageLimit(2);
//        mViewPager.setOnPageChangeListener(this);
//
//        fragmentList.add(new WeekFragment());
//        fragmentList.add(new MonthFragment());
//        fragmentList.add(new TwoMonthFragment());
//        adapter = new ViewPageFragAdapter(getSupportFragmentManager(),fragmentList);
//        mViewPager.setAdapter(adapter);
//
//    }
//
//    private void setCirle(long checkAmt,long billAmt){
////        NumberFormat nt = NumberFormat.getPercentInstance();
//////            //设置百分数精确度2即保留两位小数
////            nt.setMinimumFractionDigits(0);
//
//        roundProgressBar.setRoundWidth(getResources().getDimension(R.dimen.dp_30));
//        roundProgressBar.setTextSize(getResources().getDimension(R.dimen.sp_50));
////        String f = nt.format(checkAmt/billAmt);
//
//        int percent = (int) (checkAmt *100/ billAmt);
////         Log.e(TAG,percent+"");
//        roundProgressBar.setProgress(percent);
//    }
//    /**
//     *库存比例
//     */
////    private void setCirle(long checkAmt,long billAmt) {
////        if (checkAmt > 0 && checkAmt < billAmt){
////            ringView.setVisibility(View.VISIBLE);
////            NumberFormat nt = NumberFormat.getPercentInstance();
////            //设置百分数精确度2即保留两位小数
////            nt.setMinimumFractionDigits(0);
////
////            ringView.setBg(0, 360,Color.parseColor("#72b2f8"));
////            ringView.setFrontColor(Color.rgb(255, 255, 255));
////            ringView.setPrimaryTextParam(100,Color.rgb(255, 255, 255), "%");
////            ringView.setSecondryTextParam(30, Color.rgb(255, 255, 255), "合同票数与已验数量");
////            int i= (int) (checkAmt/billAmt);
//////            int percent = checkAmt *100/ billAmt;
//////           int percent =  Integer.parseInt(df.format(checkAmt/billAmt));
////            if (i > 0){
////                ringView.drawCircleRing(270, i,500);
////            }
////
////        }else {
////            ringView.setVisibility(View.GONE);
////        }
////    }
//
//    private void getHotelSumDo() {
//        subscription = ApiManager.getDefaut().getHotelSumDo(token ,Const.TYPE,Const.METHOD, Const.APP)
//                .compose(RxUtil.<HotelSumBean>rxSchedulerHelper())
//                .subscribe(new Action1<HotelSumBean>() {
//                    @Override
//                    public void call(HotelSumBean hotelSumBean) {
//                        try {
//                            int code = hotelSumBean.getCode();
//                            String msg = hotelSumBean.getMsg();
//                            if (200 == code){
//                                String totalRooms = hotelSumBean.getData().getTotalRooms();
//                                String usedNum = hotelSumBean.getData().getTotalSales().getUsedNum();
//                                String yiyanNum = usedNum.equals("") ? "0" :usedNum;
////                                Log.e(TAG,totalRooms+"===>"+usedNum+"yiyanNum"+yiyanNum);
//                                tv_pactNum.setText(totalRooms);
//                                tv_yiyanNum.setText(yiyanNum);
//                                setCirle(Long.parseLong(yiyanNum),Long.parseLong(totalRooms));
//                            }else {
//                                ToastShow(msg,0);
//                            }
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });
//    }
//
//    private void getDaysSum() {
//        String currentTime =  DateUtils.format(date,"yyyyMMdd");
//        String beforeTime = DateUtils.format(DateUtils.getDateBefore(date,7),"yyyyMMdd");
//
////            StringBuffer sb = new StringBuffer();
////        String url =
////                sb.append(Const.BASEIP)
////                        .append("report/days/sum.do?")
////                        .append("from=")
////                        .append(beforeTime)
////                        .append("&to=")
////                        .append(currentTime)
////                        .append("&token=")
////                        .append(token)
////                        .append("&app=")
////                        .append(Const.APP)
////                        .toString();
////        Log.e(TAG, url);
////        OkHttpClient client = new OkHttpClient();
////        final Request request = new Request.Builder()
////                .url(url)
////                .build();
////        client.newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, IOException e) {
////
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) throws IOException {
////                Log.e(TAG, response.body().string());
////
////            }
////        });
//
//
//        subscription = ApiManager.getDefaut().getReportDaysSumDo(beforeTime,currentTime,token,Const.APP)
//                .compose(RxUtil.<SumBean>rxSchedulerHelper())
//                .subscribe(new Action1<SumBean>() {
//                    @Override
//                    public void call(SumBean sumBean) {
//                        try {
//                            int code =sumBean.getCode();
//                            if (code == 200){
//                                String usedNum = sumBean.getData().getUsedNum();
//                                tv_num0.setText(usedNum);
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e(TAG,throwable.getMessage());
//                    }
//                });
//        String beforeTime1 = DateUtils.format(DateUtils.getDateBefore(date,30),"yyyyMMdd");
//        subscription = ApiManager.getDefaut().getReportDaysSumDo(beforeTime1,currentTime,token,Const.APP)
//                .compose(RxUtil.<SumBean>rxSchedulerHelper())
//                .subscribe(new Action1<SumBean>() {
//                    @Override
//                    public void call(SumBean sumBean) {
//                        try {
//                            int code =sumBean.getCode();
//                            if (code == 200){
//                                String usedNum = sumBean.getData().getUsedNum();
//                                tv_num1.setText(usedNum);
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e(TAG,throwable.getMessage());
//                    }
//                });
//        String beforeTime2 = DateUtils.format(DateUtils.getDateBefore(date,90),"yyyyMMdd");
//        subscription = ApiManager.getDefaut().getReportDaysSumDo(beforeTime2,currentTime,token,Const.APP)
//                .compose(RxUtil.<SumBean>rxSchedulerHelper())
//                .subscribe(new Action1<SumBean>() {
//                    @Override
//                    public void call(SumBean sumBean) {
//                        try {
//                            int code =sumBean.getCode();
//                            if (code == 200){
//                                String usedNum = sumBean.getData().getUsedNum();
//                                tv_num2.setText(usedNum);
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e(TAG,throwable.getMessage());
//                    }
//                });
//    }
//
//    private int currentPosition = 0;
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//
//        switch (v.getId()){
//            case R.id.ll_qi:
//                currentPosition = 0;
//
//                break;
//            case R.id.ll_sanshi:
//                currentPosition = 1;
//
//                break;
//            case R.id.ll_jiushi:
//                currentPosition = 2;
//
//                break;
//            case R.id.iv_back:
//                finish();
//                break;
//            default:
//        }
//        changeFootSelectedState(currentPosition);
//        mViewPager.setCurrentItem(currentPosition, false);
//    }
//
//    private void changeFootSelectedState(int currentPosition) {
//        Resources resources = getResources();
//        textView0.setTextColor(currentPosition == 0 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        tv_num0.setTextColor(currentPosition == 0 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        iv_cursor0.setImageDrawable(currentPosition == 0 ? resources.getDrawable(R.mipmap.cursor) : null);
//
//
//        textView1.setTextColor(currentPosition == 1 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        tv_num1.setTextColor(currentPosition == 1 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        iv_cursor1.setImageDrawable(currentPosition == 1 ? resources.getDrawable(R.mipmap.cursor) : null);
//
//        textView2.setTextColor(currentPosition == 2 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        tv_num2.setTextColor(currentPosition == 2 ? resources.getColor(R.color.color_font_3B9AE2) : resources.getColor(R.color.color_989898_font));
//        iv_cursor2.setImageDrawable(currentPosition == 2 ? resources.getDrawable(R.mipmap.cursor) : null);
//
//    }
//    //    private int one = offset*2 +bmpW;//两个相邻页面的偏移量
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        changeFootSelectedState(position);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (subscription != null){
//            subscription.unsubscribe();
//        }
//    }
//
//
//}
