package com.Amro.jobfinder.Controller.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.Amro.jobfinder.Controller.Utils.Constants;
import com.Amro.jobfinder.R;
import android.net.http.*;

/**
 * Created by Amro 13/4/2019
 * A fragment to display job details URL in a webview, after receiving it from MainFragment
 */
public class JobDetailsFragment extends BaseFragment  {
    private WebView webView;
    private String Url;

    /**
     * used to get the job details URL on fragment creation.
     * @param Url the string that has the job details url.
     * @return a fragment with the bundled data to be used in fragment creation.
     */
    public static JobDetailsFragment newInstance(String Url) {

        Bundle args = new Bundle();
        JobDetailsFragment fragment = new JobDetailsFragment();
        args.putString(Constants.JOB_URL,Url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            Url=getArguments().getString(Constants.JOB_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_details,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView=findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);//to handle https loading issues

        //webview initialising and loading the URL.
        if(Url!=null)
        {
            webView.loadUrl(Url);
        }
        webView.setWebViewClient(new WebViewClient()
        {   //to prevent user leaving the app on clicking a link in the webview, instead its loaded in the application
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener(){
            //listener for back key(hardware) so on back pressed it moves back to previous page instead of previous fragment all the time.
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

    }


}
