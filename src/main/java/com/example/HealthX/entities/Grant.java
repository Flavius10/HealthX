package com.example.HealthX.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "grants")
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grant_type", nullable = false, length = 50)
    private String grant_type;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client_id;

    public Grant() {}

    public int getId() {
        return this.id;
    }

    public String getGrant_type() {
        return this.grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public Client getClient_id() {
        return this.client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString(){
        return "Grant{" +
                "id=" + id +
                ", grant_type='" + grant_type + '\'' +
                ", client_id=" + client_id +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Grant) o).id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
