package com.webview.nan.androidwebview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * 进行资源的拦截、检测 & 替换
 *
 * @author Nan
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建WebView对象
        mWebView = (WebView) findViewById(R.id.webview);

        //支持与JS交互
        mWebView.getSettings().setJavaScriptEnabled(true);

        //加载需要显示的网页
        mWebView.loadUrl("http://ip.cn/");

        mWebView.setWebViewClient(new WebViewClient() {
            // 复写shouldInterceptRequest
            //API21以下用shouldInterceptRequest(WebView view, String url)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
                // 此处网页里图片的url为:http://s.ip-cdn.com/img/logo.gif
                // 图片的资源文件名为:logo.gif
                if (url.contains("logo.gif")) {
                    // 步骤2:创建一个输入流
                    InputStream is = null;
                    try {
                        // 步骤3:打开需要替换的资源(存放在assets文件夹里)
                        // 在app/src/main下创建一个assets文件夹
                        // assets文件夹里再创建一个images文件夹,放一个error.png的图片
                        is = getApplicationContext().getAssets().open("images/error.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 步骤4:替换资源
                    // 参数1:http请求里该图片的Content-Type,此处图片为image/png
                    // 参数2:编码类型
                    // 参数3:替换资源的输入流
                    WebResourceResponse response = new WebResourceResponse("image/png", "utf-8", is);

                    Log.d(TAG, "旧API");
                    return response;
                }

                return super.shouldInterceptRequest(view, url);
            }


            // API21以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
                // 此处图片的url为:http://s.ip-cdn.com/img/logo.gif
                // 图片的资源文件名为:logo.gif
                if (request.getUrl().toString().contains("logo.gif")) {
                    // 步骤2:创建一个输入流
                    InputStream is = null;
                    try {
                        // 步骤3:打开需要替换的资源(存放在assets文件夹里)
                        // 在app/src/main下创建一个assets文件夹
                        // assets文件夹里再创建一个images文件夹,放一个error.png的图片
                        is = getApplicationContext().getAssets().open("images/error.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //步骤4:替换资源
                    // 参数1：http请求里该图片的Content-Type,此处图片为image/png
                    // 参数2：编码类型
                    // 参数3：存放着替换资源的输入流（上面创建的那个）
                    WebResourceResponse response = new WebResourceResponse("image/png", "utf-8", is);

                    Log.d(TAG, "新API");
                    return response;
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
    }
}
