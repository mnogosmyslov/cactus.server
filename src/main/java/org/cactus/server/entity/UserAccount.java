package org.cactus.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinTable(name = "useraccount_contacts",
            joinColumns = @JoinColumn(name = "useraccount_id"))
    @Column(name = "contacts", nullable = true)
    private Set<Long> contacts = new HashSet<Long>(0);

    public UserAccount() { }

    public UserAccount(String email, String password, String login, String name, Set<Long> contacts, String photo) {
        this.email = email;
        this.password = password;
        this.login = login;
        this.name = name;
        this.contacts = contacts;
        this.photo = photo;
    }

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

    @JsonIgnore
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

    @JsonIgnore
    public UserAccountRoleEnum getRole() {
        return role;
    }

    public void setRole(UserAccountRoleEnum role) {
        this.role = role;
    }

    @JsonIgnore //TODO: if get contacts: HTTP Status 500 - failed to lazily initialize a collection of role: org.cactus.server.entity.UserAccount.contacts, could not initialize proxy - no Session (through reference chain: org.cactus.server.entity.UserAccount["contacts"])
    public Set<Long> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Long> contacts) {
        this.contacts = contacts;
    }
}
