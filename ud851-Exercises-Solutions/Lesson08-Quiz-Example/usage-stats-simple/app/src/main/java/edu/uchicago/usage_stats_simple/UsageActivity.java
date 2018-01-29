package edu.uchicago.usage_stats_simple;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class UsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        String PackageName = "Nothing";

        long TimeInforground = 500 ;

        int minutes=500,seconds=500,hours=500 ;
        UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService("usagestats");

        long time = System.currentTimeMillis();

        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*10, time);
        Log.i("BAC" , "Stats available.");

        if (stats != null) {

            Log.i("BAC" , "Stats available.");

            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {

                TimeInforground = usageStats.getTotalTimeInForeground();

                PackageName = usageStats.getPackageName();

                minutes = (int) ((TimeInforground / (1000 * 60)) % 60);

                seconds = (int) (TimeInforground / 1000) % 60;

                hours = (int) ((TimeInforground / (1000 * 60 * 60)) % 24);

                Log.i("BAC", "PackageName is" + PackageName + "Time is: " + hours + "h" + ":" + minutes + "m" + seconds + "s");

            }
        }else {
            Log.i("BAC" , "Stats not available");
        }
    }
}
