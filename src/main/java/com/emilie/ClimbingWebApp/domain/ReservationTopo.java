package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="reservation_topo")
public class ReservationTopo {

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="date_reservation")
    private LocalDateTime dateReservation;

    @Column(name="reservation_status")
    private Boolean reservationStatus;


   /* @OneToMany (targetEntity=Topo.class, mappedBy="reservation_topo", fetch=FetchType.EAGER)
   // @JoinColumn(name="topo_id", referencedColumnName="id")
    private List<Topo>topo=new ArrayList<>();*/

    //private List<User>user=new ArrayList<>();
    @ManyToOne(targetEntity=Topo.class)
    @JoinColumn(name="topo_id")
    private Topo topo;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id")
    private User user;


    public ReservationTopo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation=dateReservation;
    }

    public Boolean getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Boolean reservationStatus) {
        this.reservationStatus=reservationStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo=topo;
    }


   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTopo that=(ReservationTopo) o;
        return Objects.equals( id, that.id ) &&
                Objects.equals( dateReservation, that.dateReservation ) &&
                Objects.equals( reservationStatus, that.reservationStatus ) &&
                Objects.equals( topo, that.topo ) &&
                Objects.equals( user, that.user );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, dateReservation, reservationStatus, topo, user );
    }

    @Override
    public String toString() {
        return "ReservationTopo{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", reservationStatus=" + reservationStatus +
                ", topo=" + topo +
                ", user=" + user +
                '}';
    }*/

    @Override
    public String toString() {
        return "ReservationTopo{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}