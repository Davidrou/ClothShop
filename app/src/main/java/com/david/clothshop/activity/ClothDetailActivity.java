package com.david.clothshop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.david.clothshop.R;

import java.util.ArrayList;

/**
 * Created by luxiaolin on 17/10/11.
 */

public class ClothDetailActivity extends Activity {

    private WebView mWebView;
    private ConvenientBanner mConvenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_detail);
        mWebView = (WebView) findViewById(R.id.webview_detail_activity);
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.banner_cloth_detail);
        loadData();
    }

    private void loadData() {
        localImages.add(R.drawable.test2);
        localImages.add(R.drawable.test3);
        localImages.add(R.mipmap.test1);
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                // /设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(1500)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                //设置点击监听事件
//                .setOnItemClickListener(this)
                //设置手动影响（设置了该项无法手动切换）
//                .setManualPageable(true);
        mWebView.loadUrl("http://www.jianshu.com/p/3c94ae673e2a");
    }


    private class LocalImageHolderView implements Holder<Integer>{
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    public static void startActivity(Context context){
        Intent intent =new Intent(context,ClothDetailActivity.class);
        context.startActivity(intent);
    }
}
