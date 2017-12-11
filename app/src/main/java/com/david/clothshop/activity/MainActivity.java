package com.david.clothshop.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.david.clothshop.Fragment.HomeFragment;
import com.david.clothshop.Fragment.IncomeFragment;
import com.david.clothshop.Fragment.QrFragment;
import com.david.clothshop.Fragment.ShoppingCartFragment;
import com.david.clothshop.Fragment.UserServiceFragment;
import com.david.clothshop.R;
import com.david.clothshop.common.BaseActivity;
import com.david.clothshop.net.Request.TestRequest;
import com.david.clothshop.net.bean.GitHubRepo;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager fragmentManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        upDateFragment(new HomeFragment());
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.id_button_group);
        radioGroup.setOnCheckedChangeListener(this);
        Thread thread =new Thread(){
            @Override
            public void run() {
                super.run();
                List<GitHubRepo> list = TestRequest.test("davidrou");
                for(GitHubRepo repo : list){
                    Log.d("LZW",repo.getName()+": "+repo.getId());
                }
            }
        };
        thread.start();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.id_activity_main_home:
                upDateFragment(new HomeFragment());
                break;
            case R.id.id_activity_main_income:
                upDateFragment(new IncomeFragment());
                break;
            case R.id.id_activity_main_qr_code:
                upDateFragment(new QrFragment());
                break;
            case R.id.id_activity_main_shopping_cart:
                upDateFragment(new ShoppingCartFragment());
                break;
            case R.id.id_activity_main_customer_service:
                upDateFragment(new UserServiceFragment());
                break;
        }
    }

    private void upDateFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
    }
}
