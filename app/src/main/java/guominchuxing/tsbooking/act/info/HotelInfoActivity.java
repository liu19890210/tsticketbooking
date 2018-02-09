package guominchuxing.tsbooking.act.info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.HotelInfoBean;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.PopRight;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/20.
 */

public class HotelInfoActivity extends BaseActivity {
    private static final String TAG = "HotelInfoActivity";
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_hotelName)
    TextView tv_hotelName;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_right)
    RelativeLayout rl_right;
    @BindView(R.id.tv_star)
    TextView tv_star;
//    @Bind(R.id.tv_chain)
//    TextView tv_chain;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_linkman)
    TextView tv_linkman;
    @BindView(R.id.tv_room_call)
    TextView tv_room_call;
    private String token,ownerId;
    private Subscription subscription;
    private HotelInfoBean data;
    @Override
    protected int getLayout() {
        return R.layout.activity_hotelinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            token = SharefereUtil.getValue(this,"token","");
            ownerId = SharefereUtil.getValue(this,"ownerId","");
            getHttp();
            initVIew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initVIew() {
        tv_title.setText("景点信息");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);
        rl_right.setVisibility(View.VISIBLE);
        rl_right.setOnClickListener(this);
        if (data != null){

        }
    }

    /**
     *
     */
    private void getHttp() {
        subscription = ApiManager.getDefaut().getTicketInfo(ownerId,token, Const.APP)
                        .compose(RxUtil.<ResultBean<HotelInfoBean>>rxSchedulerHelper())
                        .subscribe(new Action1<ResultBean<HotelInfoBean>>() {
                            @Override
                            public void call(ResultBean<HotelInfoBean> resultBean) {
                             Log.e(TAG,resultBean.getCode()+"aaa"+resultBean.toString()) ;
                                try {
                                    int code = resultBean.getCode();
                                    String msg = resultBean.getMsg();
                                    data = resultBean.getData();
                                    if (code == 200){
                                    tv_hotelName.setText(data.getName());
                                    tv_star.setText(data.getLevelName());
//                                    tv_chain.setText(data.getBrandName());
                                    tv_address.setText(data.getAddress());
                                    tv_linkman.setText(data.getLinkman());
                                    tv_room_call.setText(data.getMobile());
                                    }else {
                                     ToastShow(msg,0);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                            ToastShow("数据加载失败",0);
                            Log.e(TAG,throwable.getMessage());
                            }
                        });
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_right:
                PopRight popRight = new PopRight(this);
                popRight.showPopupWindow(findViewById(R.id.rl_right));
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( subscription !=null){
            subscription.unsubscribe();
        }
    }
}