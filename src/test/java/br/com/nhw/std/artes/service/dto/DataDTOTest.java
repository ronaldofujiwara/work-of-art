package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataDTO.class);
        DataDTO dataDTO1 = new DataDTO();
        dataDTO1.setId(1L);
        DataDTO dataDTO2 = new DataDTO();
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
        dataDTO2.setId(dataDTO1.getId());
        assertThat(dataDTO1).isEqualTo(dataDTO2);
        dataDTO2.setId(2L);
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
        dataDTO1.setId(null);
        assertThat(dataDTO1).isNotEqualTo(dataDTO2);
    }
}
