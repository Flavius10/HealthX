package com.example.HealthX.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id", nullable = false, length = 50)
    private String client_id;

    @Column(name = "secret", nullable = false, length = 50)
    private String secret;

    @Column(name = "scope_type", nullable = false, length = 50)
    private String scope_type;

    @Column(name = "redirect_uri", nullable = false, length = 50)
    private String redirect_uri;

    @OneToMany(mappedBy = "client_id", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Grant> grants;

    public Client(){}

    public int getId(){
        return this.id;
    }

    public String getClient_id(){
        return this.client_id;
    }

    public void setClient_id(String client_id){
        this.client_id = client_id;
    }

    public String getSecret(){
        return this.secret;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getScope_type(){
        return this.scope_type;
    }

    public void setScope_type(String scope_type){
        this.scope_type = scope_type;
    }

    public String getRedirect_uri(){
        return this.redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri){
        this.redirect_uri = redirect_uri;
    }

    public List<Grant> getGrants(){
        return this.grants;
    }
    public void setGrants(List<Grant> grants){
        this.grants = grants;
    }

    @Override
    public String toString(){
        return "Client{" +
                "id=" + id +
                ", client_id='" + client_id + '\'' +
                ", secret='" + secret + '\'' +
                ", scope_type='" + scope_type + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object e){
        if (e == null) return false;
        if (!(e instanceof Client client)) return false;
        return this.id == client.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
