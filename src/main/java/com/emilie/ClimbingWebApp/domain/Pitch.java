package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.Objects;
import java.util.Scanner;

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
    @JoinColumn(name="voie_id", referencedColumnName="id")
    private Route route;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;


    public Pitch(Scanner sc) {
        this.scanId( sc );
    }


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

    private void scanId(Scanner sc) {
        System.out.println( "PitchId: " );
        Long inputId=Long.valueOf( sc.nextLine() );
        this.setId( inputId );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pitch pitch=(Pitch) o;
        return distance == pitch.distance &&
                Objects.equals( id, pitch.id ) &&
                Objects.equals( points, pitch.points ) &&
                Objects.equals( quotation, pitch.quotation ) &&
                Objects.equals( route, pitch.route ) &&
                Objects.equals( user, pitch.user );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, distance, points, quotation, route, user );
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "id=" + id +
                ", distance=" + distance +
                ", points='" + points + '\'' +
                ", quotation='" + quotation + '\'' +
                ", route=" + route +
                '}';
    }

}
