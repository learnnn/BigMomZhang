package com.libtop.bigmomzhang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.libtop.bigmomzhang.adapter.SlideViewPagerAdapter;
import com.libtop.bigmomzhang.utils.LogUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.tablayout)
    SlidingTabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager mPager;


    private long mLastBackPress = 0;

        private final String[] mTitles = {
               "", "笔记本","游戏本","超级本","电磁炉","电视架","电视", "手机"
        };
        private ArrayList<Fragment> mFragments = new ArrayList<>();

    private SlideViewPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


    private void initView()
    {
        mAdapter = new SlideViewPagerAdapter(getSupportFragmentManager(),mTitles);
        mPager.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        tablayout.setViewPager(mPager, mTitles);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                //Do some magic
//                keyword = query;
//                requestData(true);
                mAdapter.addTag(query);
                tablayout.addNewTab(query);
                toolbar.setTitle(query);
                mPager.setCurrentItem(0);
                LogUtil.w(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText)
            {
                //Do some magic
                return false;
            }
        });
        searchView.setVoiceSearch(false);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener()
        {
            @Override
            public void onSearchViewShown()
            {
                //Do some magic
            }


            @Override
            public void onSearchViewClosed()
            {
                //Do some magic
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            long tempTime = System.currentTimeMillis();
            if (tempTime - mLastBackPress < 2000)
            {
                super.onBackPressed();
            }
            else
            {
                Toast.makeText(MainActivity.this, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
            }
            mLastBackPress = tempTime;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
