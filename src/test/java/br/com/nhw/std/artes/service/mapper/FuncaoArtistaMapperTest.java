package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncaoArtistaMapperTest {

    private FuncaoArtistaMapper funcaoArtistaMapper;

    @BeforeEach
    public void setUp() {
        funcaoArtistaMapper = new FuncaoArtistaMapperImpl();
    }
}
