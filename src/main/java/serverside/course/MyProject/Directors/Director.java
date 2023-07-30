package serverside.course.MyProject.Directors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import serverside.course.MyProject.Movies.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private Long id;

    private String name;

    private String nation;

    private LocalDateTime birthDay;

    private Boolean isAlive;

    @JsonIgnore
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovie;

    public Director(Long id, String name, String nation) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        directedMovie= new ArrayList<>();
    }

    public void addMovieDirect(Movie movie){
        this.directedMovie.add(movie);
    }

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nation='" + nation + '\'' +
                ", birthDay=" + birthDay +
                ", isAlive=" + isAlive +
                ", directedMovie=" + directedMovie +
                '}';
    }

    public Director(Long id, String name, String nation, LocalDateTime birthDay, Boolean isAlive) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.birthDay = birthDay;
        this.isAlive = isAlive;
    }
}
