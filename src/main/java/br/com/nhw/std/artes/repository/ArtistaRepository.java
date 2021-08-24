package br.com.nhw.std.artes.repository;

import br.com.nhw.std.artes.domain.Artista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Artista entity.
 */
@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>, JpaSpecificationExecutor<Artista> {
    @Query(
        value = "select distinct artista from Artista artista left join fetch artista.contatoes",
        countQuery = "select count(distinct artista) from Artista artista"
    )
    Page<Artista> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct artista from Artista artista left join fetch artista.contatoes")
    List<Artista> findAllWithEagerRelationships();

    @Query("select artista from Artista artista left join fetch artista.contatoes where artista.id =:id")
    Optional<Artista> findOneWithEagerRelationships(@Param("id") Long id);
}
