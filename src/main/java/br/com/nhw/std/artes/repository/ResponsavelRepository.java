package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Responsavel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Responsavel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {}
