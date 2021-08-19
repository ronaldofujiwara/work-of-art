package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaDeptoMapperTest {

    private AreaDeptoMapper areaDeptoMapper;

    @BeforeEach
    public void setUp() {
        areaDeptoMapper = new AreaDeptoMapperImpl();
    }
}
