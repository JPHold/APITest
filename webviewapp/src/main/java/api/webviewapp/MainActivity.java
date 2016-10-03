package api.webviewapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView mWebView;
    private Button btnJs;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @android.webkit.JavascriptInterface
    private void initView() {
        mWebView = (WebView) findViewById(R.id.webV);
        mWebView.getSettings().setJavaScriptEnabled(true);//启动js脚本
        mWebView.addJavascriptInterface(MainActivity.this, "android");
        mWebView.setWebViewClient(new MyWebViewClient());//网页内的点击跳转链接，是在webview加载还是在浏览器加载
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);

//        mWebView.getSettings().setSupportZoom(true);//支持缩放
//        mWebView.getSettings().setBuiltInZoomControls(true);//显示缩放按钮
//        String defaultUserAgent = mWebView.getSettings().getDefaultUserAgent();
        String userAgentString = mWebView.getSettings().getUserAgentString();
//        Log.i(TAG, "initView: defaultUserAgent"+defaultUserAgent);
        Log.i(TAG, "initView: userAgentString"+userAgentString);

        //加载html页面
        mWebView.loadUrl("file:///android_asset/html1.html");

        btnJs = (Button) findViewById(R.id.btnJs);
        btnJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljswith(" + "'火火红火'" + ")");
            }
        });
    }

    public void startFunction(String jsText) {
        Toast.makeText(getApplicationContext(), jsText, Toast.LENGTH_LONG).show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Toast.makeText(getApplicationContext(), "" + Uri.parse(url).getHost(), Toast.LENGTH_SHORT).show();
            if (Uri.parse(url).getHost().equals("www.baidu.com")) {
                // This is my web site, so do not override; let my WebView load the page
                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}
