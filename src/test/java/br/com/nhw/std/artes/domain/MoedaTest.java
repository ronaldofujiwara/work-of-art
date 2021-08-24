package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoedaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Moeda.class);
        Moeda moeda1 = new Moeda();
        moeda1.setId(1L);
        Moeda moeda2 = new Moeda();
        moeda2.setId(moeda1.getId());
        assertThat(moeda1).isEqualTo(moeda2);
        moeda2.setId(2L);
        assertThat(moeda1).isNotEqualTo(moeda2);
        moeda1.setId(null);
        assertThat(moeda1).isNotEqualTo(moeda2);
    }
}
