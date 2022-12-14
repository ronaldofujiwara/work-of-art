package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.ContatoDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contato} and its DTO {@link ContatoDTO}.
 */
@Mapper(componentModel = "spring", uses = { AreaDeptoMapper.class, CidadeMapper.class })
public interface ContatoMapper extends EntityMapper<ContatoDTO, Contato> {
    @Mapping(target = "area", source = "area", qualifiedByName = "area")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidade")
    ContatoDTO toDto(Contato s);

    @Named("nomeCompSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeComp", source = "nomeComp")
    Set<ContatoDTO> toDtoNomeCompSet(Set<Contato> contato);

    @Named("nomeComp")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeComp", source = "nomeComp")
    ContatoDTO toDtoNomeComp(Contato contato);
}
