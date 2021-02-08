package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="spot")
public class Spot {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="spot_name")
    private String name;
    @Column(name="spot_description")
    private String description;
    @Column(name="tag")
    private boolean tag;


    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id",
            referencedColumnName="id")
    private User user;

    @OneToMany(targetEntity=Area.class,
            mappedBy="spot")
    private List<Area> area=new ArrayList<>();

    @ManyToMany
    //name = nom de la table d'association
    @JoinTable(name="topo_has_spot",
            joinColumns=@JoinColumn(name="spot_id"),
            inverseJoinColumns=@JoinColumn(name="topo_id"))
    private List<Topo> topos=new ArrayList<>();

    @OneToMany(targetEntity=Comment.class,
            mappedBy="spot")
    private List<Comment> comments=new ArrayList<>();


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public List<Comment> getComments() { //TODO why getComments et setCommentaire sont en gris????
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments=comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public List<Area> getSecteur() {
        return area;
    }

    public void setSecteur(List<Area> area) {
        this.area=area;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos=topos;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag=tag;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area=area;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot=(Spot) o;
        return tag == spot.tag &&
                Objects.equals( id, spot.id ) &&
                Objects.equals( name, spot.name ) &&
                Objects.equals( description, spot.description ) &&
                Objects.equals( user, spot.user ) &&
                Objects.equals( area, spot.area ) &&
                Objects.equals( topos, spot.topos ) &&
                Objects.equals( comments, spot.comments );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, description, tag, user, area, topos, comments );
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", name='" + name + '\n' +
                ", description='" + description + '\n' +
                ", user=" + user + '\n'
                ;
    }
}
