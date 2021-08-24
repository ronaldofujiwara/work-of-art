package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeguroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seguro.class);
        Seguro seguro1 = new Seguro();
        seguro1.setId(1L);
        Seguro seguro2 = new Seguro();
        seguro2.setId(seguro1.getId());
        assertThat(seguro1).isEqualTo(seguro2);
        seguro2.setId(2L);
        assertThat(seguro1).isNotEqualTo(seguro2);
        seguro1.setId(null);
        assertThat(seguro1).isNotEqualTo(seguro2);
    }
}
