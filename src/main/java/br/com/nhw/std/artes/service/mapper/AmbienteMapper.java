package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.AmbienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ambiente} and its DTO {@link AmbienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmbienteMapper extends EntityMapper<AmbienteDTO, Ambiente> {}
