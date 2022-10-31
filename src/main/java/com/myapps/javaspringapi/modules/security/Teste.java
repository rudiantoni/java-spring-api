package com.myapps.javaspringapi.modules.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teste_table")
public class Teste {

    @Id
    public Long id = null;

    public String name = null;
}
