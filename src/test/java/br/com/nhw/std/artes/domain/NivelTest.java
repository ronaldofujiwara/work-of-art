package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NivelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nivel.class);
        Nivel nivel1 = new Nivel();
        nivel1.setId(1L);
        Nivel nivel2 = new Nivel();
        nivel2.setId(nivel1.getId());
        assertThat(nivel1).isEqualTo(nivel2);
        nivel2.setId(2L);
        assertThat(nivel1).isNotEqualTo(nivel2);
        nivel1.setId(null);
        assertThat(nivel1).isNotEqualTo(nivel2);
    }
}
