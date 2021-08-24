package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.ResponsavelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Responsavel} and its DTO {@link ResponsavelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResponsavelMapper extends EntityMapper<ResponsavelDTO, Responsavel> {
    @Named("nome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ResponsavelDTO toDtoNome(Responsavel responsavel);
}
