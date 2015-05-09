package io.stalk.android.editorial;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.MaterialViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.stalk.android.editorial.fragment.RecyclerViewFragment;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        JsonObjectRequest request = new JsonObjectRequest(getString(R.string.URL_INFO),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray menus = response.getJSONArray("menu");
                            Map<String, io.stalk.android.editorial.data.MenuItem> menuMap = new HashMap<String, io.stalk.android.editorial.data.MenuItem>();
                            for(int i=0; i<menus.length(); i++){
                                JSONObject menu = menus.getJSONObject(i);
                                io.stalk.android.editorial.data.MenuItem menuItem = new io.stalk.android.editorial.data.MenuItem();
                                menuItem.setPosition(menu.getInt("position"));
                                menuItem.setId(menu.getString("id"));
                                menuItem.setName(menu.getString("name"));
                                menuItem.setBkColor(menu.getString("bkColor"));

                                menuMap.put(String.valueOf(menuItem.getPosition()), menuItem);
                            }

                            initViewPage(menuMap);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }

        );

        AppController.getInstance().addToRequestQueue(request);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initViewPage(final Map<String, io.stalk.android.editorial.data.MenuItem> menuItems){

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            int oldPosition = -1;

            @Override
            public int getCount() {
                return menuItems.size();
            }

            @Override
            public Fragment getItem(int position) {
                Log.i(TAG, "getItem : " + String.valueOf(position));
                String pos = String.valueOf(position);
                return RecyclerViewFragment.newInstance(menuItems.get(pos).getId());
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                //only if position changed
                if(position == oldPosition)
                    return;
                oldPosition = position;

                int color = 0;
                String imageUrl = "";

                String pos = String.valueOf(position);

                io.stalk.android.editorial.data.MenuItem m = menuItems.get(pos);

                Log.i(TAG, "setPrimaryItem : "+String.valueOf(position)+" "+m.getBkColor());

                final int fadeDuration = 400;
                //mViewPager.setImageUrl(imageUrl,fadeDuration);
                mViewPager.setColor(Color.parseColor(m.getBkColor()),fadeDuration);

            }

            @Override
            public CharSequence getPageTitle(int position) {
                String pos = String.valueOf(position);
                return menuItems.get(pos).getName();
            }

        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }
}
