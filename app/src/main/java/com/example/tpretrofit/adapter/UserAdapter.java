package com.example.tpretrofit.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpretrofit.R;
import com.example.tpretrofit.model.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private Random random = new Random();

    public void setUserList(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());

        // Générer une couleur aléatoire pour l'avatar
        int[] colors = {
                Color.parseColor("#1976D2"), // Bleu
                Color.parseColor("#388E3C"), // Vert
                Color.parseColor("#D32F2F"), // Rouge
                Color.parseColor("#7B1FA2"), // Violet
                Color.parseColor("#FFA000"), // Orange
                Color.parseColor("#00796B")  // Teal
        };
        holder.avatar.setBackgroundColor(colors[position % colors.length]);

        // Pour l'avatar, nous allons utiliser un TextDrawable (solution alternative)
        if (user.getName() != null && !user.getName().isEmpty()) {
            String firstLetter = String.valueOf(user.getName().charAt(0)).toUpperCase();

            // Au lieu d'essayer d'ajouter une vue dans l'ImageView, nous définissons simplement
            // un contentDescription qui sera utilisé pour l'accessibilité
            holder.avatar.setImageDrawable(null);
            holder.avatar.setContentDescription(firstLetter);

            // Note: Pour vraiment afficher la lettre, nous devrions utiliser un TextDrawable
            // ou créer un layout spécifique pour les avatars avec un TextView par-dessus
        }

        // Afficher ou masquer la ville selon disponibilité
        if (user.getCity() != null && !user.getCity().isEmpty()) {
            holder.city.setText(user.getCity());
            holder.cityContainer.setVisibility(View.VISIBLE);
        } else {
            holder.cityContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone, city;
        ShapeableImageView avatar;
        LinearLayout cityContainer;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            phone = itemView.findViewById(R.id.userPhone);
            city = itemView.findViewById(R.id.userCity);
            avatar = itemView.findViewById(R.id.userAvatar);
            cityContainer = itemView.findViewById(R.id.cityContainer);
        }
    }
}