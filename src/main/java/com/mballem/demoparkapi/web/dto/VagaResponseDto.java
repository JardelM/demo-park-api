package com.mballem.demoparkapi.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VagaResponseDto {

    private Long id;

    private String codigo;

    private String status;
}
