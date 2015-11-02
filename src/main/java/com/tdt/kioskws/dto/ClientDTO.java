package com.tdt.kioskws.dto;

import com.tdt.kioskws.model.Client;
import lombok.Builder;
import lombok.Data;

/**
 * ClientDTO.
 */
@Data
@Builder
public class ClientDTO {

    protected String clientID;
    protected String clientSecret;
    protected ClientMapperDTO clientMapper;

    public Client toEntity() {

        return Client.builder()
                .clientID(clientID)
                .clientSecret(clientSecret)
                .clientMapper(clientMapper == null ? null : clientMapper.toEntity())
                .build();
    }
}
