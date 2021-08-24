package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DadoDocumentalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadoDocumentalDTO.class);
        DadoDocumentalDTO dadoDocumentalDTO1 = new DadoDocumentalDTO();
        dadoDocumentalDTO1.setId(1L);
        DadoDocumentalDTO dadoDocumentalDTO2 = new DadoDocumentalDTO();
        assertThat(dadoDocumentalDTO1).isNotEqualTo(dadoDocumentalDTO2);
        dadoDocumentalDTO2.setId(dadoDocumentalDTO1.getId());
        assertThat(dadoDocumentalDTO1).isEqualTo(dadoDocumentalDTO2);
        dadoDocumentalDTO2.setId(2L);
        assertThat(dadoDocumentalDTO1).isNotEqualTo(dadoDocumentalDTO2);
        dadoDocumentalDTO1.setId(null);
        assertThat(dadoDocumentalDTO1).isNotEqualTo(dadoDocumentalDTO2);
    }
}
