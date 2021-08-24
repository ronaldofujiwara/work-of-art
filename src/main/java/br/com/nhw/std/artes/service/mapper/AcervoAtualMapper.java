package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.AcervoAtualDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcervoAtual} and its DTO {@link AcervoAtualDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AcervoAtualMapper extends EntityMapper<AcervoAtualDTO, AcervoAtual> {
    @Named("acervoAtual")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acervoAtual", source = "acervoAtual")
    AcervoAtualDTO toDtoAcervoAtual(AcervoAtual acervoAtual);
}
