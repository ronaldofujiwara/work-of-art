package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcervoAtualTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcervoAtual.class);
        AcervoAtual acervoAtual1 = new AcervoAtual();
        acervoAtual1.setId(1L);
        AcervoAtual acervoAtual2 = new AcervoAtual();
        acervoAtual2.setId(acervoAtual1.getId());
        assertThat(acervoAtual1).isEqualTo(acervoAtual2);
        acervoAtual2.setId(2L);
        assertThat(acervoAtual1).isNotEqualTo(acervoAtual2);
        acervoAtual1.setId(null);
        assertThat(acervoAtual1).isNotEqualTo(acervoAtual2);
    }
}
