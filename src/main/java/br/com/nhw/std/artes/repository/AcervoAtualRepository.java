package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.AcervoAtual;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AcervoAtual entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcervoAtualRepository extends JpaRepository<AcervoAtual, Long> {}
