package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeguroDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguroDTO.class);
        SeguroDTO seguroDTO1 = new SeguroDTO();
        seguroDTO1.setId(1L);
        SeguroDTO seguroDTO2 = new SeguroDTO();
        assertThat(seguroDTO1).isNotEqualTo(seguroDTO2);
        seguroDTO2.setId(seguroDTO1.getId());
        assertThat(seguroDTO1).isEqualTo(seguroDTO2);
        seguroDTO2.setId(2L);
        assertThat(seguroDTO1).isNotEqualTo(seguroDTO2);
        seguroDTO1.setId(null);
        assertThat(seguroDTO1).isNotEqualTo(seguroDTO2);
    }
}
