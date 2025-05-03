package com.example.tpretrofit.repository;

import androidx.lifecycle.MutableLiveData;


import com.example.tpretrofit.model.User;
import com.example.tpretrofit.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    public void fetchUsers(final MutableLiveData<List<User>> userListLiveData, final MutableLiveData<Boolean> isLoading) {
        RetrofitClient.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userListLiveData.postValue(response.body());
                } else {
                    userListLiveData.postValue(null);
                }
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userListLiveData.postValue(null);
                isLoading.postValue(false);
            }
        });
    }
}
