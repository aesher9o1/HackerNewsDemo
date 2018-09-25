package news.hacker.omnify.hackernews.constants;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Objects;

import news.hacker.omnify.hackernews.R;

public class UtilityFunctions {

    public static String apiURL = "https://hacker-news.firebaseio.com/v0/";

    public static void makeToast(String message, Context mContext){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }



}
