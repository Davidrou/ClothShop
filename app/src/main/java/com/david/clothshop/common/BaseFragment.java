package com.david.clothshop.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luxiaolin on 17/7/16.
 */

public class BaseFragment extends Fragment {
    protected Activity mActivity;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setFragmentViewId(), container, false);
        mView = view;
        init();
        return view;
    }

    protected void init() {
    }

    protected int setFragmentViewId() {
        return 0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    protected View findViewById(int id){
        return mView.findViewById(id);
    }
}
