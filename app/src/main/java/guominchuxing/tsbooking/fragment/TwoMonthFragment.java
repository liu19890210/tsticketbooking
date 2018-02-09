//package guominchuxing.tsbooking.fragment;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import butterknife.BindView;
//import guominchuxing.tsbooking.R;
//import guominchuxing.tsbooking.api.ApiManager;
//import guominchuxing.tsbooking.bean.WeekBean;
//import guominchuxing.tsbooking.constant.Const;
//import guominchuxing.tsbooking.util.DateUtils;
//import guominchuxing.tsbooking.util.RxUtil;
//import guominchuxing.tsbooking.util.SharefereUtil;
//import guominchuxing.tsbooking.view.quxian.RoomVoucherStatistics;
//import rx.functions.Action1;
//
///**
// * Created by admin on 2017/4/25.
// */
//
//public class TwoMonthFragment extends LazyFragment implements  RoomVoucherStatistics.SelectItem{
//    private static final String TAG = "TwoMonthFragment";
//
//    private Date date = new Date();
//    @BindView(R.id.wxs_statistics)
//    RoomVoucherStatistics wxs_statistics;
//    private List<WeekBean.WeekData> dataLists;
//    @Override
//    protected void onCreateViewLazy(Bundle savedInstanceState) {
//        super.onCreateViewLazy(savedInstanceState);
//        setContentView(R.layout.fragment_week);
//        try {
//            init();
//            show(7);
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//    }
//
//    /**
//     * 画折线
//     * @param size
//     */
//    private void show(int size) {
//        wxs_statistics.setValue(getValue(size), false, true, getDay(size,"MM.dd"), this, R.id.wxs_statistics);
//    }
//
//    /**
//     * data
//     * @param size
//     * @return
//     */
//    private List<Integer> getValue(int size){
//        List<Integer> listValueNum = new ArrayList<Integer>();
//        listValueNum.clear();
//        List<String> list2 = new ArrayList<>();
//        for (int i = 0; i<dataLists.size(); i++){
//            list2.add(dataLists.get(i).getPeriod());
//        }
//        List<WeekBean.WeekData> dataLists = getDiffrent(getDay(size,"yyyyMMdd"),list2);
//        WeekBean.SortClass sort = new WeekBean.SortClass();
//        Collections.sort(dataLists,sort);
//           if (dataLists.size() > 0){
//            List<String> listDays = getDay(size,"MM.dd");
//            for (int i = 0; i < dataLists.size(); i++){
//                for (int j = 0; j <listDays.size(); j++){
//                    if (DateUtils.format(DateUtils.parse(dataLists.get(i).getPeriod(),"yyyyMMdd"),"MM.dd").indexOf(listDays.get(j))!=-1){
//                        listValueNum.add(Integer.parseInt(dataLists.get(i).getUsedNum()));
//                    }
//                }
//            }
//        }
//        return listValueNum;
//    }
//    /**
//     * 获取两个List的不同元素
//     * @param list1
//     * @param list2
//     * @return
//     */
//    private List<WeekBean.WeekData> getDiffrent(List<String> list1, List<String> list2) {
//        for(String str:list1)
//        {
//            if(!list2.contains(str))
//            {
//            WeekBean.WeekData data = new WeekBean.WeekData();
//                data.setPeriod(str);
//                data.setUsedNum("0");
//                dataLists.add(data);
//            }
//        }
//        return dataLists;
//    }
//
//    /**
//     * x轴data
//     * @param size
//     * @param format
//     * @return
//     */
//    private List<String> getDay(int size,String format){
//        List<String> listday = new ArrayList<String>();
//        listday.clear();
//        for(int i = size-1 ; i > -1 ; i--){
//            String b = DateUtils.format(DateUtils.getDateBefore(date,i*10+1),format);
//            listday.add(b);
//        }
//        return listday;
//    }
//    /**
//     *report/mons.do 90报表接口
//     * report/mons/sum.do   按月汇总接口
//     */
//    private void init() {
//
//        String currentMon =  DateUtils.format(date,"yyyyMMdd");
//        String beforeMon = DateUtils.format(DateUtils.getDateBefore(date,90),"yyyyMMdd");
////        Log.e(TAG,currentMon+"===>"+beforeMon);
//        String token = SharefereUtil.getValue(getActivity(),"token","");
//
////        StringBuffer sb = new StringBuffer();
////        String url =
////                sb.append(Const.BASEIP)
////                        .append("report/days.do?")
////                        .append("from=")
////                        .append(beforeMon)
////                        .append("&to=")
////                        .append(currentMon)
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
//        ApiManager.getDefaut().getReportDaysDo(beforeMon,currentMon,token,Const.APP)
//                .compose(RxUtil.<WeekBean>rxSchedulerHelper())
//                .subscribe(new Action1<WeekBean>() {
//                    @Override
//                    public void call(WeekBean weekBean) {
//                        try {
//                            String msg =  weekBean.getMsg();
//                            int code = weekBean.getCode();
//                            if (code == 200){
//                                dataLists = weekBean.getData();
//                            }else {
//                              tostShow(msg,0);
//                            }
//
//                        }catch (Exception e){
//                           e.printStackTrace();
//                        }finally {
//                            show(7);
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        tostShow("暂无数据",0);
//                        Log.e(TAG,throwable.getMessage());
//                    }
//                });
//
//    }
//
//    @Override
//    public void onSelectItem(int vid, int item) {
//        switch (vid) {
//            case R.id.wxs_statistics:
//                wxs_statistics.select = item;
//                wxs_statistics.selectbottom = item;
//                wxs_statistics.ShowView();
//                break;
//            default:
//                break;
//
//        }
//    }
////    public interface Result{
////        void resultText(int rusult);
////    }
//}
//
