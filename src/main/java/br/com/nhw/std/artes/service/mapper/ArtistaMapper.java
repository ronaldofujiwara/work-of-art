package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.ArtistaDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artista} and its DTO {@link ArtistaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContatoMapper.class, CidadeMapper.class, ResponsavelMapper.class, FuncaoArtistaMapper.class })
public interface ArtistaMapper extends EntityMapper<ArtistaDTO, Artista> {
    @Mapping(target = "contatoes", source = "contatoes", qualifiedByName = "nomeCompSet")
    @Mapping(target = "cidadeNasc", source = "cidadeNasc", qualifiedByName = "cidade")
    @Mapping(target = "cidadeFalesc", source = "cidadeFalesc", qualifiedByName = "cidade")
    @Mapping(target = "respVerbete", source = "respVerbete", qualifiedByName = "nome")
    @Mapping(target = "funcaoArtista", source = "funcaoArtista", qualifiedByName = "funcaoArtista")
    ArtistaDTO toDto(Artista s);

    @Mapping(target = "removeContato", ignore = true)
    Artista toEntity(ArtistaDTO artistaDTO);

    @Named("nome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ArtistaDTO toDtoNome(Artista artista);
}
