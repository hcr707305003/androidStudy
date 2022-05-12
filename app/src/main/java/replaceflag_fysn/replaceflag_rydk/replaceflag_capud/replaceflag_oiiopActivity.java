package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class replaceflag_oiiopActivity extends Activity {

    TextView titleTv;
    WebView webView;

    private WebSettings webSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_webslayout_jjr);

        init();
        replaceflag_Styuty.putBoolean(replaceflag_Cskuio.SP_HAS_SUBMIT_URL, true);
        replaceflag_Abvffg.removeHandler3();

//        if(TextUtils.isEmpty(Spsisas.getString(Bmknxs.SP_GP_ACCOUNT, ""))){
//            Intent googlePicker = AccountManager.newChooseAccountIntent(null, null,
//            new String[] {"com.google"}, true, null, null, null, null);
//            startActivityForResult(googlePicker, 1);
//        }
        if(Build.VERSION.SDK_INT>27){
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription("", R.drawable.replaceflag_ic_gt, 0);
            this.setTaskDescription(description);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&& resultCode == RESULT_OK){
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

            replaceflag_Styuty.putString(replaceflag_Cskuio.SP_GP_ACCOUNT, accountName);
            webView.loadUrl("javascript:setAccount('" + replaceflag_Styuty.getString(replaceflag_Cskuio.SP_GP_ACCOUNT, "") + "')");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        titleTv = findViewById(R.id.title);
        webView = findViewById(R.id.webview);

        setupWebView();

        // 加载页面
        webView.loadUrl(replaceflag_Styuty.getString(replaceflag_Cskuio.SP_NOTI_URL, ""));

        webView.addJavascriptInterface(this, "haha");
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
               return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:setAccount('" + replaceflag_Styuty.getString(replaceflag_Cskuio.SP_GP_ACCOUNT, "") + "')");
            }
        });

        webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //navTitleText.setText(title);
            }
        });

    }

    @JavascriptInterface
    public void jump(){
        sendBroadcast(new Intent("com.my.submit"));
//        Aaspsls.submitComplete();
//        startActivity(new Intent(this, Web3Activity.class));
    }

    @JavascriptInterface
    public void wfinish(){
        finish();
    }

}
