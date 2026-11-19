
package com.example.taskmanager.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
    private UserProfile profile;
    
    public User() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

     public UserProfile getProfile() {
    return profile;
    }

    public void setProfile(UserProfile profile) {
    this.profile = profile;
    if (profile != null) {
        profile.setUser(this);
     }
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
}
