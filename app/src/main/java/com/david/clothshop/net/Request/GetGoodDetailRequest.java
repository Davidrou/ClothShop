package com.david.clothshop.net.Request;

import com.david.clothshop.net.Config;
import com.david.clothshop.net.bean.GetGoodDetailRequestBean;
import com.david.clothshop.net.bean.GetGoodListInHomeRequestBean;
import com.david.clothshop.net.bean.GoodDetailResponseBean;
import com.david.clothshop.net.bean.GoodListInHome;
import com.david.clothshop.net.bean.RequestData;
import com.david.clothshop.net.bean.ResponseData;
import com.david.clothshop.net.service.GoodDetailRequestService;
import com.david.clothshop.net.service.HomeListRequestService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luxiaolin on 18/3/18.
 */

public class GetGoodDetailRequest {
    public static ResponseData<GoodDetailResponseBean> request(int goodId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_ADDRESS_BASE_LOCAL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoodDetailRequestService requestService = retrofit.create(GoodDetailRequestService.class);
        GetGoodDetailRequestBean requestBean = new GetGoodDetailRequestBean(goodId);
        RequestData<GetGoodDetailRequestBean> requestData = new RequestData<>(requestBean);
        Call<ResponseData<GoodDetailResponseBean>> call = requestService.getGoodDetail(requestData);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
