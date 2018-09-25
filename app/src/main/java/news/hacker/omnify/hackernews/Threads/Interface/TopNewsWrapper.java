package news.hacker.omnify.hackernews.Threads.Interface;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TopNewsWrapper extends  RealmObject{
    private RealmList<String> author, Title, URL, Kids;
    private RealmList<String> time;

    public TopNewsWrapper(){}

    public TopNewsWrapper(RealmList<String> by, RealmList<String> Title, RealmList<String> URL, RealmList<String> time, RealmList<String> Kids) {
        this.author = by;
        this.Title = Title;
        this.URL = URL;
        this.time = time;
        this.Kids = Kids;
    }

    public void setAuthor(RealmList<String> author) {
        this.author = author;
    }

    public void setKids(RealmList<String> kids) {
        Kids = kids;
    }

    public void setTime(RealmList<String> time) {
        this.time = time;
    }

    public void setTitle(RealmList<String> title) {
        Title = title;
    }

    public void setURL(RealmList<String> URL) {
        this.URL = URL;
    }



    public RealmList<String> getTime() {
        return time;
    }

    public RealmList<String> getBy() {
        return author;
    }

    public RealmList<String> getTitle() {
        return Title;
    }

    public RealmList<String> getURL() {
        return URL;
    }

    public RealmList<String> getKids() {
        return Kids;
    }
}
