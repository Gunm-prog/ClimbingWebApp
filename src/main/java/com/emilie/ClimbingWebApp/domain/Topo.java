package com.emilie.ClimbingWebApp.domain;


import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="topo")
public class Topo {
    @Id
    //@javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="auhor")
    private String author;
    @Column(name="date_of_publishing")
    private String dateOfPublishing;
    @Column(name="is_reserved")
    private boolean isReserved;


    @OneToMany(targetEntity=ReservationTopo.class, mappedBy="topo", fetch=FetchType.EAGER)
    private Set<ReservationTopo> reservation;

    @ManyToMany
    //name = nom de la table d'association
    @JoinTable( name = "topo_has_spot",
            joinColumns = @JoinColumn( name = "topo_id" ),
            inverseJoinColumns = @JoinColumn( name = "spot_id" ) )
    private List<Spot> spots = new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;


    public Topo(Scanner sc) {
        this.scanTitle( sc );
        this.scanDateOfPublishing( sc );
        // this.scanAvailable(sc);
    }

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

    public Set<ReservationTopo> getReservation() {
        return reservation;
    }

    public void setReservation(Set<ReservationTopo> reservation) {
        this.reservation=reservation;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public String getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(String dateOfPublishing) {
        this.dateOfPublishing=dateOfPublishing;
    }

    public List<Spot> getSpot() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots=spots;
    }
    //methode pour donner un spot qui sera ajouté à la liste de spots
    public void setSpot(Spot spot){
        this.spots.add(spot);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved=reserved;
    }

    public void scanTitle(Scanner sc) {
        System.out.println( "topoTitle: " );
        String inputTitle=sc.nextLine();
        this.setName( inputTitle );
    }

    public void scanDateOfPublishing(Scanner sc) {
        System.out.println( "topoDateOfPublishing: " );
        String inputDateOfPublishing=sc.nextLine();
        this.setDateOfPublishing( inputDateOfPublishing );
    }
    

    @Override
    public String toString() {
        return "Topo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateOfPublishing='" + dateOfPublishing + '\'' +
                ", isReserved=" + isReserved +
                ", reservation=" + reservation +
                ", spots=" + spots +
                ", user=" + user +
                '}';
    }
}
