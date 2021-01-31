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


    @Override
    public String toString() {
        return "ReservationTopo{" + "\n" +
                "id=" + id + "\n" +
                ", dateReservation=" + dateReservation + "\n" +
                ", reservationStatus=" + reservationStatus + "\n" +
                ", user=" + user + "\n" +
          //      ", topo=" + topo + "\n" +
                '}' + "\n";
    }
}