package news.hacker.omnify.hackernews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import news.hacker.omnify.hackernews.R;
import news.hacker.omnify.hackernews.Threads.CommentFetch;
import news.hacker.omnify.hackernews.Threads.Interface.CommentFetchInterface;
import news.hacker.omnify.hackernews.Threads.Interface.CommentFetchWrapper;
import news.hacker.omnify.hackernews.Threads.Interface.TopNewsWrapper;
import news.hacker.omnify.hackernews.adapter.CommentAdapter;
import news.hacker.omnify.hackernews.adapter.MainNewsAdapter;
import news.hacker.omnify.hackernews.constants.UtilityFunctions;

public class Comments extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    JSONArray commentsArray;
    CommentFetchWrapper commentFetchWrapper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comments, container , false);
        ButterKnife.bind(this,v);

        try {
           commentsArray =  new JSONArray(Objects.requireNonNull(getArguments()).getString("Comments"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);


        commentFetchWrapper = new CommentFetchWrapper(null,null,null);
        recyclerView.setAdapter(new CommentAdapter(commentFetchWrapper));

        new CommentFetch(new CommentFetchInterface() {
            @Override
            public void processFinished(CommentFetchWrapper commentFetchWrapper) {

                CommentAdapter commentAdapter = new CommentAdapter(commentFetchWrapper);
                recyclerView.setAdapter(commentAdapter);

            }
        }, commentsArray).execute();

        UtilityFunctions.makeToast("Loading Comments",getContext());
        return v;
    }
}
