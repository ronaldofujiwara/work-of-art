package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TecnicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TecnicaDTO.class);
        TecnicaDTO tecnicaDTO1 = new TecnicaDTO();
        tecnicaDTO1.setId(1L);
        TecnicaDTO tecnicaDTO2 = new TecnicaDTO();
        assertThat(tecnicaDTO1).isNotEqualTo(tecnicaDTO2);
        tecnicaDTO2.setId(tecnicaDTO1.getId());
        assertThat(tecnicaDTO1).isEqualTo(tecnicaDTO2);
        tecnicaDTO2.setId(2L);
        assertThat(tecnicaDTO1).isNotEqualTo(tecnicaDTO2);
        tecnicaDTO1.setId(null);
        assertThat(tecnicaDTO1).isNotEqualTo(tecnicaDTO2);
    }
}
