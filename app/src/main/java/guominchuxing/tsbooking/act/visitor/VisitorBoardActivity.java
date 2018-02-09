package guominchuxing.tsbooking.act.visitor;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.adapter.BoardAdapter;
import guominchuxing.tsbooking.adapter.OnItemClickListener;
import guominchuxing.tsbooking.adapter.RecyclerHolder;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.BaseBean;
import guominchuxing.tsbooking.bean.BoardBean;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.util.DateUtils;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.DoubleDatePickerDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.functions.Action1;


/**
 * Created by admin on 2017/12/4.
 * 游客看板
 */

public class VisitorBoardActivity extends BaseActivity implements BaseActivity.OnReloadDataListener,BoardAdapter.CallPhone{

    private static final String TAG = "VisitorBoardActivity";

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_date)
    RelativeLayout rl_date;
    @BindView(R.id.iv_empty)
    ImageView iv_empty;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    @BindView(R.id.fl_empty)
    RelativeLayout rl_empty;
    private GridLayoutManager gridLayoutManager;
    private Date date = new Date();
    private BoardAdapter adapter = null;
    private List<BoardBean>list = null;
    private String token;
    private String timeFrom,timeTo;
    @Override
    protected int getLayout() {
        return R.layout.activity_visitor_board;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            initData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData() {
      token = SharefereUtil.getValue(this, "token", "");

      tv_title.setText(R.string.str_visitor_board);
      iv_back.setBackgroundResource(R.mipmap.iv_back);
      rl_back.setOnClickListener(this);
//      rl_front.setOnClickListener(this);
//      rl_after.setOnClickListener(this);
      rl_date.setOnClickListener(this);

      tv_date.setText(DateUtils.format(date,"yyyy-MM-dd"));
      gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
      recyclerView.setLayoutManager(gridLayoutManager);
      adapter = new BoardAdapter(this);
      recyclerView.setAdapter(adapter);


      timeFrom = DateUtils.format(date, "yyyy-MM-dd");
      timeTo = DateUtils.format(DateUtils.getTomorrowDate(date), "yyyy-MM-dd");

      request(true);

      adapter.setCallPhone(this);
    }



    private void getDate(){
        Calendar c = Calendar.getInstance();
        DoubleDatePickerDialog datePickerDialog =  new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth
            ) {
                StringBuilder sb1 = new StringBuilder();
                timeFrom = sb1.append(startYear)
                        .append("-")
                        .append((startMonthOfYear+1)<10 ? "0"+(startMonthOfYear+1):(startMonthOfYear+1))
                        .append("-")
                        .append(startDayOfMonth<10 ? "0"+startDayOfMonth : startDayOfMonth).toString();
                timeTo = DateUtils.format(DateUtils.getTomorrowDate(DateUtils.parse(timeFrom,"yyyy-MM-dd")), "yyyy-MM-dd");
                tv_date.setText(timeFrom);
                request(true);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true);

        datePickerDialog.show();
    }
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
//            case R.id.rl_front:
//                Toast.makeText(this,"front",Toast.LENGTH_LONG).show();
//                break;
//            case R.id.rl_after:
//                Toast.makeText(this,"after",Toast.LENGTH_LONG).show();
//                break;
            case R.id.rl_date:
                //选择器的日期
                getDate();
                break;
            case R.id.rl_back:
                finish();
                break;
                default:
        }
    }

    @Override
    public void request(boolean isRefresh) {
        if (isRefresh) {
            showLoadingPage("正在加载中...", R.mipmap.ic_loading);
        }

        ApiManager.getDefaut().getBoard(timeFrom,timeTo,Const.PAYSTATUS,Const.STATUS,token,Const.APP)
                .compose(RxUtil.<ResultBean<List<BoardBean>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<List<BoardBean>>>() {
                    @Override
                    public void call(ResultBean<List<BoardBean>> resultBean) {
                        rl_empty.setVisibility(View.GONE);
                        Log.i(TAG,resultBean.getData().toString()+"--"+resultBean.getCode()+"+++");
                        list = resultBean.getData();
                        adapter.clear();
                        adapter.addAll(list);
                        if (list.size() == 0){
                            rl_empty.setVisibility(View.VISIBLE);
                            iv_empty.setImageResource(R.mipmap.icon_empty);
                            tv_empty.setText("暂无游客信息");

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,throwable.getMessage().toString());
                        rl_empty.setVisibility(View.VISIBLE);
                        iv_empty.setImageResource(R.mipmap.icon_empty);
                        tv_empty.setText("暂无游客信息");
                    }
                });



    }

    /**
     *
     * @param call
     */
    private void getCall(String call) {

            if (ContextCompat.checkSelfPermission(VisitorBoardActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                   // 没有获得授权，申请授权
                if (ActivityCompat.shouldShowRequestPermissionRationale(VisitorBoardActivity.this,Manifest.permission.CALL_PHONE)) {

                    Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();
                          // 帮跳转到该应用的设置界面，让用户手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                       // 不需要解释为何需要该权限，直接请求授权
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},Const.REQUEST_PHONE_PERM);
                }
            }else {
// 已经获得授权，可以打电话
                CallPhone(call);
            }

    }
    @SuppressLint("MissingPermission")
    private void CallPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
       startActivity(intent);
    }


    @Override
    public void callPhone(String phone) {
        getCall(phone);
    }
}
