package engine.dao;

import engine.entities.Completed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends PagingAndSortingRepository<Completed, Long> {

    @Query("SELECT c FROM Completed c WHERE c.email = :email order by c.completedAt desc")
    Page<Completed> findAllByUserOrderByCompletedAtDesc(String email, Pageable pageable);

}