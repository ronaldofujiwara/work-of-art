package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.EspacoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Espaco} and its DTO {@link EspacoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EspacoMapper extends EntityMapper<EspacoDTO, Espaco> {
    @Named("espaco")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "espaco", source = "espaco")
    EspacoDTO toDtoEspaco(Espaco espaco);
}
