package com.tdt.kioskws.dto;

import com.tdt.kioskws.model.ClientMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClientMapperDTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientMapperDTO {

    protected int id;
    protected ClientDTO client;
    protected String key;

    public ClientMapper toEntity() {

        return ClientMapper
                .builder()
                .id(id)
                .key(key)
                .client(client == null ? null : client.toEntity())
                .build();
    }
}
