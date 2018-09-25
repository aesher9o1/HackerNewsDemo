package news.hacker.omnify.hackernews;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Hacker extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("hacker.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
