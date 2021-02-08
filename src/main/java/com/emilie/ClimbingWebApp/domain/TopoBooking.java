package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="topo_booking")
public class TopoBooking {

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="booking_date")
    private LocalDateTime bookingDate;

    @Column(name="booking_status")
    private Boolean bookingStatus;


    @ManyToOne(targetEntity=Topo.class)
    @JoinColumn(name="topo_id")
    private Topo topo;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id")
    private User user;


    public TopoBooking() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate=bookingDate;
    }

    public Boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Boolean bookingStatus) {
        this.bookingStatus=bookingStatus;
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


}

