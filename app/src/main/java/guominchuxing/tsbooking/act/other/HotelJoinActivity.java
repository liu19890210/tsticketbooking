package guominchuxing.tsbooking.act.other;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import guominchuxing.tsbooking.App;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;

/**
 * Created by admin on 2017/4/20.
 */

public class HotelJoinActivity extends BaseActivity {
    private static final String TAG = "HotelJoinActivity";

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @Override
    protected int getLayout() {
        return R.layout.activity_hotel_join;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initVIew();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initVIew() {
        tv_title.setText("酒店加盟");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);

        String url = "http://motel.guomintrip.com/hoteljoin";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
//        webView.addJavascriptInterface(new JsInteration(), "android");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.equals("file:///android_asset/test2.html")) {
//                    Log.e(TAG, "shouldOverrideUrlLoading: " + url);
////                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
//                    return true;
//                } else {
//                    webView.loadUrl(url);
//                    return false;
//                }
//            }
//        });
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (tvProgress == null)
                    return;
                if (newProgress == 100) {
                    tvProgress.setVisibility(View.GONE);
                } else {
                    tvProgress.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams lp = tvProgress.getLayoutParams();
                    lp.width = App.SCREEN_WIDTH * newProgress / 100;
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        webView.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }
}
