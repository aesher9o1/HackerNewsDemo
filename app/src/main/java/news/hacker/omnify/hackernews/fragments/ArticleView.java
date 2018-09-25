package news.hacker.omnify.hackernews.fragments;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import  news.hacker.omnify.hackernews.R;
import news.hacker.omnify.hackernews.constants.UtilityFunctions;

public class ArticleView extends Fragment {

    @BindView(R.id.webview)
    WebView articleLoad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_webview, container , false);

        ButterKnife.bind(this,v);


        articleLoad.getSettings().setJavaScriptEnabled(true);
        articleLoad.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                UtilityFunctions.makeToast(description,getContext());
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        articleLoad .loadUrl(Objects.requireNonNull(getArguments()).getString("URL"));




        return v;
    }
}
