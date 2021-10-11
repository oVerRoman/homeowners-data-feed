package com.simbirsoftintensiv.intensiv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.Set;

@Entity // ->поля класса имеют отображение в БД,

@Table(name = "users")
public class User extends AbstractBaseEntity/* implements UserDetails*/ {

    @Column(name = "phone")
    @NotNull
    private Long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Company company;

//    @Size(min = 2, message = "Не меньше 5 знаков")
//    private String password;

    @Transient // под этой анотации поле не имеет отображения в БД.
    private String passwordConfirm;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "user_id", "role" }, name = "uk_user_roles") })
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @JoinColumn(name = "user_id") // https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    public User() {
    }

    public User(Long phone, String email, String firstName, String secondName, String patronymic,/*String password,*/ Role role) {
        System.out.println("!!!!!!User->" + role);
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
//        this.password = password;
        this.roles = EnumSet.of(role);
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isEnabled() {
        return true;
    }

    public Set<Role> getRoles() {
        System.out.println("getRoles->" + roles);
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
