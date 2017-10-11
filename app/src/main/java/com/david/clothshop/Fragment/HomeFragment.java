package com.david.clothshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.clothshop.R;
import com.david.clothshop.activity.ClothDetailActivity;
import com.david.clothshop.common.BaseFragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class HomeFragment extends BaseFragment {
    private RecyclerView mRecycleView;
    private List<String> mDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setFragmentViewId(), container, false);
        initData();
        mRecycleView = (RecyclerView) view.findViewById(R.id.list_home_fragment);
        mRecycleView.setLayoutManager(new GridLayoutManager(mActivity,2));
        mRecycleView.setAdapter(new HomeListAdapter());
        // 设置间隔样式
        mRecycleView.addItemDecoration(new MDGridRvDividerDecoration(getContext()));
        return view;
    }

    protected int setFragmentViewId() {
        return R.layout.fragment_home;
    }


    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
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
            if(position %3 ==0) {
                holder.imageView.setImageResource(R.mipmap.test1);
            }else if (position %3 ==1){
                holder.imageView.setImageResource(R.drawable.test2);
            }else{
                holder.imageView.setImageResource(R.drawable.test3);
            }
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
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.id_image_item_home_list);
        }
    }
}
