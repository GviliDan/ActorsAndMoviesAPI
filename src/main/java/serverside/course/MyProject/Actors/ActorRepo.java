package serverside.course.MyProject.Actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActorRepo extends JpaRepository<Actor,Long> {


    List<Actor> findByName(String name);

    List<Actor> findByNameAndNation(String name, String nation);

    List<Actor> findByNation(String nation);
}
