package ma.fstt.repo;

import ma.fstt.entity.VolontaringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolontaringRepo extends JpaRepository<VolontaringEntity,Long> {
    @Query("SELECT a FROM VolontaringEntity a WHERE a.userId = :userId")
    List<VolontaringEntity> findByUserId(@Param("userId") Long userId);

}
