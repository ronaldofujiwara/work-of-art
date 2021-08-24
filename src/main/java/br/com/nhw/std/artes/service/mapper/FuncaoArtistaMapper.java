package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.FuncaoArtistaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuncaoArtista} and its DTO {@link FuncaoArtistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FuncaoArtistaMapper extends EntityMapper<FuncaoArtistaDTO, FuncaoArtista> {
    @Named("funcaoArtista")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "funcaoArtista", source = "funcaoArtista")
    FuncaoArtistaDTO toDtoFuncaoArtista(FuncaoArtista funcaoArtista);
}
