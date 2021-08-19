package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmbienteMapperTest {

    private AmbienteMapper ambienteMapper;

    @BeforeEach
    public void setUp() {
        ambienteMapper = new AmbienteMapperImpl();
    }
}
