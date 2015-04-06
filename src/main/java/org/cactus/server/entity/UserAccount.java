package org.cactus.server.entity;

import org.cactus.share.enums.UserAccountRoleEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserAccountRoleEnum role;

    @ElementCollection
    @Embedded
    @Column(name = "contacts", nullable = true)
    private Set<UserAccount> contacts = new HashSet<UserAccount>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public UserAccountRoleEnum getRole() {
        return role;
    }

    public void setRole(UserAccountRoleEnum role) {
        this.role = role;
    }

    public Set<UserAccount> getContacts() {
        return contacts;
    }

    public void setContacts(Set<UserAccount> contacts) {
        this.contacts = contacts;
    }
}
