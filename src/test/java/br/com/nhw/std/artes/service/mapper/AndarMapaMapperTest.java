package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AndarMapaMapperTest {

    private AndarMapaMapper andarMapaMapper;

    @BeforeEach
    public void setUp() {
        andarMapaMapper = new AndarMapaMapperImpl();
    }
}
