package jm.sergius.crud_spring_boot.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private byte age;

    @Column
    private String profession;

    @Column (nullable = false, unique = true)
    private String username; // уникальное значение

    @Column (nullable = false, unique = true)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles;


    public User(long id, String first_name, String last_name, byte age, String profession, String username, String password, Set<Role> roles) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String first_name, String last_name, byte age, String profession, String username, String password, Set<Role> roles) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(long id, String first_name, String last_name, byte age, String profession, String username, String password, Role... roles) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.username = username;
        this.password = password;
        this.roles = Set.of(roles);
        for(Role role : roles){
            role.setUser(this);
        }
    }

    public User(String first_name, String last_name, byte age, String profession, String username, String password, Role... roles) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.username = username;
        this.password = password;
        this.roles = Set.of(roles);
        for(Role role : roles){
            role.setUser(this);
        }
    }

    public User(long id, String first_name, String last_name, byte age, String profession) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.roles = new HashSet<>();
    }

    public User(String first_name, String last_name, byte age, String profession) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.profession = profession;
        this.roles = new HashSet<>();
    }

    public User() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        for(Role role : roles) {
            role.setUser(this);
        }
    }

    public void addRole(Role role) {
        this.roles = Set.of(role);
        role.setUser(this);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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

