package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.Scanner;


@Entity
@Table(name="commentaire")
public class Commentaire {

    @Id//norme
    @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="content")
    private String content;
    @Column(name="date")
    private String date;

    @ManyToOne(targetEntity=User.class, fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @ManyToOne(targetEntity=Spot.class)
    @JoinColumn(name="spot_id")
    private Spot spot;

    public Commentaire(Scanner sc, User user, Spot spot) {
        this.user=user;//TODO à vérifier cet ajout de constructeur
        this.spot=spot;
        this.scanName( sc );
        this.scanContent( sc );
    }

    public Commentaire() {

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot=spot;
    }


    public void scanName(Scanner sc) {
        System.out.println( "Title: " );
        String inputName=sc.nextLine();
        this.setName( inputName );
    }

    public void scanContent(Scanner sc) {
        System.out.println( "Content: " );
        String inputContent=sc.nextLine();
        this.setContent( inputContent );
    }

    public void scanDate(Scanner sc) {
        System.out.println( "Date: " );
        String inputDate=sc.nextLine();
        this.setDate( inputDate );
    }


    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", user=" + user +
                '}';
    }
}



