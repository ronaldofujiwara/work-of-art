package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.DadoDocumental;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DadoDocumental entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DadoDocumentalRepository extends JpaRepository<DadoDocumental, Long> {}
