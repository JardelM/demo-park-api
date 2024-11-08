package com.mballem.demoparkapi.web.dto.mapper;

import com.mballem.demoparkapi.web.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto dto (Page page){
        return new ModelMapper().map(page, PageableDto.class);
    }
}
