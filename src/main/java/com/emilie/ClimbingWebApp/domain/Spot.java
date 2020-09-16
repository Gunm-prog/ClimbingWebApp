package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name="spot")//spécification nom table forcé
public class Spot implements Serializable {
    //private Scanner sc = new Scanner (System.in);
    @Id
    //norme
    @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
    @Column (name = "id")
    private Long id;
    @Column (name="Nom_spot")
    private String name;
    @Column(name="description_spot")
    private String description;

  /*  @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name= "user_id")//, nullable=false)
    private User user;*/

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id"/*, insertable=false, updatable=false*/)
    private User user;

    @OneToMany(targetEntity=Secteur.class, mappedBy="spot", fetch=FetchType.EAGER)
    private List<Secteur> secteurs = new ArrayList<>();

   @ManyToOne(targetEntity=Topo.class)
   @JoinColumn(name="topo_id", referencedColumnName="id")
   private Topo topo;

    @OneToMany(targetEntity=Commentaire.class, mappedBy="spot")
    private List<Commentaire> commentaires = new ArrayList<>();
    /*@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name= "Commentaire")
    private Commentaire commentaire;
    private List<Commentaire> commentaires;*/

   /* @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="commentaire_id")
    private Commentaire commentaire;*/


    public Spot(Scanner sc) {
        this.scanName(sc);
        this.scanDescription(sc);
    }


    public Spot() {

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

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires=commentaires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }
    /*public User getUser (){ return user;}
    public void setUser(User user){ this.user=user;}*/

  /*  public Commentaire getCommentaire(){ return commentaire;}
    public void setCommentaire(Commentaire commentaire){this.commentaire=commentaire;}*/
    public Secteur getSecteur(){return (Secteur) secteurs;}

    public void setSecteur(Secteur secteur) {this.secteurs=(List<Secteur>) secteur;}

    public Topo getTopo(){return topo;}

    public void setTopos(Topo topo){this.topo=topo; }

    public void scanName(Scanner sc){
        System.out.println ("spotName: ");
        String inputName = sc.nextLine();
        this.setName(inputName );
    }
    public void scanDescription(Scanner sc){
        System.out.println("spotDescription: ");
        String inputDescription = sc.nextLine();
        this.setDescription(inputDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot=(Spot) o;
        return Objects.equals( id, spot.id ) &&
                Objects.equals( name, spot.name ) &&
                Objects.equals( description, spot.description ) &&
                Objects.equals( user, spot.user ) &&
                Objects.equals( secteurs, spot.secteurs ) &&
                Objects.equals( topo, spot.topo ) &&
                Objects.equals( commentaires, spot.commentaires );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, description, user, secteurs, topo, commentaires );
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", secteurs=" + secteurs +
                ", topos=" + topo +
                ", commentaires=" + commentaires +
                '}';
    }
}