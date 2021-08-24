package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.NivelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nivel} and its DTO {@link NivelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NivelMapper extends EntityMapper<NivelDTO, Nivel> {
    @Named("nivel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nivel", source = "nivel")
    NivelDTO toDtoNivel(Nivel nivel);
}
