package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NivelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelDTO.class);
        NivelDTO nivelDTO1 = new NivelDTO();
        nivelDTO1.setId(1L);
        NivelDTO nivelDTO2 = new NivelDTO();
        assertThat(nivelDTO1).isNotEqualTo(nivelDTO2);
        nivelDTO2.setId(nivelDTO1.getId());
        assertThat(nivelDTO1).isEqualTo(nivelDTO2);
        nivelDTO2.setId(2L);
        assertThat(nivelDTO1).isNotEqualTo(nivelDTO2);
        nivelDTO1.setId(null);
        assertThat(nivelDTO1).isNotEqualTo(nivelDTO2);
    }
}
