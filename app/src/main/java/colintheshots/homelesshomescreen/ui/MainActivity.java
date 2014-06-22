package colintheshots.homelesshomescreen.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import colintheshots.homelesshomescreen.R;

/**
 * Created by colintheshots on 6/21/14.
 */
public class MainActivity extends ActionBarActivity {

    Map<String, String> menuMap = new HashMap<String, String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        menuMap.put("Benefits","benefits.json");
        menuMap.put("Health","health.json");
        menuMap.put("Employment","employment.json");

        SpinnerAdapter mSpinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.item_dropdown_spinner, menuMap.keySet().toArray(new String[menuMap.keySet().size()]));

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        final String[] strings = getResources().getStringArray(R.array.testArray);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
//                Log.e("REMOVEME", strings[i]);
                String key = Iterables.get(menuMap.keySet(), i);
                StringBuilder json=new StringBuilder();

                try {
                    Log.e("TEST",menuMap.get(key));
                    InputStream inputStream = getAssets().open(menuMap.get(key));

                    BufferedReader in=
                            new BufferedReader(new InputStreamReader(inputStream));

                    String rawJson = "";

                    while ((rawJson=in.readLine()) != null) {
                        json.append(rawJson);
                    }

                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String,String> map=new HashMap<String,String>();

                map = (Map<String,String>) new Gson().fromJson(json.toString(), map.getClass());
                return false;
            }
        });
    }

}
