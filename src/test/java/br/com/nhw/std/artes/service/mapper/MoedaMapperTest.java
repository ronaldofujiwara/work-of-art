package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoedaMapperTest {

    private MoedaMapper moedaMapper;

    @BeforeEach
    public void setUp() {
        moedaMapper = new MoedaMapperImpl();
    }
}
