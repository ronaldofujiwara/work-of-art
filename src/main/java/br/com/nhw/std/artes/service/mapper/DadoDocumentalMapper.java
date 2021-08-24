package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.DadoDocumentalDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DadoDocumental} and its DTO {@link DadoDocumentalDTO}.
 */
@Mapper(componentModel = "spring", uses = { TipoDocumentoMapper.class })
public interface DadoDocumentalMapper extends EntityMapper<DadoDocumentalDTO, DadoDocumental> {
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "tipoDocumento")
    DadoDocumentalDTO toDto(DadoDocumental s);

    @Named("dataSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "data", source = "data")
    Set<DadoDocumentalDTO> toDtoDataSet(Set<DadoDocumental> dadoDocumental);
}
