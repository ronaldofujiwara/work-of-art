package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.AndarMapa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AndarMapa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AndarMapaRepository extends JpaRepository<AndarMapa, Long> {}
