package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.AreaDeptoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaDepto} and its DTO {@link AreaDeptoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AreaDeptoMapper extends EntityMapper<AreaDeptoDTO, AreaDepto> {
    @Named("area")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "area", source = "area")
    AreaDeptoDTO toDtoArea(AreaDepto areaDepto);
}
