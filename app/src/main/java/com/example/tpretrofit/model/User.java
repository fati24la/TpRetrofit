package com.example.tpretrofit.model;


public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Address address; // L'API JSONPlaceholder utilise un objet address contenant city

    public int getId() {return id; }
    public String getName() {return name; }
    public String getEmail() {return email; }
    public String getPhone() {return phone; }

    // Getter pour city extrait de l'objet address
    public String getCity() {
        return (address != null) ? address.getCity() : "";
    }

    // Classe imbriquée pour l'adresse
    public static class Address {
        private String city;

        public String getCity() {return city; }
    }
}
