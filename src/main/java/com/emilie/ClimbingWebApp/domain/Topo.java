package com.emilie.ClimbingWebApp.domain;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


@Entity
@Table(name="topo")
public class Topo  {

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="date_of_publishing")
    private String dateOfPublishing;
   // @Column(name="availability")
    //private Boolean availability;


    @OneToMany(targetEntity=ReservationTopo.class, mappedBy="topo")
    private List<ReservationTopo> reservationTopos = new ArrayList<>();

    @OneToMany(targetEntity=Spot.class, mappedBy="topo", fetch=FetchType.EAGER)
    private List<Spot> spots = new ArrayList<>();

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")//*, insertable=false, updatable=false*//*)
    private User user;

   /* @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;*/

    public Topo(Scanner sc){
        this.scanTitle(sc);
        this.scanAuthor( sc );
        this.scanDateOfPublishing(sc);
       // this.scanAvailable(sc);
    }

    public Topo(){
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

    public String getAuthor(){
        return author;
    }

    public void setTitle(String title) {
        this.title=title;
    }

   /* public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability=availability;
    }*/

    public List<Spot> getSpots() {
        return spots;
    }

    public void setAuthor(String author){
        this.author=author;
    }
    public String getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(String dateOfPublishing) {
        this.dateOfPublishing=dateOfPublishing;
    }

   /* public Boolean getAvailable(){ return availability;}
    public void setAvailable(Boolean available){ this.availability=availability;}*/

    public List<ReservationTopo> getReservationTopos() {
        return reservationTopos;
    }

    public void setReservationTopos(List<ReservationTopo> reservationTopos) {
        this.reservationTopos=reservationTopos;
    }

    public List<Spot> getSpot() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots=spots;
    }



   public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void scanTitle(Scanner sc){
        System.out.println("topoTitle: ");
        String inputTitle = sc.nextLine();
        this.setName( inputTitle);
    }

    public void scanAuthor(Scanner sc){
        System.out.println("topoAuthor:");
        String inputAuthor = sc.nextLine();
        this.setAuthor( inputAuthor );
    }

    public void scanDateOfPublishing(Scanner sc){
        System.out.println("topoDateOfPublishing: ");
        String inputDateOfPublishing = sc.nextLine();
        this.setDateOfPublishing( inputDateOfPublishing );
    }

   /* public void scanAvailable(Scanner sc){
        System.out.println("topoAvailability:");
        Boolean inputAvailable =Boolean.valueOf( sc.nextLine() );
        this.setAvailable( inputAvailable );
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topo topo=(Topo) o;
        return Objects.equals( id, topo.id ) &&
                Objects.equals( title, topo.title ) &&
                Objects.equals( author, topo.author ) &&
                Objects.equals( dateOfPublishing, topo.dateOfPublishing ) &&
              //  Objects.equals( availability, topo.availability) &&
                Objects.equals( reservationTopos, topo.reservationTopos ) &&
                Objects.equals( spots, topo.spots )&&
                Objects.equals( user, topo.user );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, title, author, dateOfPublishing/* availability*/, reservationTopos, spots, user );
    }

    @Override
    public String toString() {
        return "Topo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateOfPublishing='" + dateOfPublishing + '\'' +
                //", availability= '" + availability + '\'' +
                ", reservationTopos=" + reservationTopos +
                ", spots=" + spots +
                '}';
    }
}
