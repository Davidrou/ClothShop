package com.david.clothshop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.david.clothshop.R;
import com.david.clothshop.adapter.ProperyTagAdapter;
import com.david.clothshop.customize.FlowTagLayout;
import com.david.clothshop.customize.OnTagSelectListener;
import com.david.clothshop.customize.TagInfo;
import com.david.clothshop.dialog.GoodSelectDialog;
import com.david.clothshop.net.Request.GetGoodDetailRequest;
import com.david.clothshop.net.bean.GoodDetailResponseBean;
import com.david.clothshop.net.bean.ResponseData;
import com.david.clothshop.utils.ThreadPool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxiaolin on 17/10/11.
 */

public class ClothDetailActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private ConvenientBanner mConvenientBanner;
    private ArrayList<String> mBannerImages = new ArrayList<String>();
    private Button mBuyButton;
    private static final String KEY_PRAMS_GOOD_ID = "key_prams_good_id";
    private static final String TAG = "ClothDetailActivity";
    private Handler mHandler = new Handler();
    private  TextView mPriceTextView, mTitielTextView;
    private int shopNum = 1;
    /**
     * 颜色 内存 分期 数量
     */
    private TextView tvColor, tvMomey, tvNum;
    /**
     * 增加、减少
     */
    private Button btnCut, btnAdd;

    /**
     * 弹窗
     */
    public GoodSelectDialog mBottomSheetDialog;
    /**
     * 引用上下文
     */
    private Activity mActivity;
    /**
     * 弹窗布局
     */
    private View contentView;
    /**
     * 颜色列表
     */
    private FlowTagLayout rlShopColor;
    /**
     * 内存列表
     */
    private FlowTagLayout rlShopMomery;
    /**
     * 版本
     */
    private FlowTagLayout rlShopVersion;

    /**
     * 分期列表
     */
    private FlowTagLayout rlShopStages;
    private String parentid;
    private String strColor;
    private String strMemory;
    private String strVersion;
    private List<TagInfo> mColors;
    private List<String> mTempColors;
    private List<TagInfo> mMonerys;
    private List<String> mTempMonerys;
    private List<TagInfo> mStages;
    private List<String> mTempStages;
    private List<TagInfo> mVersions;
    private List<String> mTempVersions;
    private List<String> mImages;
    private TextView tvVersion;

    private TextView tvPrice;
    private TextView tvShopName;
    private ImageView ivShopPhoto;
    private String mShopImageUrl = "";
    private String strStages;
    private int versionPositon = 0;
    private int momeryPositon = 0;
    private int colorPositon = 0;
    private int initShopStagesCount = 1;
    private LinearLayout llBuyStages;
    private Button goInput;
    private List<String> tempImageColor;
    private int goodId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodId = getIntent().getIntExtra(KEY_PRAMS_GOOD_ID, 0);
        setContentView(R.layout.activity_cloth_detail);
        mWebView = (WebView) findViewById(R.id.webview_detail_activity);
        WebSettings webSettings = mWebView.getSettings();

        //设置自适应屏幕，两者合用
        mBuyButton = (Button) findViewById(R.id.button_buy_now);
        mBuyButton.setOnClickListener(this);
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.banner_cloth_detail);
        mPriceTextView = (TextView) findViewById(R.id.text_price_detail_activity);
        mTitielTextView = (TextView) findViewById(R.id.text_cloth_name_detail_activity);
        loadData();
    }

    private void loadData() {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ResponseData<GoodDetailResponseBean> responseData = GetGoodDetailRequest.request(goodId);
                if(responseData==null || !"OK".equals(responseData.getCode())){
                    Log.e(TAG, responseData==null?"responseBean is null":("Response Code : "+responseData.getCode()));
                    return;
                }
                GoodDetailResponseBean responseBean =  responseData.getData();
                GoodDetailResponseBean.GoodDetail goodDetail = responseBean.getGoodDetail();
                if(goodDetail == null){
                    Log.e(TAG, "goodDetail is null");
                    return;
                }
                List<GoodDetailResponseBean.BannerItem> bannerItemList = goodDetail.getBanner();
                for(GoodDetailResponseBean.BannerItem item: bannerItemList){
                    mBannerImages.add(item.getValue());
                }
                final String title = goodDetail.getTitle();
                final double price = goodDetail.getPrice();
                final String detailHtml = goodDetail.getContent();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadDataWithBaseURL("http://39.106.49.139/",detailHtml,"text/html", "UTF -8", null);
                        mTitielTextView.setText(title);
                        mPriceTextView.setText("¥"+price);
                        mConvenientBanner.setPages(new CBViewHolderCreator() {
                            @Override
                            public Object createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, mBannerImages)
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
                    }
                });
            }
        });





        //mWebView.loadUrl("http://www.jianshu.com/p/3c94ae673e2a");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_buy_now:
                showDialog();
                break;

            case R.id.iv_close://关闭弹窗
                mBottomSheetDialog.dismiss();
                break;
            case R.id.btn_buy_input_message://下一步

                //输入订单信息
                //inputOrderMessage();
                break;
            case R.id.btn_shop_cut://减少
                if (shopNum > 1)
                    shopNum--;

                tvNum.setText(shopNum + "");
                break;
            case R.id.btn_shop_add://增加
                if (shopNum < 99)
                    shopNum++;
                tvNum.setText(shopNum + "");
                break;
        }

    }

    private void showDialog(){
        mBottomSheetDialog = new GoodSelectDialog(this);
        //设置退出速度
        mBottomSheetDialog.outDuration(100);
        mBottomSheetDialog.inDuration(100);
        //设置铺满
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        //解析视图
        View contentView = LayoutInflater.from(ClothDetailActivity.this)
                .inflate(R.layout.layout_by_shop, null);
        //设置视图
        mBottomSheetDialog.setContentView(contentView);
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        goInput = (Button) contentView.findViewById(R.id.btn_buy_input_message);
        //-------颜色、内存、是否分期 数量
        tvColor = (TextView) contentView.findViewById(R.id.tv_shop_color);
        tvMomey = (TextView) contentView.findViewById(R.id.tv_shop_momery);
        tvVersion = (TextView) contentView.findViewById(R.id.tv_shop_version);
        tvNum = (TextView) contentView.findViewById(R.id.tv_shop_num);
        tvPrice = (TextView) contentView.findViewById(R.id.tv_shop_price);
        tvShopName = (TextView) contentView.findViewById(R.id.tv_shop_name);
        ivShopPhoto = (ImageView) contentView.findViewById(R.id.iv_shop_photo);
        llBuyStages = (LinearLayout) contentView.findViewById(R.id.ll_buy_stages);
        //-------颜色、内存、是否分期 数量
        //------------------增加 减少
        btnCut = (Button) contentView.findViewById(R.id.btn_shop_cut);
        btnAdd = (Button) contentView.findViewById(R.id.btn_shop_add);
        shopNum = 1;
        //------------------增加 减少
        ivClose.setOnClickListener(this);
        goInput.setOnClickListener(this);

        //--------------------------商品颜色
        rlShopColor = (FlowTagLayout) contentView.findViewById(R.id.rl_shop_color);
        initShopColor();
        //--------------------------商品颜色
        //--------------------------内存列表
        rlShopMomery = (FlowTagLayout) contentView.findViewById(R.id.rl_shop_momery);
        //initShopMomery();
        //--------------------------内存列表

        //-------------------------制式版本
        rlShopVersion = (FlowTagLayout) contentView.findViewById(R.id.rl_shop_version);
        // initShopVersion();
        //-------------------------制式版本
        btnCut.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        //------------名字 价格
        //tvShopName.setText(responseDto.getMsg().getParent().getName());
        tvShopName.setText("超级时尚连衣裙");
        //tvPrice.setText("￥" + responseDto.getPricemin());
        tvPrice.setText("￥"+899);
        mBottomSheetDialog.show();
        ivShopPhoto.setImageResource(R.mipmap.test1);
    }
    /**
     * 初始化颜色
     *
     * @hint
     */
    private void initShopColor() {
        mColors = new ArrayList<>();
        mColors.add(new TagInfo("红色"));
        mColors.add(new TagInfo("奶白色"));
        mColors.add(new TagInfo("黑色"));
        mColors.add(new TagInfo("紫色色"));
        mColors.add(new TagInfo("葱绿色"));
        mColors.add(new TagInfo("红色"));
        for (TagInfo mColor : mColors) {
            //初始化所有的选项为未选择状态
            mColor.setSelect(false);
        }
        tvColor.setText("\"未选择颜色\"");
        mColors.get(colorPositon).setSelect(true);
        ProperyTagAdapter colorAdapter = new ProperyTagAdapter(getApplicationContext(), mColors);
        rlShopColor.setAdapter(colorAdapter);
        colorAdapter.notifyDataSetChanged();
        rlShopColor.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        rlShopColor.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                colorPositon = selectedList.get(0);
                strColor = mColors.get(colorPositon).getText();
                // L.e("选中颜色：" + strColor);
                tvColor.setText("\"" + strColor + "\"");
            }
        });
    }


    private class LocalImageHolderView implements Holder<String>{
        private SimpleDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleDraweeView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageURI(data);
        }
    }

    public static void startWithId(Context context, int id){
        Intent intent =new Intent(context,ClothDetailActivity.class);
        intent.putExtra(KEY_PRAMS_GOOD_ID, id);
        context.startActivity(intent);
    }
}
