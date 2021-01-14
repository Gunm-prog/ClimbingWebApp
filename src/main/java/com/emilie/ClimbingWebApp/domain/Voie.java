package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name="voie")
public class Voie {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="hauteur")
    private int hauteur;
    @Column(name="nombre")
    private int nombre;

    @ManyToOne(targetEntity=Secteur.class)
    @JoinColumn(name="secteur_id", referencedColumnName="id")
    private Secteur secteur;

    @OneToMany(targetEntity=Longueur.class, mappedBy="voie")
    private List<Longueur> voie=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;

    public Voie(Scanner sc) {
        this.scanName( sc );
    }

    public Voie() {

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

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur=secteur;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    private void scanName(Scanner sc) {
        System.out.println( "voieName: " );
        String inputName=sc.nextLine();
        this.setName( inputName );
    }


    @Override
    public String toString() {
        return "Voie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hauteur=" + hauteur +
                ", nombre=" + nombre +
                '}';
    }
}



