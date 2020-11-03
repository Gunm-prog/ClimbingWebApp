package com.emilie.ClimbingWebApp.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name="reservation_topo")
public class ReservationTopo  {

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="borrowed")
    private boolean borrowed;
    @Column(name="reservation")
    private boolean reservation;
    @Column(name="date_emprunt")
    private String dateEmprunt;
    @Column(name="date_retour")
    private String dateRetour;

    @ManyToOne(targetEntity=Topo.class)
    @JoinColumn(name="topo_id", referencedColumnName="id")
    private Topo topo;

    public ReservationTopo(Scanner sc){this.scanName(sc);}

    public ReservationTopo() {

    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id=id; }

    public String getName() { return name; }

    public void setName(String name) { this.name=name; }

    public boolean isBorrowed() { return borrowed; }

    public void setBorrowed(boolean borrowed) { this.borrowed=borrowed; }

    public boolean isReservation() { return reservation; }

    public void setReservation(boolean reservation) { this.reservation=reservation; }

    public String getDateEmprunt() { return dateEmprunt; }

    public void setDateEmprunt(String dateEmprunt) { this.dateEmprunt=dateEmprunt; }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour=dateRetour;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo=topo;
    }
    private void scanName(Scanner sc){
        System.out.println("reservationTopoName:");
        String inputName= sc.nextLine();
        this.setName(inputName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTopo that=(ReservationTopo) o;
        return borrowed == that.borrowed &&
                reservation == that.reservation &&
                Objects.equals( id, that.id ) &&
                Objects.equals( name, that.name ) &&
                Objects.equals( dateEmprunt, that.dateEmprunt ) &&
                Objects.equals( dateRetour, that.dateRetour ) &&
                Objects.equals( topo, that.topo );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, borrowed, reservation, dateEmprunt, dateRetour, topo );
    }

    @Override
    public String toString() {
        return "ReservationTopo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", borrowed=" + borrowed +
                ", reservation=" + reservation +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                ", topo=" + topo +
                '}';
    }
}