package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.AreaDepto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AreaDepto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaDeptoRepository extends JpaRepository<AreaDepto, Long>, JpaSpecificationExecutor<AreaDepto> {}
