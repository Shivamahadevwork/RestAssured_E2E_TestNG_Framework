package spotify.playlist_requests.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePlaylist_Request {
	
		@JsonProperty("name")
		private String name;
		@JsonProperty("description")
		private String description;
		@JsonProperty("public")
		private Boolean _public;	
	

}
