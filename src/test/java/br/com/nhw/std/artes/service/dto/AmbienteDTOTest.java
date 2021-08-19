package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmbienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmbienteDTO.class);
        AmbienteDTO ambienteDTO1 = new AmbienteDTO();
        ambienteDTO1.setId(1L);
        AmbienteDTO ambienteDTO2 = new AmbienteDTO();
        assertThat(ambienteDTO1).isNotEqualTo(ambienteDTO2);
        ambienteDTO2.setId(ambienteDTO1.getId());
        assertThat(ambienteDTO1).isEqualTo(ambienteDTO2);
        ambienteDTO2.setId(2L);
        assertThat(ambienteDTO1).isNotEqualTo(ambienteDTO2);
        ambienteDTO1.setId(null);
        assertThat(ambienteDTO1).isNotEqualTo(ambienteDTO2);
    }
}
