package com.tdt.kioskws.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * ClientMapper
 */
@Entity
@Data
@Builder
public class ClientMapper {

    @Id
    @GeneratedValue
    protected int id;

    @OneToOne
    @PrimaryKeyJoinColumn
    protected Client client;
    @Column(unique = true)
    protected String key;
}
