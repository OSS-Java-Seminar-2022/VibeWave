package com.projectvibewave.vibewaveapp.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User implements UserDetails {
    private final static String ROLE_PREFIX = "ROLE_";
    private final static String DEFAULT_ROLE = "BASIC";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private LocalDateTime createdOn = LocalDateTime.now();
    private String artistName;
    private boolean isPrivate = false;
    private String profilePhotoUrl;
    private boolean isVerified = false;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    private boolean isEnabled;

    public User(String username,
                String password,
                String email,
                LocalDateTime createdOn,
                String artistName,
                boolean isPrivate,
                String profilePhotoUrl,
                boolean isVerified,
                Set<Role> roles,
                boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdOn = createdOn;
        this.artistName = artistName;
        this.isPrivate = isPrivate;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isVerified = isVerified;
        this.roles = roles;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + DEFAULT_ROLE));
        for(Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
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
        return isEnabled;
    }
}