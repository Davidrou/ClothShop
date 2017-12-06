package com.david.clothshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.david.clothshop.R;
import com.david.clothshop.adapter.CartAdapter;
import com.david.clothshop.bean.ShoppingCart;
import com.david.clothshop.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class ShoppingCartFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    private CheckBox mCheckBox;

    private TextView mTextTotal;

    private Button mBtnOrder;

    private Button mBtnDel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setFragmentViewId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void init() {
        super.init();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox_all);
        mBtnDel = (Button) findViewById(R.id.btn_del);
        mBtnOrder = (Button) findViewById(R.id.btn_order);
        mTextTotal = (TextView) findViewById(R.id.txt_total);

        if(mTextTotal == null){
            Log.e("LZW","text is null");
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCount(3);
        shoppingCart.setId(1L);
        shoppingCart.setIsChecked(false);
        shoppingCart.setName("时尚连衣裙");
        shoppingCart.setPrice(100F);
        shoppingCart.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512583305146&di=1730791210d2311556a68a35702ea673&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150628%2Fmp20454195_1435489370253_11.jpg");
        List<ShoppingCart> list = new ArrayList<>();
        list.add(shoppingCart);
        mRecyclerView.setAdapter(new CartAdapter(getContext(),list,mCheckBox,mTextTotal));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }







}
