package com.david.clothshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.clothshop.R;
import com.david.clothshop.common.BaseFragment;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class ShoppingCartFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setFragmentViewId() {
        return R.layout.fragment_shopping_cart;
    }
}
