package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.AndarMapaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AndarMapa} and its DTO {@link AndarMapaDTO}.
 */
@Mapper(componentModel = "spring", uses = { EspacoMapper.class })
public interface AndarMapaMapper extends EntityMapper<AndarMapaDTO, AndarMapa> {
    @Mapping(target = "espaco", source = "espaco", qualifiedByName = "espaco")
    AndarMapaDTO toDto(AndarMapa s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AndarMapaDTO toDtoId(AndarMapa andarMapa);
}
