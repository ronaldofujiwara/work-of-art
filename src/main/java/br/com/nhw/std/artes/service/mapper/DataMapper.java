package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.DataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Data} and its DTO {@link DataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataMapper extends EntityMapper<DataDTO, Data> {
    @Named("data")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "data", source = "data")
    DataDTO toDtoData(Data data);
}
