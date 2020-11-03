package com.emilie.ClimbingWebApp.domain;




import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name="secteur")//spécification nom table forcé
public class Secteur  {

    @Id
    //norme
    @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
    @Column(name="id")
    private Long id;
    @Column(name="Nom_secteur")
    private String name;
    @Column(name="description")
    private String description;

    @ManyToOne(targetEntity=Spot.class)
    @JoinColumn(name="spot_id", referencedColumnName="id"/*, insertable=false, updatable=false*/)
    private Spot spot;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;

    public Secteur(Scanner sc, String description) {
        this.description=description;
        this.scanName(sc);
    }

    public Secteur() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public List<Spot> getSpot(){return (List<Spot>) spot;}
    public void setSpot(List<Spot> spot){this.spot=(Spot) spot;}

    public void scanName(Scanner sc){
        System.out.println ("secteurName: ");
        String inputName = sc.nextLine();
        this.setName(inputName );
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Secteur secteur=(Secteur) o;
        return Objects.equals( id, secteur.id ) &&
                Objects.equals( name, secteur.name ) &&
                Objects.equals( description, secteur.description ) &&
                Objects.equals( user, secteur.user )&&
                Objects.equals( spot, secteur.spot );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, description, user, spot );
    }

    @Override
    public String toString() {
        return "Secteur{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

