package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = { CidadeMapper.class })
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidade")
    EmpresaDTO toDto(Empresa s);

    @Named("empresa")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "empresa", source = "empresa")
    EmpresaDTO toDtoEmpresa(Empresa empresa);
}
