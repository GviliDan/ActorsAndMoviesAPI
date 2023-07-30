package serverside.course.MyProject.Actors;

import org.springframework.stereotype.Component;
import serverside.course.MyProject.Directors.Director;
import serverside.course.MyProject.Directors.DirectorController;
import serverside.course.MyProject.SimpleIdentifiableRepresentationModelAssembler;

@Component
public class ActorEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Actor> {

    public ActorEntityAssembler() {
        super(ActorController.class);
    }
}
