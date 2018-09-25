package news.hacker.omnify.hackernews.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import news.hacker.omnify.hackernews.R;

import news.hacker.omnify.hackernews.Threads.Interface.TopNewsWrapper;
import news.hacker.omnify.hackernews.activities.ContentReader;

public class MainNewsAdapter extends RecyclerView.Adapter<MainNewsAdapter.ViewHolder> {

    private TopNewsWrapper topNewsWrapper;
    private Context context;

    public MainNewsAdapter(Context context, TopNewsWrapper topNewsWrapper){
        this.context = context;
        this.topNewsWrapper = topNewsWrapper;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_top_news,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.newsTitle.setText(topNewsWrapper.getTitle().get(i));
        viewHolder.newsAuthor.setText(topNewsWrapper.getBy().get(i));

        Date timePosted = new Date(Long.parseLong(topNewsWrapper.getTime().get(i)));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
        viewHolder.timePassed.setText(formatter.format(timePosted));

        try {

            JSONArray newsCount = new JSONArray(topNewsWrapper.getKids().get(i));
            viewHolder.commentCount.setText(""+newsCount.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewHolder.cardBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String comments = topNewsWrapper.getKids().get(viewHolder.getAdapterPosition());

                Intent i = new Intent(context , ContentReader.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Comments", comments);
                i.putExtra("URL", topNewsWrapper.getURL().get(viewHolder.getAdapterPosition()));

                context.startActivity(i,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (topNewsWrapper.getURL() != null)
            return topNewsWrapper.getURL().size();
        else
            return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView newsTitle, timePassed, commentCount, newsAuthor;
        CardView cardBody;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            timePassed = itemView.findViewById(R.id.time_past);
            commentCount = itemView.findViewById(R.id.comment);
            newsAuthor = itemView.findViewById(R.id.user_name);
            cardBody = itemView.findViewById(R.id.card_body);
        }
    }
}
