package com.tdt.kioskws.model;

import com.tdt.kioskws.dto.ClientMapperDTO;
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
    @JoinColumn(name = "clientid")
    protected Client client;
    @Column(unique = true)
    protected String key;

    public ClientMapperDTO toDTO() {

        return ClientMapperDTO
                .builder()
                .client(client == null ? null : client.toDTO())
                .id(id)
                .key(key)
                .build();
    }
}
