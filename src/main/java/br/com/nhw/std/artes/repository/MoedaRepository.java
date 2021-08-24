package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Moeda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Moeda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoedaRepository extends JpaRepository<Moeda, Long> {}
