package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Entity
@Table(name="secteur")//spécification nom table forcé
public class Area {

    @Id
    //norme
    @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
    @Column(name="id")
    private Long id;
    @Column(name="nom_secteur")
    private String name;
    @Column(name="description")
    private String description;

    @ManyToOne(targetEntity=Spot.class)
    @JoinColumn(name="spot_id", referencedColumnName="id"/*, insertable=false, updatable=false*/)
    private Spot spot;

    @OneToMany(targetEntity=Route.class, mappedBy="area")
    private List<Route> route=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;


    public Area(Scanner sc, String description) {
        this.description=description;
        this.scanName( sc );
    }

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

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot=spot;
    }

    public List<Route> getVoie() {
        return route;
    }

    public void setVoie(List<Route> route) {
        this.route=route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void scanName(Scanner sc) {
        System.out.println( "secteurName: " );
        String inputName=sc.nextLine();
        this.setName( inputName );
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
                '}';
    }
}

