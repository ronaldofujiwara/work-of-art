package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.MoedaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Moeda} and its DTO {@link MoedaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoedaMapper extends EntityMapper<MoedaDTO, Moeda> {
    @Named("tipoMoeda")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoMoeda", source = "tipoMoeda")
    MoedaDTO toDtoTipoMoeda(Moeda moeda);
}
