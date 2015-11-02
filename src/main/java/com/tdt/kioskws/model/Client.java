package com.tdt.kioskws.model;

import com.tdt.kioskws.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Client model
 */
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String clientID;

    @Column(unique = true)
    protected String clientSecret;
    @OneToOne(mappedBy = "client")
    protected ClientMapper clientMapper;

    public ClientDTO toDTO() {

        return ClientDTO.builder()
                .clientID(clientID)
                .clientSecret(clientSecret)
                .clientMapper(clientMapper == null ? null : clientMapper.toDTO())
                .build();
    }
}
