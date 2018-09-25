package news.hacker.omnify.hackernews.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import news.hacker.omnify.hackernews.R;
import news.hacker.omnify.hackernews.Threads.Interface.TopNewsInterface;
import news.hacker.omnify.hackernews.Threads.Interface.TopNewsWrapper;
import news.hacker.omnify.hackernews.Threads.TopNewsFetcher;
import news.hacker.omnify.hackernews.adapter.MainNewsAdapter;
import news.hacker.omnify.hackernews.constants.UtilityFunctions;

public class MainActivity extends AppCompatActivity {

    /**
     * @param topNewsWrapper -> Holds the object of the news fetched from the API
     */

    FirebaseAuth mAuth;
    Realm realm;

    TopNewsWrapper topNewsWrapper;


    @BindView(R.id.loading_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check auth status of user
        mAuth = FirebaseAuth.getInstance();
        realm = Realm.getDefaultInstance();

        if ((mAuth.getCurrentUser() == null) || mAuth.getCurrentUser().getPhoneNumber() == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.title)
                            .setTheme(R.style.LoginTheme)
                            .build(),
                    0);

        }

        //User is authenticated all logic below this

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        //initially the wrapper is null waiting to be filled by async task
        topNewsWrapper = new TopNewsWrapper(null    ,null,null,null,null);
        recyclerView.setAdapter(new MainNewsAdapter(this,topNewsWrapper));


        RealmQuery<TopNewsWrapper> query = realm.where(TopNewsWrapper.class);
        TopNewsWrapper topNewsWrapper = query.findFirst();


            Log.w("Hacked",""+query.findAll().size());

       new TopNewsFetcher(new TopNewsInterface() {
           @Override
           public void processFinished(TopNewsWrapper newsWrapper) {

               progressBar.setVisibility(View.GONE);

               MainNewsAdapter ieeeAdapter = new MainNewsAdapter(getApplicationContext(), newsWrapper);
               recyclerView.setAdapter(ieeeAdapter);


               recyclerView.setVisibility(View.VISIBLE);

               realm.executeTransactionAsync(new Realm.Transaction() {
                   @Override
                   public void execute(@NonNull Realm bgRealm) {
                       TopNewsWrapper newsWrapper = bgRealm.createObject(TopNewsWrapper.class);
                       newsWrapper.setAuthor(newsWrapper.getBy());
                       newsWrapper.setTime(newsWrapper.getTime());
                       newsWrapper.setTitle(newsWrapper.getTitle());
                       newsWrapper.setKids(newsWrapper.getKids());
                       newsWrapper.setTitle(newsWrapper.getTitle());
                   }
               }, new Realm.Transaction.OnSuccess() {
                   @Override
                   public void onSuccess() {
                       Log.w("Realm", "Done");
                   }
               }, new Realm.Transaction.OnError() {
                   @Override
                   public void onError(Throwable error) {
                       Log.w("Realm", error);
                   }
               });


           }
       }).execute();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            } else {
                UtilityFunctions.makeToast("An Error Occured", this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
