package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcervoAtualDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcervoAtualDTO.class);
        AcervoAtualDTO acervoAtualDTO1 = new AcervoAtualDTO();
        acervoAtualDTO1.setId(1L);
        AcervoAtualDTO acervoAtualDTO2 = new AcervoAtualDTO();
        assertThat(acervoAtualDTO1).isNotEqualTo(acervoAtualDTO2);
        acervoAtualDTO2.setId(acervoAtualDTO1.getId());
        assertThat(acervoAtualDTO1).isEqualTo(acervoAtualDTO2);
        acervoAtualDTO2.setId(2L);
        assertThat(acervoAtualDTO1).isNotEqualTo(acervoAtualDTO2);
        acervoAtualDTO1.setId(null);
        assertThat(acervoAtualDTO1).isNotEqualTo(acervoAtualDTO2);
    }
}
