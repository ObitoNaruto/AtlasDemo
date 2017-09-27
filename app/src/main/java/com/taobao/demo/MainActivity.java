package com.taobao.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.middleware.dialog.Dialog;
import com.taobao.android.ActivityGroupDelegate;
import com.taobao.update.Updater;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ActivityGroupDelegate mActivityDelegate;
    private ViewGroup mActivityGroupContainer;

    //底部BottomNavigationView点击监听
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home://goFirst
                    //跳转第一个bundle中的FirstBundleActivity
                    switchToActivity("home","com.taobao.firstbundle.FirstBundleActivity");
                    Toast.makeText(RuntimeVariables.androidApplication,"on click",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard://goSecond
                    //跳转第二个bundle的SecondBundleActivity
                    switchToActivity("second","com.taobao.secondbundle.SecondBundleActivity");
                    Toast.makeText(RuntimeVariables.androidApplication, "goSecond onClick", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications://待定
                    Intent intent3 = new Intent();
                    intent3.setClassName(getBaseContext(),"com.taobao.firstBundle.FirstBundleActivity");
                    mActivityDelegate.execStartChildActivityInternal(mActivityGroupContainer,"third",intent3);
                    Toast.makeText(MainActivity.this, "待定被点击", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("ddddd","dsfsfsf");
        //最外层layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //底部BottomNavigationView(goFirst, foSecond, 待定)
        ((BottomNavigationView)findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //侧滑NavigationView，当前从左边滑出
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mActivityDelegate = new ActivityGroupDelegate(this,savedInstanceState);
        //显示内容容器
        mActivityGroupContainer = (ViewGroup) findViewById(R.id.content);
        //加载了firstbundle的FirstBundleActivity
        switchToActivity("home","com.taobao.firstbundle.FirstBundleActivity");
    }

    public void switchToActivity(String key, String activityName){
        Intent intent = new Intent();
        intent.setClassName(getBaseContext(),activityName);//getBaseContext:ContextImplHook
        mActivityDelegate.startChildActivity(mActivityGroupContainer,key,intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent();
            intent.setClassName(this,"com.taobao.firstbundle.WebViewDemoActivity");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        }
//        else
            if (id == R.id.nav_slideshow) {//动态部署模拟
//            Intent intent = new Intent();
//            intent.setClassName(this,"com.taobao.demo.UpdateDemoActivity");
                Intent intent = new Intent(this, UpdateDemoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {//远程组件模拟

            Intent intent = new Intent();
                intent.setPackage(getPackageName());
            intent.setClassName(this,"com.taobao.demo.RemoteDemoActivity");
            startActivity(intent);

        } else if (id == R.id.awo_manager) {//单模块调试模拟
            Dialog dialog = new Dialog(this,"单bundle调试",
                    "1、安装设备且连接电脑成功\n\n"+
                     "2、修改一个bundle工程的代码或者自由（设置生效的标识）\n\n"+
                            "3、bundle工程的目录下执行 ../gradlew clean assemblePatchDebug,然后等应用重启或者应用关闭后点击重启");

            dialog.show();


        } else if (id == R.id.nav_dex_patch) {//dex_patch模拟
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Updater.dexPatchUpdate(getBaseContext());
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }.execute();
        }else if (id == R.id.nav_databind_bundle) {//databinding模拟

                Intent intent = new Intent();
                intent.setPackage(getPackageName());
                intent.setClassName(this,"com.taobao.databindbundle.databind.DataBundleSampleActivity");
                startActivity(intent);
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}