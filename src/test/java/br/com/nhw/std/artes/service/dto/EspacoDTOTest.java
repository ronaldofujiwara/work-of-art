package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspacoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspacoDTO.class);
        EspacoDTO espacoDTO1 = new EspacoDTO();
        espacoDTO1.setId(1L);
        EspacoDTO espacoDTO2 = new EspacoDTO();
        assertThat(espacoDTO1).isNotEqualTo(espacoDTO2);
        espacoDTO2.setId(espacoDTO1.getId());
        assertThat(espacoDTO1).isEqualTo(espacoDTO2);
        espacoDTO2.setId(2L);
        assertThat(espacoDTO1).isNotEqualTo(espacoDTO2);
        espacoDTO1.setId(null);
        assertThat(espacoDTO1).isNotEqualTo(espacoDTO2);
    }
}
