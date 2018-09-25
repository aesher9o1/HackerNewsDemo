package news.hacker.omnify.hackernews.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import news.hacker.omnify.hackernews.fragments.ArticleView;
import news.hacker.omnify.hackernews.fragments.Comments;

public class ReaderAdapter extends FragmentPagerAdapter {

    String article_comments, URL;

    public ReaderAdapter(FragmentManager fm, String URL, String article_comments) {
        super(fm);
        this.article_comments = article_comments;
        this.URL = URL;
    }

    @Override
    public Fragment getItem(int i) {

        //Instance of comment
        Comments comments = new Comments();
        Bundle commentBundle = new Bundle();
        commentBundle.putString("Comments",article_comments);
        comments.setArguments(commentBundle);

        //Instance of ArticleView
        ArticleView articleView = new ArticleView();
        Bundle webViewBundle = new Bundle();
        webViewBundle.putString("URL",URL);
        articleView.setArguments(webViewBundle);

        switch (i){
            case 0: return comments;
            case 1: return articleView;
            default: return articleView;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        int comment_size = 0;
        try {
            JSONArray comments = new JSONArray(article_comments);
            comment_size = comments.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (position){
            case 0: return comment_size+" Comments";
            case 1: return  "Article";
            default: return  "Top Comments";
        }
    }
}
