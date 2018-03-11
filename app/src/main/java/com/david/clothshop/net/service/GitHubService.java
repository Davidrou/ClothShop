package com.david.clothshop.net.service;

import com.david.clothshop.net.bean.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by luxiaolin on 17/12/11.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> listRepos(@Path("user") String user);
}