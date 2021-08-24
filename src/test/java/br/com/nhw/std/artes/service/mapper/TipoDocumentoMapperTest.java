package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoDocumentoMapperTest {

    private TipoDocumentoMapper tipoDocumentoMapper;

    @BeforeEach
    public void setUp() {
        tipoDocumentoMapper = new TipoDocumentoMapperImpl();
    }
}
