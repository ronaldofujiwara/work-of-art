package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.TecnicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tecnica} and its DTO {@link TecnicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TecnicaMapper extends EntityMapper<TecnicaDTO, Tecnica> {
    @Named("tecnica")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tecnica", source = "tecnica")
    TecnicaDTO toDtoTecnica(Tecnica tecnica);
}
