package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.Objects;
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

    @ManyToOne
    @JoinColumn(name="secteur_id")
    private Secteur secteur;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;

    public Voie(Scanner sc){this.scanName(sc);}

    public Voie(){

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

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

    private void scanName(Scanner sc) {
        System.out.println("voieName: ");
        String inputName= sc.nextLine();
        this.setName( inputName );
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voie voie=(Voie) o;
        return hauteur == voie.hauteur &&
                nombre == voie.nombre &&
                Objects.equals( id, voie.id ) &&
                Objects.equals( name, voie.name ) &&
                Objects.equals( secteur, voie.secteur )&&
                Objects.equals( user, voie.user );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, hauteur, nombre, secteur, user );
    }

    @Override
    public String toString() {
        return "Voie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hauteur=" + hauteur +
                ", nombre=" + nombre +
                ", secteur=" + secteur +
                '}';
    }
}



