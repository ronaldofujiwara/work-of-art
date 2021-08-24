package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Espaco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Espaco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {}
