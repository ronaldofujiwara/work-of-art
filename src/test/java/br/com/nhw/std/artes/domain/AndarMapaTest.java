package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AndarMapaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AndarMapa.class);
        AndarMapa andarMapa1 = new AndarMapa();
        andarMapa1.setId(1L);
        AndarMapa andarMapa2 = new AndarMapa();
        andarMapa2.setId(andarMapa1.getId());
        assertThat(andarMapa1).isEqualTo(andarMapa2);
        andarMapa2.setId(2L);
        assertThat(andarMapa1).isNotEqualTo(andarMapa2);
        andarMapa1.setId(null);
        assertThat(andarMapa1).isNotEqualTo(andarMapa2);
    }
}
