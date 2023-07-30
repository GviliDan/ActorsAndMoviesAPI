package serverside.course.MyProject.Movies;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Value;
import serverside.course.MyProject.Actors.Actor;

@Value
@JsonPropertyOrder({"id","title","genre","releaseDate","cast","director","info"})
public class MovieDTO {

    private final Movie movie;


    public Long getId(){
        return this.movie.getId();
    }

    public String getTitle(){
        return this.movie.getTitle();
    }

    public String getGenere(){
        return this.movie.getGenere();
    }

    public String getReleaseDate(){
        if (this.movie.getReleaseDate()!=null){
            return String.valueOf(this.movie.getReleaseDate().getYear());
        }
        else return "no release date info";
    }

    public String getCast(){
        StringBuilder s1 = new StringBuilder();
        Integer i=1;
        if (movie.getCast().isEmpty())
            return "no cast details";
        else {
            for (Actor a: movie.getCast()) {
                s1.append(i).append(". ").append(a.getName()).append("\n");
                i++;
            }
            return  s1.toString();
        }
    }

    public String getDirector(){
        if(movie.getDirector()== null)
            return "no director deatails";
        return movie.getDirector().getName();
    }

    public String getInfo(){
        return "this is a TTO of the movie";
    }



}
