package br.com.nhw.std.artes.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeguroMapperTest {

    private SeguroMapper seguroMapper;

    @BeforeEach
    public void setUp() {
        seguroMapper = new SeguroMapperImpl();
    }
}
