package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Obra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Obra entity.
 */
@Repository
public interface ObraRepository extends JpaRepository<Obra, Long>, JpaSpecificationExecutor<Obra> {
    @Query(
        value = "select distinct obra from Obra obra left join fetch obra.dadoDocumentals",
        countQuery = "select count(distinct obra) from Obra obra"
    )
    Page<Obra> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct obra from Obra obra left join fetch obra.dadoDocumentals")
    List<Obra> findAllWithEagerRelationships();

    @Query("select obra from Obra obra left join fetch obra.dadoDocumentals where obra.id =:id")
    Optional<Obra> findOneWithEagerRelationships(@Param("id") Long id);
}
