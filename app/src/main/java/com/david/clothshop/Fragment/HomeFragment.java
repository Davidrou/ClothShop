package com.david.clothshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.clothshop.R;
import com.david.clothshop.activity.ClothDetailActivity;
import com.david.clothshop.common.BaseFragment;
import com.david.clothshop.net.Request.GetListInHomeRequest;
import com.david.clothshop.net.bean.GetGoodListInHomeRequestBean;
import com.david.clothshop.net.bean.GoodListInHome;
import com.david.clothshop.net.bean.ResponseData;
import com.david.clothshop.utils.ThreadPool;
import com.facebook.drawee.view.SimpleDraweeView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class HomeFragment extends BaseFragment {
    private RecyclerView mRecycleView;
    private List<GoodListInHome.Good> mDatas = new ArrayList<>();
    private static final String TAG = "HomeFragment";
    HomeListAdapter mHomeListAdapter;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setFragmentViewId(), container, false);
        mHomeListAdapter = new HomeListAdapter();
        initData();
        mRecycleView = (RecyclerView) view.findViewById(R.id.list_home_fragment);
        mRecycleView.setLayoutManager(new GridLayoutManager(mActivity,2));
        mRecycleView.setAdapter(mHomeListAdapter);
        // 设置间隔样式
        mRecycleView.addItemDecoration(new MDGridRvDividerDecoration(getContext()));
        return view;
    }

    protected int setFragmentViewId() {
        return R.layout.fragment_home;
    }


    protected void initData() {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ResponseData<GoodListInHome> responseData = GetListInHomeRequest.request(1);
                if(responseData == null || !TextUtils.equals(responseData.getCode(), "OK")){
                    Log.e(TAG, "net work error");
                }
                List<GoodListInHome.Good> goodList = responseData.getData().getGoodList();
                mDatas.addAll(goodList);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomeListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    private class HomeListAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mActivity).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            GoodListInHome.Good good = mDatas.get(position);
            holder.imageView.setImageURI(good.getShow());
            holder.title.setText(good.getTitle());
            holder.price.setText(good.getPrice()+"");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClothDetailActivity.startActivity(getContext());
                }
            });
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    private class MyViewHolder extends ViewHolder {
        SimpleDraweeView imageView;
        TextView title;
        TextView price;

        public MyViewHolder(View view) {
            super(view);
            imageView = (SimpleDraweeView) view.findViewById(R.id.id_image_item_home_list);
            title = (TextView) view.findViewById(R.id.id_text_name);
            price = (TextView) view.findViewById(R.id.id_text_price);
        }
    }
}
