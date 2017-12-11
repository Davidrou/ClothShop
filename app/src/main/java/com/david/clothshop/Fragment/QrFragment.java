package com.david.clothshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.david.clothshop.R;
import com.david.clothshop.activity.LoginActivity;
import com.david.clothshop.common.BaseFragment;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class QrFragment extends BaseFragment {

    @Override
    protected int setFragmentViewId() {
        return R.layout.fragment_qr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivity.start(getContext());
    }
}
