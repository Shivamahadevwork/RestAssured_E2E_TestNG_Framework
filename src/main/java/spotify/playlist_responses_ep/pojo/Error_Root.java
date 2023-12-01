package spotify.playlist_responses_ep.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"error"
})

public class Error_Root {

@JsonProperty("error")
private Error_Inner error;

@JsonProperty("error")
public Error_Inner getError() {
return error;
}

@JsonProperty("error")
public void setError(Error_Inner error) {
this.error = error;
}

}