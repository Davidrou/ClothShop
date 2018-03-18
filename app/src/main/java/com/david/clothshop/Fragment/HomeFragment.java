package com.david.clothshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshContainer;
    private List<GoodListInHome.Good> mDatas = new ArrayList<>();
    private static final String TAG = "HomeFragment";
    HomeListAdapter mHomeListAdapter;
    private int mPageNum = 1;
    private boolean mHasNext = false;
    public int loadState;
    int STATE_LOADING = 1;
    int STATE_LASTED = 2;
    int STATE_ERROR = 3;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setFragmentViewId(), container, false);
        mHomeListAdapter = new HomeListAdapter();
        initData();
        mRecycleView = (RecyclerView) view.findViewById(R.id.list_home_fragment);
        mSwipeRefreshContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_home_fragment);
        mSwipeRefreshContainer.setOnRefreshListener(this);
        mRecycleView.setLayoutManager(new GridLayoutManager(mActivity,2));
        mRecycleView.setAdapter(mHomeListAdapter);
        // 设置间隔样式
        mRecycleView.addItemDecoration(new MDGridRvDividerDecoration(getContext()));
        mRecycleView.addOnScrollListener(new LoadMoreScrollListener(mRecycleView));
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
                    return;
                }
                List<GoodListInHome.Good> goodList = responseData.getData().getGoodList();
                mDatas.clear();
                mDatas.addAll(goodList);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomeListAdapter.notifyDataSetChanged();
                        mSwipeRefreshContainer.setRefreshing(false);
                    }
                });
                mHasNext = responseData.getData().isHasNext();
                mPageNum = 1;
            }
        });
    }

    private void loadMoreData(){
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "loadMoreData");
                loadState = STATE_LOADING;
                ResponseData<GoodListInHome> responseData = GetListInHomeRequest.request(mPageNum+1);
                if(responseData == null || !TextUtils.equals(responseData.getCode(), "OK")){
                    Log.e(TAG, "net work error");
                    loadState = STATE_ERROR;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHomeListAdapter.notifyDataSetChanged();
                        }
                    });
                    return;
                }
                List<GoodListInHome.Good> goodList = responseData.getData().getGoodList();
                if(goodList==null && goodList.isEmpty()){
                    Log.e(TAG, "good list is empty");
                    return;
                }
                mDatas.addAll(goodList);
                ++mPageNum;
                mHasNext = responseData.getData().isHasNext();
                if(!mHasNext){
                    loadState = STATE_LASTED;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomeListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        initData();
    }


    private class HomeListAdapter extends RecyclerView.Adapter<MyViewHolder> {
        public static final int TYPE_OTHER = 1;
        public static final int TYPE_BOTTOM = 2;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (TYPE_BOTTOM == viewType) {
                //返回我们的那个加载中的布局Viewholder
                return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_footer, parent, false));
            } else {
                return new MyViewHolder(LayoutInflater.from(
                        mActivity).inflate(R.layout.item_home, parent,
                        false));
            }
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_BOTTOM) {
                //对相应的onBindViewHolder进行处理
                LinearLayout container = ((FooterViewHolder) holder).container;
                final ProgressBar pb = ((FooterViewHolder) holder).pb;
                final TextView content = ((FooterViewHolder) holder).content;
                if(loadState == STATE_LASTED){
                    pb.setVisibility(View.GONE);
                    content.setText("-----到底了----");
                    container.setOnClickListener(null);
                }else if (loadState == STATE_ERROR){
                    pb.setVisibility(View.GONE);
                    content.setText("--- 加载更多失败点击重试 ---");
                    container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadMoreData();
                            content.setText("加载中");
                            pb.setVisibility(View.VISIBLE);
                        }
                    });
                }else {
                    content.setText("加载中");
                    pb.setVisibility(View.VISIBLE);
                    container.setOnClickListener(null);
                }

            }else {
                final GoodListInHome.Good good = mDatas.get(position);
                holder.imageView.setImageURI(good.getShow());
                holder.title.setText(good.getTitle());
                holder.price.setText(good.getPrice() + "");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClothDetailActivity.startWithId(getContext(), good.getId());
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return mDatas.size() < 10 ? mDatas.size() : mDatas.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (!mDatas.isEmpty() && mDatas.size() > position) {
                return TYPE_OTHER;
            } else {
                return TYPE_BOTTOM;
            }
        }

        public boolean isHasMore() {
            return mHasNext;
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

    private class FooterViewHolder extends MyViewHolder {
        LinearLayout container;
        ProgressBar pb;
        TextView content;

        public FooterViewHolder(View view) {
            super(view);
            container = (LinearLayout) view;
            pb = (ProgressBar) view.findViewById(R.id.progress);
            content = (TextView) view.findViewById(R.id.bottom_title);
        }
    }

    class LoadMoreScrollListener extends RecyclerView.OnScrollListener{
        private RecyclerView mRecyclerView;

        public LoadMoreScrollListener(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            HomeListAdapter adapter = (HomeListAdapter) mRecyclerView.getAdapter();

            if (null == manager) {
                throw new RuntimeException("you should call setLayoutManager() first!!");
            }
            if (manager instanceof GridLayoutManager) {
                int lastCompletelyVisibleItemPosition = ((GridLayoutManager) manager).findLastCompletelyVisibleItemPosition();
                Log.d("lzw","lastViP"+lastCompletelyVisibleItemPosition);
                if (adapter.getItemCount() > 10 &&
                        lastCompletelyVisibleItemPosition == adapter.getItemCount() - 1 &&
                        adapter.isHasMore()) {
                    loadMoreData();
                }
            }
        }
    }
}
