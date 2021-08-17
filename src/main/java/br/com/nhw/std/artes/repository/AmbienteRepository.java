package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Ambiente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ambiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Long>, JpaSpecificationExecutor<Ambiente> {}
