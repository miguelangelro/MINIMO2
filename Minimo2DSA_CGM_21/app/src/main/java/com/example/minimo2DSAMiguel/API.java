package com.example.minimo2DSAMiguel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    @GET("users/{Username}")
    Call<User> userInfo (@Path("Username") String username);

    @GET("users/{Username}/repos")
    Call<List<Repos>> ReposInfo (@Path("Username") String username);
}
