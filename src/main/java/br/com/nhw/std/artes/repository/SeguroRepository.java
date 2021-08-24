package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Seguro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Seguro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {}
