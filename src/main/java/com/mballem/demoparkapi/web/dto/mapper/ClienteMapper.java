package com.mballem.demoparkapi.web.dto.mapper;

import com.mballem.demoparkapi.entity.Cliente;
import com.mballem.demoparkapi.web.dto.ClienteCreateDto;
import com.mballem.demoparkapi.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDto dto) {

        return new ModelMapper().map(dto, Cliente.class);
    }


    public static ClienteResponseDto toClienteDto(Cliente cliente) {

        return new ModelMapper().map(cliente, ClienteResponseDto.class);

    }

    public static ClienteResponseDto toDto(Cliente cliente) {

        ModelMapper mapperMain = new ModelMapper();
        TypeMap<Cliente, ClienteResponseDto> propertyMapper = mapperMain.createTypeMap(Cliente.class, ClienteResponseDto.class);
        return mapperMain.map(cliente, ClienteResponseDto.class);
    }

    public static List<ClienteResponseDto> toListDto(List<Cliente> clientes) {
        return clientes.stream().map(cliente -> toDto(cliente)).collect(Collectors.toList());
    }
}
