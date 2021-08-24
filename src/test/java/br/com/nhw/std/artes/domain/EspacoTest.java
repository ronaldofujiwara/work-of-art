package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspacoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Espaco.class);
        Espaco espaco1 = new Espaco();
        espaco1.setId(1L);
        Espaco espaco2 = new Espaco();
        espaco2.setId(espaco1.getId());
        assertThat(espaco1).isEqualTo(espaco2);
        espaco2.setId(2L);
        assertThat(espaco1).isNotEqualTo(espaco2);
        espaco1.setId(null);
        assertThat(espaco1).isNotEqualTo(espaco2);
    }
}
