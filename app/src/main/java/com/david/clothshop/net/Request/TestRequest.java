package com.david.clothshop.net.Request;

import com.david.clothshop.net.service.GitHubService;
import com.david.clothshop.net.bean.GitHubRepo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luxiaolin on 17/12/11.
 */

public class TestRequest {
    public static List<GitHubRepo> test(String user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<GitHubRepo>> repos =  service.listRepos(user);
        List<GitHubRepo> result = null;
        try {
             result= repos.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
