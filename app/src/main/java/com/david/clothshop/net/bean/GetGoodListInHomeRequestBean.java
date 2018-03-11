package com.david.clothshop.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luxiaolin on 18/3/11.
 */

public class GetGoodListInHomeRequestBean {
    @SerializedName("pageNum")
    public int pageNum;

    public GetGoodListInHomeRequestBean(int pageNum){
        this.pageNum = pageNum;
    }
}
