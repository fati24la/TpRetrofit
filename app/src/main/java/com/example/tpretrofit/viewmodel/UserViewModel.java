package com.example.tpretrofit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.tpretrofit.model.User;
import com.example.tpretrofit.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private MutableLiveData<Boolean> isLoading;
    private UserRepository repository;

    public UserViewModel() {
        repository = new UserRepository();
        users = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        loadUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void loadUsers() {
        isLoading.setValue(true);
        repository.fetchUsers(users, isLoading);
    }

    public void refreshUsers() {
        loadUsers();
    }
}
