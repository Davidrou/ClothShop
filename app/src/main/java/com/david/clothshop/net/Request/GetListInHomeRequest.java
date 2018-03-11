package com.david.clothshop.net.Request;

import com.david.clothshop.net.Config;
import com.david.clothshop.net.bean.GetGoodListInHomeRequestBean;
import com.david.clothshop.net.bean.GoodListInHome;
import com.david.clothshop.net.bean.RequestData;
import com.david.clothshop.net.bean.ResponseData;
import com.david.clothshop.net.service.HomeListRequestService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetListInHomeRequest {
 public static ResponseData<GoodListInHome> request(int pageNum){
     Retrofit retrofit = new Retrofit.Builder()
             .baseUrl(Config.SERVER_ADDRESS_BASE_LOCAL)
             .addConverterFactory(GsonConverterFactory.create())
             .build();
     HomeListRequestService homeListRequestService = retrofit.create(HomeListRequestService.class);
     GetGoodListInHomeRequestBean getGoodListInHomeRequestBean =new GetGoodListInHomeRequestBean(pageNum);
     RequestData<GetGoodListInHomeRequestBean> requestData = new RequestData<>(getGoodListInHomeRequestBean);
     Call<ResponseData<GoodListInHome>> goodListInHomeCall = homeListRequestService.getGoodList(requestData);
     try {
         return goodListInHomeCall.execute().body();
     } catch (IOException e) {
         e.printStackTrace();
     }
     return null;
 }

}
