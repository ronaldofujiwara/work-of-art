package br.com.nhw.std.artes.service.mapper;

import br.com.nhw.std.artes.domain.*;
import br.com.nhw.std.artes.service.dto.ObraDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Obra} and its DTO {@link ObraDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        DadoDocumentalMapper.class,
        ArtistaMapper.class,
        CategoriaMapper.class,
        TecnicaMapper.class,
        NivelMapper.class,
        DataMapper.class,
        EmpresaMapper.class,
        MoedaMapper.class,
        SeguroMapper.class,
        ResponsavelMapper.class,
        AcervoAtualMapper.class,
        ContatoMapper.class,
        AndarMapaMapper.class,
    }
)
public interface ObraMapper extends EntityMapper<ObraDTO, Obra> {
    @Mapping(target = "dadoDocumentals", source = "dadoDocumentals", qualifiedByName = "dataSet")
    @Mapping(target = "artista", source = "artista", qualifiedByName = "nome")
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoria")
    @Mapping(target = "tecnica", source = "tecnica", qualifiedByName = "tecnica")
    @Mapping(target = "nivel", source = "nivel", qualifiedByName = "nivel")
    @Mapping(target = "data", source = "data", qualifiedByName = "data")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresa")
    @Mapping(target = "moeda", source = "moeda", qualifiedByName = "tipoMoeda")
    @Mapping(target = "seguro", source = "seguro", qualifiedByName = "apolice")
    @Mapping(target = "responsavel", source = "responsavel", qualifiedByName = "nome")
    @Mapping(target = "acervoatual", source = "acervoatual", qualifiedByName = "acervoAtual")
    @Mapping(target = "fotografo", source = "fotografo", qualifiedByName = "nomeComp")
    @Mapping(target = "andarMapa", source = "andarMapa", qualifiedByName = "id")
    ObraDTO toDto(Obra s);

    @Mapping(target = "removeDadoDocumental", ignore = true)
    Obra toEntity(ObraDTO obraDTO);
}
