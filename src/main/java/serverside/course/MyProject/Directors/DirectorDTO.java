package serverside.course.MyProject.Directors;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import serverside.course.MyProject.Movies.Movie;

@Value
@JsonPropertyOrder({"id","name","nation","movielist","info"})
public class DirectorDTO {

    private final Director director;

    public Long getId(){
        return this.director.getId();
    }

    public String getName(){
        return this.director.getName();
    }

    public String getNation(){
        return this.director.getNation();
    }


    public String getMovieList(){
        StringBuilder s1 = new StringBuilder();
        Integer i=1;
        if (director.getDirectedMovie().isEmpty())
            return "no filmography details";
        else {
            for (Movie a: director.getDirectedMovie()) {
                s1.append(i).append(". ").append(a.getTitle()).append("\n");
                i++;
            }
            return  s1.toString();
        }
    }

    public String getInfo(){
        return "this is a TTO of the director";
    }

}
