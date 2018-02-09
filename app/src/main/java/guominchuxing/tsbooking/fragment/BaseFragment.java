package guominchuxing.tsbooking.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    protected LayoutInflater inflater;
    private View contentView;
    private Context context;
    private ViewGroup container;

    /**
     * 网络类型
     */
    private int netMobile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();



    }



    //子类通过重写onCreateView，调用setOnContentView进行布局设置，否则contentView==null，返回null
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);

        if (contentView == null)

            return super.onCreateView(inflater, container, savedInstanceState);

//            inspectNet();
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    public Context getApplicationContext() {
        return context;
    }

    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        Log.d("TAG", "onDetach() : ");
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化时判断有没有网络
     */

//    public boolean inspectNet() {
//        this.netMobile = NetUtil.getNetWorkState(context);
//
//        return isNetConnect();
//
//    }
    /**
     * 网络变化之后的类型
     */
//    @Override
//    public void onNetChange(int netMobile) {
//        // TODO Auto-generated method stub
//        this.netMobile = netMobile;
//        this.isNetConnect();
//
//    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
//    public boolean isNetConnect() {
//        if (netMobile == NetUtil.NETWORK_WIFI) {
//            return true;
//        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
//            return true;
//        } else if (netMobile == NetUtil.NETWORK_NONE) {
//            return false;
//
//        }
//        return false;
//    }


    ProgressDialog loadDialog;


    protected void showLoadDialog(String msg) {
        if (null == loadDialog) {
            loadDialog = ProgressDialog.show(getActivity(), "",
                    msg, true, true);
        }
        loadDialog.setMessage(msg);

        if (!loadDialog.isShowing())
            loadDialog.show();

    }

    protected void dismissLoadDialog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (null != loadDialog && loadDialog.isShowing())
                    loadDialog.dismiss();
            }
        }).start();

    }

    /**
     * 吐司
     * @param str
     * @param i
     */
    public void tostShow(String str,int i){
        Toast.makeText(getActivity(),str,i).show();
    }

    @Override
    public void onDestroy() {
        Log.d("TAG", "onDestroy() : ");
        super.onDestroy();
        if (null != loadDialog && loadDialog.isShowing()){
            loadDialog.dismiss();
        }
    }


}