package serverside.course.MyProject.Directors;

import org.springframework.stereotype.Component;
import serverside.course.MyProject.Movies.Movie;
import serverside.course.MyProject.Movies.MovieController;
import serverside.course.MyProject.SimpleIdentifiableRepresentationModelAssembler;

@Component
public class DirectorEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Director> {

    public DirectorEntityAssembler() {
        super(DirectorController.class);
    }
}
