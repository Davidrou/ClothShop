package com.david.clothshop.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luxiaolin on 18/3/18.
 */

public class GetGoodDetailRequestBean {
    @SerializedName("id")
    public int goodId;

    public GetGoodDetailRequestBean(int id){
        this.goodId = id;
    }

}
