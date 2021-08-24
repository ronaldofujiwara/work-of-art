package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Tecnica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tecnica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TecnicaRepository extends JpaRepository<Tecnica, Long> {}
