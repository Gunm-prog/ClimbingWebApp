package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;

@Entity
@Table(name="pitch")
public class Pitch {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="distance")
    private int distance;
    @Column(name="points")
    private String points;
    @Column(name="quotation")
    private String quotation;

    @ManyToOne(targetEntity=Route.class)
    @JoinColumn(name="route_id", referencedColumnName="id")
    private Route route;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;


    public Pitch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance=distance;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points=points;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation=quotation;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route=route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }


}
