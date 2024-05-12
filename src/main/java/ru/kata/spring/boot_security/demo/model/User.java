package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "username")
private String username;

@Column(name = "lastname")
private String lastname;

@Column(name = "age")
private Byte age;

@Email
@Column(name = "email")
private String email;
@Column(name = "password")
private String password;

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
private List<Role> roles = new ArrayList<>();

public User() {
}

public User(String username, String lastname, Byte age, String email, String password, List<Role> roles) {
    this.username = username;
    this.lastname = lastname;
    this.age = age;
    this.email = email;
    this.password = password;
    this.roles = roles;
}
@Override
public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            '}';
}
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
}

@Override
public boolean isAccountNonExpired() {
    return true;
}

@Override
public boolean isAccountNonLocked() {
    return true;
}

@Override
public boolean isCredentialsNonExpired() {
    return true;
}

@Override
public boolean isEnabled() {
    return true;
}

}
