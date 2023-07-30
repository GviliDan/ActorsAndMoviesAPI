package serverside.course.MyProject.Actors;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import serverside.course.MyProject.Directors.Director;
import serverside.course.MyProject.Movies.Movie;

@Value
@JsonPropertyOrder({"id","name","nation","filmography","info"})
public class ActorDTO {

    private final Actor actor;

    public Long getId(){
        return this.actor.getId();
    }

    public String getName(){
        return this.actor.getName();
    }

    public String getNation(){
        return this.actor.getNation();
    }

    public String getFilmogeaphy(){
        StringBuilder s1 = new StringBuilder();
        Integer i=1;
        if (actor.getMovieList().isEmpty())
            return "no filmography details";
        else {
            for (Movie a: actor.getMovieList()) {
                s1.append(i).append(". ").append(a.getTitle()).append("\n");
                i++;
            }
            return  s1.toString();
        }
    }

    public String getInfo(){
        return "this is a TTO of the actor";
    }

}
