package com.tdt.kioskws.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Client model
 */
@Builder
@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String clientID;

    @Column(unique = true)
    protected String clientSecret;
    @OneToOne(mappedBy = "client")
    protected ClientMapper clientMapper;
}
