package br.com.nhw.std.artes.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncaoArtistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncaoArtista.class);
        FuncaoArtista funcaoArtista1 = new FuncaoArtista();
        funcaoArtista1.setId(1L);
        FuncaoArtista funcaoArtista2 = new FuncaoArtista();
        funcaoArtista2.setId(funcaoArtista1.getId());
        assertThat(funcaoArtista1).isEqualTo(funcaoArtista2);
        funcaoArtista2.setId(2L);
        assertThat(funcaoArtista1).isNotEqualTo(funcaoArtista2);
        funcaoArtista1.setId(null);
        assertThat(funcaoArtista1).isNotEqualTo(funcaoArtista2);
    }
}
