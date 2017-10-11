package com.david.clothshop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.david.clothshop.R;

/**
 * Created by luxiaolin on 17/10/11.
 */

public class ClothDetailActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_detail);
    }

    public static void startActivity(Context context){
        Intent intent =new Intent(context,ClothDetailActivity.class);
        context.startActivity(intent);
    }
}
