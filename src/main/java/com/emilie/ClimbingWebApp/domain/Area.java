package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="area")
public class Area {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="area_name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="number_of_routes")
    private String numberOfRoutes;

    @ManyToOne(targetEntity=Spot.class)
    @JoinColumn(name="spot_id", referencedColumnName="id")
    private Spot spot;

    @OneToMany(targetEntity=Route.class, mappedBy="area")
    private List<Route> route=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;


    public Area() {

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

    public String getNumberOfRoutes() {
        return numberOfRoutes;
    }

    public void setNumberOfRoutes(String numberOfRoutes) {
        this.numberOfRoutes=numberOfRoutes;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot=spot;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route=route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }


    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numberOfRoutes='" + numberOfRoutes + '\'' +
                '}';
    }
}

