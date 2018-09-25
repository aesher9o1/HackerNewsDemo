package news.hacker.omnify.hackernews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import news.hacker.omnify.hackernews.R;
import news.hacker.omnify.hackernews.Threads.Interface.CommentFetchWrapper;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private CommentFetchWrapper commentFetchWrapper;

    public CommentAdapter (CommentFetchWrapper commentFetchWrapper){
        this.commentFetchWrapper = commentFetchWrapper;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment,viewGroup,false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.userComment.loadData(commentFetchWrapper.getUserComment().get(i),"text/html; charset=UTF-8", null);
        viewHolder.userName.setText(commentFetchWrapper.getUserName().get(i));

        Date timePosted = new Date(Long.parseLong(commentFetchWrapper.getTimePosted().get(i)));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
        viewHolder.postedTime.setText(formatter.format(timePosted));
    }

    @Override
    public int getItemCount() {
        if(commentFetchWrapper.getTimePosted()==null)
        return 0;
        else
            return commentFetchWrapper.getUserComment().size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView  postedTime, userName;
        WebView userComment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_posted);
            postedTime = itemView.findViewById(R.id.time_posted);
            userComment = itemView.findViewById(R.id.user_comment);

        }
    }


}
