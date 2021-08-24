package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TecnicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tecnica.class);
        Tecnica tecnica1 = new Tecnica();
        tecnica1.setId(1L);
        Tecnica tecnica2 = new Tecnica();
        tecnica2.setId(tecnica1.getId());
        assertThat(tecnica1).isEqualTo(tecnica2);
        tecnica2.setId(2L);
        assertThat(tecnica1).isNotEqualTo(tecnica2);
        tecnica1.setId(null);
        assertThat(tecnica1).isNotEqualTo(tecnica2);
    }
}
