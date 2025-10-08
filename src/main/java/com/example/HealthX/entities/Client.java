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
    private String clientId;

    @Column(name = "secret", nullable = false, length = 50)
    private String secret;

    @Column(name = "scope", nullable = false, length = 50)
    private String scope;

    @Column(name = "redirect_uri", nullable = false, length = 50)
    private String redirectUri;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Grant> grants;

    public Client(){}

    public int getId(){
        return this.id;
    }

    public String getClientId(){
        return this.clientId;
    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public String getSecret(){
        return this.secret;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getScope(){
        return this.scope;
    }

    public void setScope(String scope_type){
        this.scope = scope_type;
    }

    public String getRedirectUri(){
        return this.redirectUri;
    }

    public void setRedirecUri(String redirect_uri){
        this.redirectUri = redirect_uri;
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
                ", client_id='" + clientId + '\'' +
                ", secret='" + secret + '\'' +
                ", scope_type='" + scope + '\'' +
                ", redirect_uri='" + redirectUri + '\'' +
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
