package news.hacker.omnify.hackernews.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;



import io.realm.RealmList;
import news.hacker.omnify.hackernews.Threads.Interface.TopNewsInterface;
import news.hacker.omnify.hackernews.Threads.Interface.TopNewsWrapper;
import news.hacker.omnify.hackernews.constants.UtilityFunctions;

public class TopNewsFetcher extends AsyncTask<String, Void, TopNewsWrapper>{

    private TopNewsInterface topNewsInterface;


    public TopNewsFetcher(TopNewsInterface topNewsInterface){
        this.topNewsInterface = topNewsInterface;
    }

    @Override
    protected TopNewsWrapper doInBackground(String... strings) {
        final RealmList<String> by = new RealmList<>();
        final RealmList<String> title = new RealmList<>();
        RealmList<String> URL = new RealmList<>();
        final RealmList<String> Kids = new RealmList<>();
        final RealmList<String> time = new RealmList<>();


        try {

            String fetchedNewsArray = Jsoup.connect(UtilityFunctions.apiURL+"topstories.json")
                    .ignoreContentType(true).get().body().text();

            JSONArray newsArray = new JSONArray(fetchedNewsArray);

            for (int i = 0; i < newsArray.length(); i++) {

                final JSONObject newObject = new JSONObject(
                        Jsoup.connect(UtilityFunctions.apiURL+"item/" + newsArray.get(i) + ".json")
                                .ignoreContentType(true).get().body().text()
                );

                try {
                    URL.add(newObject.getString("url"));
                    by.add(newObject.getString("by"));
                    title.add(newObject.getString("title"));
                    Kids.add(newObject.getString("kids"));
                    Long timeDiff = System.currentTimeMillis()- Long.parseLong(newObject.getString("time"));
                    time.add(""+timeDiff) ;

                } catch (Exception e){
                        Log.w("TopNewsFetcher", e);
                }

            }


            return new TopNewsWrapper(by, title, URL, time, Kids);

        } catch (Exception e) {
            Log.w("Hacked Fetch Exception", "" + e);
        }

        return new TopNewsWrapper(by, title, URL, time, Kids);

    }

    @Override
    protected void onPostExecute(TopNewsWrapper topNewsWrapper) {
        super.onPostExecute(topNewsWrapper);
        topNewsInterface.processFinished(topNewsWrapper);
    }
}
