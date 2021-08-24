package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AndarMapaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AndarMapaDTO.class);
        AndarMapaDTO andarMapaDTO1 = new AndarMapaDTO();
        andarMapaDTO1.setId(1L);
        AndarMapaDTO andarMapaDTO2 = new AndarMapaDTO();
        assertThat(andarMapaDTO1).isNotEqualTo(andarMapaDTO2);
        andarMapaDTO2.setId(andarMapaDTO1.getId());
        assertThat(andarMapaDTO1).isEqualTo(andarMapaDTO2);
        andarMapaDTO2.setId(2L);
        assertThat(andarMapaDTO1).isNotEqualTo(andarMapaDTO2);
        andarMapaDTO1.setId(null);
        assertThat(andarMapaDTO1).isNotEqualTo(andarMapaDTO2);
    }
}
