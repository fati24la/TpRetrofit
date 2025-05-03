package com.example.tpretrofit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tpretrofit.adapter.UserAdapter;
import com.example.tpretrofit.model.User;
import com.example.tpretrofit.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private UserViewModel viewModel;
    private ProgressBar progressBar;
    private TextView emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText searchEditText;
    private List<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialisation des vues
        progressBar = findViewById(R.id.progressBar);
        emptyView = findViewById(R.id.emptyView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        searchEditText = findViewById(R.id.searchEditText);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        // Configuration du RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialisation du ViewModel
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observer les changements de données
        viewModel.getUsers().observe(this, users -> {
            if (users != null) {
                allUsers = users;
                adapter.setUserList(users);
                updateEmptyView(users.isEmpty());
            } else {
                Toast.makeText(this, "Erreur lors du chargement des données", Toast.LENGTH_SHORT).show();
                updateEmptyView(true);
            }
        });

        // Observer l'état de chargement
        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            swipeRefreshLayout.setRefreshing(isLoading);
        });

        // Configuration du SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshUsers();
        });

        // Configuration du FAB pour rafraîchir
        fabRefresh.setOnClickListener(v -> {
            viewModel.refreshUsers();
        });

        // Configuration de la recherche
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterUsers(String query) {
        if (allUsers == null) return;

        List<User> filteredList = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase().trim();

        if (lowerCaseQuery.isEmpty()) {
            filteredList.addAll(allUsers);
        } else {
            for (User user : allUsers) {
                // Filtrer par nom, email, téléphone ou ville
                if (user.getName().toLowerCase().contains(lowerCaseQuery) ||
                        user.getEmail().toLowerCase().contains(lowerCaseQuery) ||
                        user.getPhone().toLowerCase().contains(lowerCaseQuery) ||
                        (user.getCity() != null && user.getCity().toLowerCase().contains(lowerCaseQuery))) {
                    filteredList.add(user);
                }
            }
        }

        adapter.setUserList(filteredList);
        updateEmptyView(filteredList.isEmpty());
    }

    private void updateEmptyView(boolean isEmpty) {
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}