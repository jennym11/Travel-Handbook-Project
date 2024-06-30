package com.hcl.userservice.resources;

import com.hcl.userservice.models.User;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private MongoCollection<Document> mongo;

    @RequestMapping("/register")
    public boolean registerUser(@RequestBody User user) {
        try {
            return mongo.insertOne(user.toDocument()).wasAcknowledged();
        } catch(MongoWriteException m) {
            return false;
        }
    }

    @RequestMapping("/authenticate")
    public boolean authenticateUser(@RequestBody User user) {
        Document doc = getDocument(user.getName());
        if(doc != null) {
            return user.equals(new User(doc));
        }
        return false;
    }

    @RequestMapping("/profile/{name}")
    public User getUser(@PathVariable String name) {
        Document doc = getDocument(name);
        return (doc == null) ? null : new User(doc);
    }

    @RequestMapping("/update/{name}")
    public boolean updateUser(@PathVariable String name, @RequestBody User user) {
        Document doc = getDocument(name);
        Bson updates = Updates.combine(
                Updates.set("name", user.getName()),
                Updates.set("password", user.getPassword()),
                Updates.set("email", user.getEmail()),
                Updates.set("admin", user.isAdmin()),
                Updates.set("destination", user.getDestination()),
                Updates.set("reviews", user.getReviews()),
                Updates.set("recommendations", user.getRecommendations())
        );
        if(doc == null) {
            return false;
        }
        return mongo.updateOne(doc, updates).wasAcknowledged();
    }

    @RequestMapping("/delete/{name}")
    public boolean deleteUser(@PathVariable String name) {
        return mongo.deleteOne(getDocument(name)).wasAcknowledged();
    }

    @RequestMapping("/all")
    public List<User> getUsers() {
        List<User> users = List.of();
        mongo.find().forEach(user -> users.add(new User(user)));
        return users;
    }

    private Document getDocument(String name) {
        return mongo.find(new Document().append("name", name)).first();
    }
}
