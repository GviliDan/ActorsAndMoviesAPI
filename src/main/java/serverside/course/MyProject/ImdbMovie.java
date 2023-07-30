package serverside.course.MyProject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbMovie {

    @JsonProperty("id")
    private String id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("title")
    private String title;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("rating")
    private double rating;


    @JsonProperty("year")
    private int year;

    @JsonProperty("image")
    private String image;

    @JsonProperty("description")
    private String description;

    @JsonProperty("trailer")
    private String trailer;


    @Override
    public String toString() {
        return "ImdbMovie{" +
                "id=" + id +
                ", rank=" + rank +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", trailer='" + trailer + '\'' +
                '}';
    }
}
