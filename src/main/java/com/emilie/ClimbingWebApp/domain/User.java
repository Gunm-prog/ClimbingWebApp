package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="User")//spécification nom table forcé
public class User {


    @javax.persistence.Id//norme
    @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
    //  private static final long serialVersionUID=1L;
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(unique=true, name="email") //todo à vérifier
    private String email;
    @Column(name="pseudo")
    private String pseudo;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;


    @OneToMany(targetEntity=ReservationTopo.class, mappedBy="user")
    private List<ReservationTopo> reservationTopos=new ArrayList<>();

    @OneToMany(targetEntity=Comment.class, mappedBy="user")
    private Set<Comment> comment;

    @OneToMany(targetEntity=Spot.class, mappedBy="user")
    private List<Spot> spot=new ArrayList<>();

      /*  @OneToMany(targetEntity=Area.class, mappedBy="user")
        private List<Area> secteur= new ArrayList<>();

        @OneToMany(targetEntity=Route.class, mappedBy="user")
        private List<Route> voie = new ArrayList<>();

        @OneToMany(targetEntity=Longueur.class, mappedBy="user")
        private List<Longueur> longueur = new ArrayList<>();*/

    @OneToMany(targetEntity=Topo.class, mappedBy="user")
    private List<Topo> topo=new ArrayList<>();


    public User() {
        super();
    }

    public User(Scanner sc) {
        this.scanName( sc );
        this.scanEmail( sc );
        this.scanPseudo( sc );
        this.scanPassword( sc );
    }


    public User(String name, String email, String pseudo, String password) {
    }

    public List<ReservationTopo> getReservationTopos() {
        return reservationTopos;
    }

    public void setReservationTopos(List<ReservationTopo> reservationTopos) {
        this.reservationTopos=reservationTopos;
    }

    public void setTopo(List<Topo> topo) {
        this.topo=topo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo=pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public List<Spot> getSpot() {
        return spot;
    }

    public void setSpot(List<Spot> spot) {
        this.spot=spot;
    }

    public Set<Comment> getCommentaire() {
        return comment;
    }

    public void setCommentaire(Set<Comment> comment) {
        this.comment=comment;
    }

    public List<Topo> getTopo() {
        return topo;
    }

    public void setTopos(List<Topo> topo) {
        this.topo=topo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role=role;
    }

    public void scanName(Scanner sc) {
        System.out.println( "userName: " );
        String inputName=sc.nextLine();
        this.setName( inputName );
        System.out.println( "Vous avez saisi: " + inputName + "" );
    }

    public void scanEmail(Scanner sc) {
        System.out.println( "Email: " );
        String inputEmail=sc.nextLine();
        this.setEmail( inputEmail );
        System.out.println( "vous avez saisi: " + inputEmail + "email" );
    }

    public void scanPseudo(Scanner sc) {
        System.out.println( "Pseudo: " );
        String inputPseudo=sc.nextLine();
        this.setPseudo( inputPseudo );
    }

    public void scanPassword(Scanner sc) {
        System.out.println( "Password: " );
        String inputPassword=sc.nextLine();
        this.setPassword( inputPassword );
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

