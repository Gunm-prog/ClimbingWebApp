package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name="longueur")//spécification nom table forcé
public class Longueur  {

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

    @ManyToOne(targetEntity=Voie.class)
    @JoinColumn(name="voie_id", referencedColumnName="id")
    private Voie voie;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;



    public Longueur(Scanner sc){this.scanId(sc);}


    public Longueur(){}

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

    public Voie getVoie() {
        return voie;
    }

    public void setVoie(Voie voie) {
        this.voie=voie;
    }

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

  private void scanId(Scanner sc){
        System.out.println("longueurId: ");
        Long inputId=Long.valueOf( sc.nextLine() );
        this.setId(inputId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Longueur longueur=(Longueur) o;
        return distance == longueur.distance &&
                Objects.equals( id, longueur.id ) &&
                Objects.equals( points, longueur.points ) &&
                Objects.equals( quotation, longueur.quotation ) &&
                Objects.equals( voie, longueur.voie )&&
                Objects.equals( user, longueur.user );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, distance, points, quotation, voie, user);
    }

    @Override
    public String toString() {
        return "Longueur{" +
                "id=" + id +
                ", distance=" + distance +
                ", points='" + points + '\'' +
                ", quotation='" + quotation + '\'' +
                ", voie=" + voie +
                '}';
    }
}
