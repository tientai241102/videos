package com.example.video.entities;

import com.example.video.entities.user.User;

import javax.persistence.*;

@Entity
@Table(name = "follows")
public class Follow extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ownerId;

    private int partnerId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partnerId", referencedColumnName = "id", insertable = false, updatable = false)
    private User partner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public User getPartner() {
        return partner;
    }

    public void setPartner(User partner) {
        this.partner = partner;
    }
}