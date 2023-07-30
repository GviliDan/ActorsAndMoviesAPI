package serverside.course.MyProject.Movies;

import org.springframework.stereotype.Component;
import serverside.course.MyProject.SimpleIdentifiableRepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MovieEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Movie> {

    public MovieEntityAssembler() {
        super(MovieController.class);
    }


}
