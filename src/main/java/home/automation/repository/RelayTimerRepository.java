package home.automation.repository;

import home.automation.timer.entity.RelayTimer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayTimerRepository  extends JpaRepository<RelayTimer, Integer> {
    RelayTimer findByName(String isbn);
}