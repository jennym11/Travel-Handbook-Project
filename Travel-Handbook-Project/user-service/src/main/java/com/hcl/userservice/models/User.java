package com.hcl.userservice.models;

import org.bson.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class User {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private boolean admin;
    private String destination;
    private List<String> reviews;
    private List<String> recommendations;

    //Empty Constructor
    public User() {
    }

    //Constructor to be used for registration
    public User(String name, String password, String email, boolean admin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.destination = "";
        this.reviews = List.of();
        this.recommendations = List.of();
    }

    //Constructor for results from db fetches.
    public User(Document document) {
        this.name = (document.containsKey("name")) ? document.getString("name") : null;
        this.password = (document.containsKey("password")) ? document.getString("password") : null;
        this.email = (document.containsKey("email")) ? document.getString("email") : null;
        this.admin = (document.containsKey("admin")) ? document.getBoolean("admin") : false;
        this.destination = (document.containsKey("destination")) ? document.getString("destination") : null;
        this.reviews = (document.containsKey("reviews")) ? document.getList("reviews", String.class) : List.of();
        this.recommendations = (document.containsKey("recommendations")) ? document.getList("recommendations", String.class) : List.of();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email);
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Password: %s, Email: %s, Admin: %b, Destination: %s\nReviews: %s\nRecommendations: %s", name, password, email, admin, destination, reviews.toString(), recommendations.toString());
    }

    public Document toDocument() {
        return new Document()
                .append("name", this.name)
                .append("password", this.password)
                .append("email", this.email)
                .append("admin", this.admin)
                .append("destination", this.destination)
                .append("reviews", this.reviews)
                .append("recommendations", this.recommendations);
    }
}
