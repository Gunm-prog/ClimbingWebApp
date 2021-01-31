package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Entity
@Table(name="voie")
public class Route {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="hauteur")
    private int hauteur;
    @Column(name="nombre")
    private int nombre;

    @ManyToOne(targetEntity=Area.class)
    @JoinColumn(name="secteur_id", referencedColumnName="id")
    private Area area;

    @OneToMany(targetEntity=Pitch.class, mappedBy="route")
    private List<Pitch> pitches=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;

    public Route(Scanner sc) {
        this.scanName( sc );
    }

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

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur=hauteur;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre=nombre;
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

    private void scanName(Scanner sc) {
        System.out.println( "voieName: " );
        String inputName=sc.nextLine();
        this.setName( inputName );
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hauteur=" + hauteur +
                ", nombre=" + nombre +
                '}';
    }
}



