package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.service.dto.CidadeDTO;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {
    List<Cidade> findAllOrderByCidade(Specification<Cidade> specification);
}
