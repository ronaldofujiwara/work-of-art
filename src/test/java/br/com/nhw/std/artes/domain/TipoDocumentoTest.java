package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDocumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDocumento.class);
        TipoDocumento tipoDocumento1 = new TipoDocumento();
        tipoDocumento1.setId(1L);
        TipoDocumento tipoDocumento2 = new TipoDocumento();
        tipoDocumento2.setId(tipoDocumento1.getId());
        assertThat(tipoDocumento1).isEqualTo(tipoDocumento2);
        tipoDocumento2.setId(2L);
        assertThat(tipoDocumento1).isNotEqualTo(tipoDocumento2);
        tipoDocumento1.setId(null);
        assertThat(tipoDocumento1).isNotEqualTo(tipoDocumento2);
    }
}
