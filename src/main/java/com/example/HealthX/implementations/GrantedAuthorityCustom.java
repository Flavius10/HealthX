package com.example.HealthX.implementations;

import com.example.HealthX.entities.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityCustom implements GrantedAuthority {

    private final Authority authority;

    public GrantedAuthorityCustom(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority.getName();
    }

}
