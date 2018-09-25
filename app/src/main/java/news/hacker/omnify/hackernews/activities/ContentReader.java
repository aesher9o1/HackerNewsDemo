package news.hacker.omnify.hackernews.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



import butterknife.BindView;
import butterknife.ButterKnife;
import news.hacker.omnify.hackernews.R;
import news.hacker.omnify.hackernews.adapter.ReaderAdapter;

public class ContentReader extends AppCompatActivity {


    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_reader);

        ButterKnife.bind(this);

        String comments = getIntent().getStringExtra("Comments");
        String URL = getIntent().getStringExtra("URL");

        Log.w("URL", URL);

        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        ReaderAdapter readerAdapter = new ReaderAdapter(getSupportFragmentManager(), URL, comments);
        viewPager.setAdapter(readerAdapter);
        viewPager.setCurrentItem(0);


    }
}
