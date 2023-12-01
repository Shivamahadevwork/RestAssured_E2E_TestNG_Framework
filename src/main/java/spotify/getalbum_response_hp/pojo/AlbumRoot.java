
package spotify.getalbum_response_hp.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "albums"
})

public class AlbumRoot {

    @JsonProperty("albums")
    private List<Album__1> albums;

    @JsonProperty("albums")
    public List<Album__1> getAlbums() {
        return albums;
    }

    @JsonProperty("albums")
    public void setAlbums(List<Album__1> albums) {
        this.albums = albums;
    }

}
