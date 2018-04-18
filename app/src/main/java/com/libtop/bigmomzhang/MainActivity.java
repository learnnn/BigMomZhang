package com.libtop.bigmomzhang;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.libtop.bigmomzhang.adapter.SlideViewPagerAdapter;
import com.libtop.bigmomzhang.utils.SharedPrefsStrListUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    @BindView(R.id.activity_main)
//    LinearLayout activityMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.search_view)
//    MaterialSearchView searchView;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager mPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Context mContext;

    private SearchView searchView;

    private long mLastBackPress = 0;

    private String[] mTitles = {
            "笔记本", "游戏本", "超级本", "冰箱", "电磁炉", "电视架", "电视", "手机", "happy", "710s"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private SlideViewPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchItem.getActionView();

        if (searchItem != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Toast like print
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//                    TabLayout tab = new TabLayout(MainActivity.this);
//                    tab.addTab(tab.newTab().setTag(query),0,true);

                    tablayout.addTab(tablayout.newTab().setTag(query));
                    mAdapter.addTag(query);
//                    tablayout.setselect
                    //                    tablayout.addNewTab(query);
//                    toolbar.setTitle(query);
                    mPager.setCurrentItem(0);
                    //去除焦点，防止执行两次搜索
                    searchView.clearFocus();
                    //清空搜索输入字符，防止执行两次搜索
                    searchView.setIconified(true);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                    return false;
                }
            });
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }


    private void initView() {
        List<String> list = SharedPrefsStrListUtil.getStrListValue(this, "Titles");
        for (String item: list){
            Log.w("guanglog",item + "\n");
        }
        if (list != null && !list.isEmpty())
            mTitles = list.toArray(new String[0]);
        for (String item: list){
            Log.w("guanglog",item + " next\n");
        }
        mAdapter = new SlideViewPagerAdapter(getSupportFragmentManager(), mTitles);
        mPager.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        tablayout.setupWithViewPager(mPager);
//        tablayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "click...", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tablayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return false;
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tablayout.getTabCount() > 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                            .setTitle("是否删除当前标签？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TabLayout.Tab tab = tablayout.getTabAt(tablayout.getSelectedTabPosition());
                                    String tabText = tab.getText().toString();
                                    tablayout.removeTab(tab);
                                    mAdapter.removeTag(tabText);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    alertDialog.show();
                }else {
                    Toast.makeText(mContext, "请先添加标签", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        tablayout.setViewPager(mPager, mTitles);

//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
//        {
//            @Override
//            public boolean onQueryTextSubmit(String query)
//            {
//                //Do some magic
//                keyword = query;
//                requestData(true);
//                mAdapter.addTag(query);
//                tablayout.addNewTab(query);
//                toolbar.setTitle(query);
//                mPager.setCurrentItem(0);
//                LogUtil.w(query);
//                return false;
//            }


//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//                //Do some magic
//                return false;
//            }
//        });
//        searchView.setVoiceSearch(false);
//
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener()
//        {
//            @Override
//            public void onSearchViewShown()
//            {
//                //Do some magic
//            }
//
//
//            @Override
//            public void onSearchViewClosed()
//            {
//                //Do some magic
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long tempTime = System.currentTimeMillis();
            if (tempTime - mLastBackPress < 2000) {
                super.onBackPressed();
            } else {
                Toast.makeText(MainActivity.this, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
            }
            mLastBackPress = tempTime;
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        searchView.clearFocus();
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
