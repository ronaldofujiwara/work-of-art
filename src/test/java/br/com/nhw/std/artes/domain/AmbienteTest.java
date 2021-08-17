package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmbienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ambiente.class);
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setId(1L);
        Ambiente ambiente2 = new Ambiente();
        ambiente2.setId(ambiente1.getId());
        assertThat(ambiente1).isEqualTo(ambiente2);
        ambiente2.setId(2L);
        assertThat(ambiente1).isNotEqualTo(ambiente2);
        ambiente1.setId(null);
        assertThat(ambiente1).isNotEqualTo(ambiente2);
    }
}
