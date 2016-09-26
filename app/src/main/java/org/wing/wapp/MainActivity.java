package org.wing.wapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import com.avos.avoscloud.AVUser;

import org.wing.wapp.navigation.AboutFragment;
import org.wing.wapp.navigation.BlogFragment;
import org.wing.wapp.navigation.ExperienceFragment;
import org.wing.wapp.navigation.StatusFragment;
import org.wing.wapp.ui.StatusSendActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liao on 2016/9/23.
 * 处理底部导航、侧边栏、发送status
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SEND_REQUEST = 2;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.nav_view)
    NavigationView navigationView;
    private StatusFragment statusFragment;
    private BlogFragment blogFragment;
    private ExperienceFragment experienceFragment;
    private AboutFragment aboutFragment;
    private RadioGroup myTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        //初始化底部导航
        initView();
        Initialization.registerUser(AVUser.getCurrentUser());
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

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.add_status:
                    Intent intent = new Intent(MainActivity.this, StatusSendActivity.class);
                    startActivityForResult(intent, SEND_REQUEST);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_nickname) {
            // Handle the camera action
        } else if (id == R.id.nav_edu) {

        } else if (id == R.id.nav_gender) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //初始化底部导航
    public void  initView(){
        statusFragment = new StatusFragment();
        blogFragment=new BlogFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content,blogFragment).commit();
        myTab=(RadioGroup)findViewById(R.id.tab_menu);
        myTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.w_index:
                        blogFragment=new BlogFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content,blogFragment)
                                .commit();
                        break;
                    case R.id.w_status:

                        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, statusFragment)
                                .commit();
                        break;
                    case R.id.w_experience:
                        experienceFragment = new ExperienceFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, experienceFragment)
                                .commit();
                        break;
                    case R.id.w_wing:
                        aboutFragment = new AboutFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.ly_content, aboutFragment)
                                .commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
