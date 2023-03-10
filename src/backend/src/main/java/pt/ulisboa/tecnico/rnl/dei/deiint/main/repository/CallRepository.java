package pt.ulisboa.tecnico.rnl.dei.deiint.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.entity.Call;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CallRepository extends JpaRepository<Call, Long> {

}