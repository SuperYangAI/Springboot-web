package com.actec.ms.vo;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
public class BaseEntity {
    public static final int STATUS_OK = 1;
    public static final int STATUS_DEL = 0;

    @Id
    private int id;
    private String name;
    private int status = STATUS_OK;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
