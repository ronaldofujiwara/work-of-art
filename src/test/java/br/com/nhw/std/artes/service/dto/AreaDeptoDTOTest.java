package br.com.nhw.std.artes.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.nhw.std.artes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaDeptoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDeptoDTO.class);
        AreaDeptoDTO areaDeptoDTO1 = new AreaDeptoDTO();
        areaDeptoDTO1.setId(1L);
        AreaDeptoDTO areaDeptoDTO2 = new AreaDeptoDTO();
        assertThat(areaDeptoDTO1).isNotEqualTo(areaDeptoDTO2);
        areaDeptoDTO2.setId(areaDeptoDTO1.getId());
        assertThat(areaDeptoDTO1).isEqualTo(areaDeptoDTO2);
        areaDeptoDTO2.setId(2L);
        assertThat(areaDeptoDTO1).isNotEqualTo(areaDeptoDTO2);
        areaDeptoDTO1.setId(null);
        assertThat(areaDeptoDTO1).isNotEqualTo(areaDeptoDTO2);
    }
}
