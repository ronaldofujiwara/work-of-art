package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Data;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Data entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataRepository extends JpaRepository<Data, Long> {}
