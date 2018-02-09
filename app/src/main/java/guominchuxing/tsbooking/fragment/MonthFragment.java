//package guominchuxing.tsbooking.fragment;
//
//import android.os.Bundle;
//import android.util.Log;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import butterknife.BindView;
//import guominchuxing.tsbooking.R;
//import guominchuxing.tsbooking.api.ApiManager;
//import guominchuxing.tsbooking.bean.WeekBean;
//import guominchuxing.tsbooking.constant.Const;
//import guominchuxing.tsbooking.util.DateUtils;
//import guominchuxing.tsbooking.util.RxUtil;
//import guominchuxing.tsbooking.util.SharefereUtil;
//import guominchuxing.tsbooking.view.quxian.RoomVoucherStatistics;
//import rx.Subscription;
//import rx.functions.Action1;
//
///**
// * Created by admin on 2017/4/25.
// */
//
//public class MonthFragment extends LazyFragment implements  RoomVoucherStatistics.SelectItem{
//    private static final String TAG = "MonthFragment";
//
//    private Date date = new Date();
//    @BindView(R.id.wxs_statistics)
//    RoomVoucherStatistics wxs_statistics;
//    private List<WeekBean.WeekData> dataLists;
//    private int resultNum = 0;
//    private List<WeekBean.WeekData> dataList = new ArrayList<>();
//    private Subscription subscription;
//
//    @Override
//    protected void onCreateViewLazy(Bundle savedInstanceState) {
//        super.onCreateViewLazy(savedInstanceState);
//        setContentView(R.layout.fragment_week);
//        try {
//           init();
//
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//    }
//
//    /**
//     * 绘图折线
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
////            Log.e(TAG,dataLists.get(i).getPeriod()+"========"+dataLists.get(i).getUsedNum());
//        }
//        List<WeekBean.WeekData> dataLists = getDiffrent(getOtherDay(35,"yyyyMMdd"),list2);
//        WeekBean.SortClass sort = new WeekBean.SortClass();
//        Collections.sort(dataLists,sort);
//
//        //===================================================================
//        int m = 0;
//        List<Integer> list = new ArrayList<>();
//        int a = 5;
//        for (int i = 0; i < dataLists.size(); i++){
//            m += Integer.parseInt(dataLists.get(i).getUsedNum());
//            a--;
//            if(a == 0){
//                list.add(m);
//                m = 0;
//                a = 5;
//            }
////            Log.e(TAG,dataLists.get(i).getUsedNum() +"++++++++"+dataLists.get(i).getPeriod()+"&&&&&&"+m+">>>>>>"+i);
//        }
//        if (list.size()> 0){
//        List<String> listDay = getDay(size,"MM.dd");
//            for (int i = 0;i < list.size();i++){
////                Log.e(TAG,list.get(i)+"@@@@@@@"+listDay.get(i));
//                WeekBean.WeekData datas = new WeekBean.WeekData();
//                datas.setPeriod(listDay.get(i));
//                datas.setUsedNum(String.valueOf(list.get(i)));
//                dataList.add(datas);
//            }
//
//        }
//        //===================================================================
//        if (dataLists.size() > 0){
//            List<String> listDays = getDay(size,"MM.dd");
//            for (int i = 0; i < dataList.size(); i++){
//                for (int j = 0; j <listDays.size(); j++){
//                    if (dataList.get(i).getPeriod().indexOf(listDays.get(j))!=-1){
//                        listValueNum.add(Integer.parseInt(dataList.get(i).getUsedNum()));
//                    }
//                }
//            }
//        }
//
//        return listValueNum;
//
//    }
//    /**
//     * 获取两个List的不同元素
//     * @param list1
//     * @param list2
//     * @return
//     */
//    private List<WeekBean.WeekData> getDiffrent(List<String> list1, List<String> list2) {
//        for(String str:list1){
//             WeekBean.WeekData data = new WeekBean.WeekData();
//            if(!list2.contains(str)){
//                data.setPeriod(str);
//                data.setUsedNum("0");
////                Log.e(TAG,data.getPeriod()+"<<<<<<<<<"+data.getUsedNum());
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
//            String b = DateUtils.format(DateUtils.getDateBefore(date,i*5+1),format);
//            listday.add(b);
//        }
//        return listday;
//
//    }
//    private List<String> getOtherDay(int size,String format){
//        List<String> listOtherDay = new ArrayList<>();
//        listOtherDay.clear();
//        for(int i = size ; i > 0 ; i--){
//            String b = DateUtils.format(DateUtils.getDateBefore(date,i),format);
//            listOtherDay.add(b);
//
//        }
//        return listOtherDay;
//    }
//
//    /**
//     *report/mons.do 月报表接口
//     * report/mons/sum.do   按月汇总接口
//     */
//    private void init() {
//        String currentMon =  DateUtils.format(date,"yyyyMMdd");
//        String beforeMon = DateUtils.format(DateUtils.getDateBefore(date,30),"yyyyMMdd");
//        String token = SharefereUtil.getValue(getActivity(),"token","");
//
//        subscription = ApiManager.getDefaut().getReportDaysDo(beforeMon,currentMon,token,Const.APP)
//                .compose(RxUtil.<WeekBean>rxSchedulerHelper())
//                .subscribe(new Action1<WeekBean>() {
//                    @Override
//                    public void call(WeekBean weekBean) {
//                        try {
//                             String msg =  weekBean.getMsg();
//                            int code = weekBean.getCode();
//                            if (code == 200){
//                                dataLists = weekBean.getData();
//                            }else {
//                                tostShow(msg,0);
//                            }
//
//                        }catch (Exception e){
//                                e.printStackTrace();
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
//
//                wxs_statistics.select = item;
//                wxs_statistics.selectbottom = item;
//                wxs_statistics.ShowView();
//
//                break;
//            default:
//                break;
//
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (subscription != null){
//            subscription.unsubscribe();
//        }
//    }
//
////    public interface Result{
////        void resultText(int rusult);
////    }
//}
