package com.bignerdranch.android.httpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new LinkedList<>();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //加载网页信息
        initFruits();
        //菜单点击事件
        navView.setCheckedItem(R.id.nav_duanzi);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()==R.id.nav_duanzi){
                    //加载布局
                    initRecycler();
                    mDrawerLayout.closeDrawers();
                }else {
                    mDrawerLayout.closeDrawers();
                }
                return true;

            }
        });

        //FloatingActionButton点击事件
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo",new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"You Clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You Clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You Clicked Settings",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //将数据添加到RecyclerView中
    public void initFruits(){
        new Thread(mRunnable).start();
    }

    public void initRecycler(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run(){
            try {
                Document doc = Jsoup.connect("http://www.qiushibaike.com/8hr/page/").get();
                Elements els = doc.select("a.contentHerf");
                for (int i = 0; i < 20; i++) {
                    Element el = els.get(i);
                    String href = el.attr("href");//链接
                    Document doc_detail = Jsoup.connect("http://www.qiushibaike.com" + href).get();
                    Elements els_neirong = doc_detail.select(".content");

                    Elements els_zuozhe = doc_detail.select("h2");
                    Log.e("作者", els_zuozhe.text());

                    //取出页面内的评论
                    Elements els_pinglun = doc_detail.select(".main-text");
                    Log.e("评论：", els_pinglun.text());
                    //页面有多条评论，取出第一条

                    String pinglun=null;

                    if(els_pinglun.size()==0){
                        pinglun="暂无";
                    }else {
                        pinglun = els_pinglun.get(0).text();
                        Log.e("评论：", pinglun);
                    }


                    String zuozhe = "作者："+els_zuozhe.text();
                    String neirong = els_neirong.text();

                    Fruit one = new Fruit(i + zuozhe,neirong,"神评论："+ pinglun);
                    fruitList.add(one);

                    Log.e("内容", els_neirong.text());
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            mHandler.sendEmptyMessage(0);
        }
    };

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };

}
