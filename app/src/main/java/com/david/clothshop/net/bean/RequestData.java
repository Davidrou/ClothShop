package com.david.clothshop.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luxiaolin on 18/3/11.
 */

public class RequestData<T> {
    @SerializedName("data")
    public T data;

    public RequestData(T data){
        this.data = data;
    }
}
