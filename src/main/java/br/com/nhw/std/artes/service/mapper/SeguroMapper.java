package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.SeguroDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seguro} and its DTO {@link SeguroDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContatoMapper.class, MoedaMapper.class })
public interface SeguroMapper extends EntityMapper<SeguroDTO, Seguro> {
    @Mapping(target = "contatoSeg", source = "contatoSeg", qualifiedByName = "nomeComp")
    @Mapping(target = "contatoCor", source = "contatoCor", qualifiedByName = "nomeComp")
    @Mapping(target = "moeda", source = "moeda", qualifiedByName = "tipoMoeda")
    SeguroDTO toDto(Seguro s);

    @Named("apolice")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "apolice", source = "apolice")
    SeguroDTO toDtoApolice(Seguro seguro);
}
