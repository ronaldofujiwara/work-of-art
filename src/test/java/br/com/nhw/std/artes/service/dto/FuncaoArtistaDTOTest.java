package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncaoArtistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncaoArtistaDTO.class);
        FuncaoArtistaDTO funcaoArtistaDTO1 = new FuncaoArtistaDTO();
        funcaoArtistaDTO1.setId(1L);
        FuncaoArtistaDTO funcaoArtistaDTO2 = new FuncaoArtistaDTO();
        assertThat(funcaoArtistaDTO1).isNotEqualTo(funcaoArtistaDTO2);
        funcaoArtistaDTO2.setId(funcaoArtistaDTO1.getId());
        assertThat(funcaoArtistaDTO1).isEqualTo(funcaoArtistaDTO2);
        funcaoArtistaDTO2.setId(2L);
        assertThat(funcaoArtistaDTO1).isNotEqualTo(funcaoArtistaDTO2);
        funcaoArtistaDTO1.setId(null);
        assertThat(funcaoArtistaDTO1).isNotEqualTo(funcaoArtistaDTO2);
    }
}
