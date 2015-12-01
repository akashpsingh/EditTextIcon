package com.karma.followback.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.karma.followback.R;
import com.karma.followback.utils.Session;
import com.karma.followback.constants.AppProperties;


public class InstagramRedirectLoginActivity extends ActionBarActivity {
    private WebView webView;
    private ProgressBar pbar;
    private Session session;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_login);

        webView = (WebView)findViewById(R.id.webview);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new Session(this);
        getSupportActionBar().setTitle("Instagram Login");


        CookieManager cookieManager = CookieManager.getInstance();
        if(Build.VERSION.SDK_INT >= 21)
        {
            cookieManager.removeAllCookies(null);
        }
        else {
            cookieManager.removeAllCookie();
        }

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(AppProperties.authURLString);
        //make sure no autofill of forms
        webView.clearFormData();
        webView.getSettings().setSaveFormData(false);
        //to make sure no caching is done
        webView.getSettings().setCacheMode(webView.getSettings().LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class AuthWebViewClient extends WebViewClient {

        public AuthWebViewClient(){
            pbar.setVisibility(View.VISIBLE);
            pbar.bringToFront();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(AppProperties.CALLBACKURL)) {
                System.out.println(url);
                String parts[] = url.split("=");
                String request_token = parts[1];  //This is your request token.
                System.out.println("Request Token="+request_token);
                session.setValue(AppProperties.TOKEN, request_token);
                session.commit();
                //passing back the data to the calling activity
                Intent back = new Intent();
                back.putExtra(AppProperties.TOKEN, request_token);
                setResult(RESULT_OK,back);
                finish();
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pbar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbar.setVisibility(View.GONE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instagram_login, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            webView.reload();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
