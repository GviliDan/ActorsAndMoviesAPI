package serverside.course.MyProject.Directors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DirectorRepo extends JpaRepository<Director,Long> {


    List<Director> findByName(String name);
}
