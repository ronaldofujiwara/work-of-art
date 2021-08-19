package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.CidadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {
    @Named("cidade")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cidade", source = "cidade")
    CidadeDTO toDtoCidade(Cidade cidade);
}
