package news.hacker.omnify.hackernews.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import news.hacker.omnify.hackernews.Threads.Interface.CommentFetchInterface;
import news.hacker.omnify.hackernews.Threads.Interface.CommentFetchWrapper;
import news.hacker.omnify.hackernews.constants.UtilityFunctions;


public class CommentFetch extends AsyncTask<String, Void, CommentFetchWrapper> {

    private CommentFetchInterface commentFetchInterface;
    private JSONArray commentsArray;

    public  CommentFetch (CommentFetchInterface commentFetchInterface, JSONArray commentsArray){
        this.commentFetchInterface = commentFetchInterface;
        this.commentsArray = commentsArray;
    }

    @Override
    protected CommentFetchWrapper doInBackground(String... strings) {

        List<String> userComment = new ArrayList<>();
        List<String> userName = new ArrayList<>();
        List<String> timePosted = new ArrayList<>();

        for (int i =0; i<commentsArray.length();i++){
            try {

                final JSONObject newObject = new JSONObject(
                        Jsoup.connect(UtilityFunctions.apiURL+"item/"+ commentsArray.get(i)+".json")
                                .ignoreContentType(true).get().body().text()
                );

                Log.w("Comments", newObject.getString("text"));
                userComment.add(newObject.getString("text"));
                userName.add(newObject.getString("by"));
                timePosted.add(newObject.getString("time"));

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


        }


        return new CommentFetchWrapper(userComment,userName,timePosted);
    }

    @Override
    protected void onPostExecute(CommentFetchWrapper commentFetchWrapper) {
        super.onPostExecute(commentFetchWrapper);
        commentFetchInterface.processFinished(commentFetchWrapper);
    }
}
