
package com.pruebatecnica.backend.Entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;
    @Basic
    @Column(nullable = false)
    String username;
    @Column(nullable = false)

    String firstname;
    String lastname;
    String ci;
    String password;
    String email;
    boolean removed = false;
    boolean active_session = false;
    Integer failed_attempts;
    boolean locked = false;
    LocalDateTime date_creation;
    LocalDateTime date_update;

    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
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

    public void generarEmail() {
        this.email = this.firstname.toLowerCase() + this.lastname.toLowerCase() + "@mail.com";
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public void agregarNumeroEmailDuplicado() {
        Pattern pattern = Pattern.compile("(.+)(\\d)$");
        Matcher matcher = pattern.matcher(this.email);
        if (matcher.find()) {
            int numero = Integer.parseInt(matcher.group(2)) + 1;
            this.email = matcher.group(1) + numero + "@mail.com";
        } else {
            this.email += "1@mail.com";
        }
    }
}
