package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

    @Entity
    @Table(name="User")//spécification nom table forcé
    public class User implements Serializable {

        @javax.persistence.Id//norme
        @GeneratedValue(strategy=GenerationType.IDENTITY)//se charge de mettre à jour l'id dans bdd
        @Column (name="id")
        private Long Id;
        @Column (name="name")
        private String name;
        @Column (name="email")
        private String email;
        @Column (name="pseudo")
        private String pseudo;
        @Column (name="password")
        private String password;

        @OneToMany(targetEntity=Commentaire.class, mappedBy="user", fetch=FetchType.EAGER)
        private Set<Commentaire> commentaire;

        @OneToMany(targetEntity=Spot.class, mappedBy="user")
        private List<Spot> spot = new ArrayList<>();


        public User(){
            super();
        }

        public User(Scanner sc){
            this.scanName(sc);
            this.scanEmail(sc);
            this.scanPseudo(sc);
            this.scanPassword(sc);
        }

        public User(String name, String email, String pseudo, String password) {
        }

        public Long getId(){return Id;}
        public void setId(Long id){this.Id=Id;}

        public String getName(){return name;}
        public void setName(String name){this.name=name; }

        public String getEmail(){return email;}
        public void setEmail(String email){this.email=email;}

        public String getPseudo(){return pseudo;}
        public void setPseudo(String pseudo){this.pseudo=pseudo;}

        public String getPassword(){return password;}
        public void setPassword(String password){this.password=password;}

        public List<Spot> getSpot(){return spot;}
        public void setSpot(List<Spot> spot){this.spot=spot;}

        public Set<Commentaire> getCommentaire(){return commentaire;}
        public void setCommentaire(Set<Commentaire> commentaire){this.commentaire=commentaire;}


        public void scanName(Scanner sc){
            System.out.println("userName: ");
            String inputName = sc.nextLine();
            this.setName(inputName);
            System.out.println("Vous avez saisi: " + inputName + "");
        }

        public void scanEmail(Scanner sc){
            System.out.println("Email: ");
            String inputEmail = sc.nextLine();
            this.setEmail( inputEmail );
            System.out.println("vous avez saisi: " + inputEmail + "email");
        }

        public void scanPseudo(Scanner sc){
            System.out.println("Pseudo: ");
            String inputPseudo = sc.nextLine();
            this.setPseudo (inputPseudo);
        }

        public void scanPassword(Scanner sc){
            System.out.println("Password: ");
            String inputPassword = sc.nextLine();
            this.setPassword(inputPassword);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user=(User) o;
            return Objects.equals( Id, user.Id );
        }

        @Override
        public int hashCode() {
            return Objects.hash( Id );
        }

        @Override
        public String toString() {
            return "User{" +
                    "Id=" + Id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", pseudo='" + pseudo + '\'' +
                    ", password='" + password + '\'' +
                    ", commentaire=" + commentaire +
                    ", spot=" + spot +
                    '}';
        }

        //  public void setCreateDate(Date date) {
        // }
    }

