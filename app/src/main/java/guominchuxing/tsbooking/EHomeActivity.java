package guominchuxing.tsbooking;



import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.List;

import butterknife.BindView;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.act.history.HistoryActivity;
import guominchuxing.tsbooking.act.info.HotelInfoActivity;
import guominchuxing.tsbooking.act.other.LoginActivity;
import guominchuxing.tsbooking.act.me.MeActivity;
import guominchuxing.tsbooking.act.other.NoticeActivity;
import guominchuxing.tsbooking.act.visitor.VisitorBoardActivity;
import guominchuxing.tsbooking.act.voucher.VoucherActivity;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.NoticeBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.scanner.CaptureActivity;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.AutoScrollTextView;
import rx.Subscription;
import rx.functions.Action1;


public class EHomeActivity extends BaseActivity {
    private static final String TAG = "EHomeActivity";


    @BindView(R .id.rl_inputNum)
    RelativeLayout rl_inputNum;
    @BindView(R.id.rl_scan)
    RelativeLayout rl_scan;
    @BindView(R.id.rl_history)
    RelativeLayout rl_history;
//    @Bind(R.id.rl_tj_form)
//    RelativeLayout rl_tj_form;
    @BindView(R.id.rl_vb)
    RelativeLayout rl_vb;
    @BindView(R.id.rl_hotel_info)
    RelativeLayout rl_hotel_info;
    @BindView(R.id.iv_me)
    ImageView iv_me;
    @BindView(R.id.tv_auto)
    AutoScrollTextView tv_auto;
    @BindView(R.id.rl_auto)
    RelativeLayout rl_auto;
    private String token;
    private String account,password;
    private List<NoticeBean.NoticeData> dataList;
    private Subscription subscription;
//    private MyReceiver myReceiver;
    @Override
    protected int getLayout() {
        return R.layout.activity_ehome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setTransparentForWindow(Color.parseColor("#0D1F40"));
            initView();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initView() {
        rl_inputNum.setOnClickListener(this);
        rl_scan.setOnClickListener(this);
        rl_history.setOnClickListener(this);
//        rl_tj_form.setOnClickListener(this);
        rl_vb.setOnClickListener(this);
        rl_hotel_info.setOnClickListener(this);
        iv_me.setOnClickListener(this);

    }

    /**
     *
     * @param title
     */
    private void setTextAuto(String title){
        if (!title.equals("") && title != null){
            rl_auto.setVisibility(View.VISIBLE);
            tv_auto.setText(title);
            tv_auto.init(getWindowManager());
            tv_auto.startScroll();
            tv_auto.setOnClickListener(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNoticeListDo();
    }

    /**
     *
     */
    private void getNoticeListDo(){
      token = SharefereUtil.getValue(this,"token","");
//        Log.e(TAG,token);
      subscription = ApiManager.getDefaut().getNoticeList(Const.FORTYPE,Const.ODYCREATETIME,Const.TYPE_2,token,Const.APP)
                .compose(RxUtil.<NoticeBean>rxSchedulerHelper())
                .subscribe(new Action1<NoticeBean>() {
                    @Override
                    public void call(NoticeBean noticeBean) {
                        String title = null;
                      try {
                         int code = noticeBean.getCode();
                         String msg = noticeBean.getMsg();
                          Log.e(TAG,msg);
                          if (200 == code){
//                          List<NoticeBean.NoticeData> dataList = noticeBean.getData();
//                          if (dataList != null && dataList.size() !=0){
//                              title = dataList.get(dataList.size()-1).getTitle();
//                          }
                          }else if (msg.equals("未登录")){
                              intent(LoginActivity.class);
                              finish();
                          } else {
                              ToastShow(msg, 0);
                          }
                      }catch (Exception e){
                          e.printStackTrace();
                      }finally {
                          setTextAuto(title);

                      }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                         ToastShow("加载失败",0);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_inputNum:
                intent(VoucherActivity.class);
                break;
            case R.id.rl_scan:
                intent(CaptureActivity.class);
                break;
            case R.id.rl_history:
                intent(HistoryActivity.class);
                break;
//            case R.id.rl_tj_form:
//                ToastShow("此功能暂未开发",1);
//                intent(TongjiActivity.class);
//                break;
            case R.id.rl_vb:
                intent(VisitorBoardActivity.class);
                break;
            case R.id.rl_hotel_info:
                intent(HotelInfoActivity.class);
                break;
            case R.id.iv_me:
                intent(MeActivity.class);
                break;
            case R.id.tv_auto:
                intent(NoticeActivity.class);
                break;
            default:
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription !=null){
            subscription.unsubscribe();
        }
    }
}
