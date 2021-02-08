package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="route")
public class Route {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="height")
    private int height;
    @Column(name="number")
    private int number;

    @ManyToOne(targetEntity=Area.class)
    @JoinColumn(name="area_id", referencedColumnName="id")
    private Area area;

    @OneToMany(targetEntity=Pitch.class, mappedBy="route")
    private List<Pitch> pitches=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;


    public Route() {

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height=height;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number=number;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area=area;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public List<Pitch> getPitches() {
        return pitches;
    }

    public void setPitches(List<Pitch> pitches) {
        this.pitches=pitches;
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", number=" + number +
                '}';
    }
}



