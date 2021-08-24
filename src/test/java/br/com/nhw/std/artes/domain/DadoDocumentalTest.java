package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DadoDocumentalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadoDocumental.class);
        DadoDocumental dadoDocumental1 = new DadoDocumental();
        dadoDocumental1.setId(1L);
        DadoDocumental dadoDocumental2 = new DadoDocumental();
        dadoDocumental2.setId(dadoDocumental1.getId());
        assertThat(dadoDocumental1).isEqualTo(dadoDocumental2);
        dadoDocumental2.setId(2L);
        assertThat(dadoDocumental1).isNotEqualTo(dadoDocumental2);
        dadoDocumental1.setId(null);
        assertThat(dadoDocumental1).isNotEqualTo(dadoDocumental2);
    }
}
