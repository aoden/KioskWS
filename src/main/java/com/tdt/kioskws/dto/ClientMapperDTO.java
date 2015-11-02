package com.tdt.kioskws.dto;

import com.tdt.kioskws.model.ClientMapper;
import lombok.Builder;
import lombok.Data;

/**
 * ClientMapperDTO
 */
@Data
@Builder
public class ClientMapperDTO {

    protected Integer id;
    protected ClientDTO client;
    protected String key;

    public ClientMapper toEntity() {

        return ClientMapper
                .builder()
                .key(key)
                .id(id)
                .client(client == null ? null :client.toEntity())
                .build();
    }
}
