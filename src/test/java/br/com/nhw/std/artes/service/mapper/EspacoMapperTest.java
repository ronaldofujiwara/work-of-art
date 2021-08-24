package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EspacoMapperTest {

    private EspacoMapper espacoMapper;

    @BeforeEach
    public void setUp() {
        espacoMapper = new EspacoMapperImpl();
    }
}
