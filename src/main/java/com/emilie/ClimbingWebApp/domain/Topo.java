package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="topo")
public class Topo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="publication_date")
    private String publicationDate;
    @Column(name="is_booked")
    private boolean isBooked;


    @OneToMany(targetEntity=TopoBooking.class, mappedBy="topo", fetch=FetchType.EAGER)
    private Set<TopoBooking> booking;

    @ManyToMany
    @JoinTable(name="topo_has_spot",
            joinColumns=@JoinColumn(name="topo_id"),
            inverseJoinColumns=@JoinColumn(name="spot_id"))
    private List<Spot> spots=new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;


    public Topo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title=title;
    }


    public void setTitle(String title) {
        this.title=title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author=author;
    }

    public Set<TopoBooking> getBooking() {
        return booking;
    }

    public void setBooking(Set<TopoBooking> booking) {
        this.booking=booking;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate=publicationDate;
    }

    public List<Spot> getSpot() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots=spots;
    }

    public void setSpot(Spot spot) {
        this.spots.add( spot );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked=isBooked;
    }

}
