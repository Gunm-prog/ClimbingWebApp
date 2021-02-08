package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="User")
public class User {


    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(unique=true, name="email")
    private String email;
    @Column(name="pseudo")
    private String pseudo;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;


    @OneToMany(targetEntity=TopoBooking.class, mappedBy="user")
    private List<TopoBooking> TopoBookings=new ArrayList<>();

    @OneToMany(targetEntity=Comment.class, mappedBy="user")
    private Set<Comment> comment;

    @OneToMany(targetEntity=Spot.class, mappedBy="user")
    private List<Spot> spot=new ArrayList<>();

    @OneToMany(targetEntity=Topo.class, mappedBy="user")
    private List<Topo> topo=new ArrayList<>();


    public User() {
        super();
    }


    public User(String name, String email, String pseudo, String password) {
    }

    public List<TopoBooking> getTopoBookings() {
        return TopoBookings;
    }

    public void setTopoBookings(List<TopoBooking> reservationTopos) {
        this.TopoBookings=TopoBookings;
    }

    public void setTopo(List<Topo> topo) {
        this.topo=topo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo=pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public List<Spot> getSpot() {
        return spot;
    }

    public void setSpot(List<Spot> spot) {
        this.spot=spot;
    }

    public Set<Comment> getCommentaire() {
        return comment;
    }

    public void setCommentaire(Set<Comment> comment) {
        this.comment=comment;
    }

    public List<Topo> getTopo() {
        return topo;
    }

    public void setTopos(List<Topo> topo) {
        this.topo=topo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role=role;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

