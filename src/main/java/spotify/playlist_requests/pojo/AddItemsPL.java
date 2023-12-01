package spotify.playlist_requests.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"uris",
"position"
})

public class AddItemsPL {

@JsonProperty("uris")
private List<String> uris;
@JsonProperty("position")
private Integer position;

@JsonProperty("uris")
public List<String> getUris() {
return uris;
}

@JsonProperty("uris")
public void setUris(List<String> uris) {
this.uris = uris;
}

@JsonProperty("position")
public Integer getPosition() {
return position;
}

@JsonProperty("position")
public void setPosition(Integer position) {
this.position = position;
}

}