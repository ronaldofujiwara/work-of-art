package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaDeptoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDepto.class);
        AreaDepto areaDepto1 = new AreaDepto();
        areaDepto1.setId(1L);
        AreaDepto areaDepto2 = new AreaDepto();
        areaDepto2.setId(areaDepto1.getId());
        assertThat(areaDepto1).isEqualTo(areaDepto2);
        areaDepto2.setId(2L);
        assertThat(areaDepto1).isNotEqualTo(areaDepto2);
        areaDepto1.setId(null);
        assertThat(areaDepto1).isNotEqualTo(areaDepto2);
    }
}
