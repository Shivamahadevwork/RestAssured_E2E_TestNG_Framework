package spotify.playlist_requests.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlaylist_Request {
	
		@JsonProperty("name")
		private String name;
		@JsonProperty("description")
		private String description;
		@JsonProperty("public")
		private Boolean _public;	
		
		@JsonProperty("name")
		public String getName() {
			return name;
		}
		@JsonProperty("name")
		public void setName(String name) {
			this.name = name;
		}
		@JsonProperty("description")
		public String getDescription() {
			return description;
		}
		@JsonProperty("description")
		public void setDescription(String description) {
			this.description = description;
		}
		@JsonProperty("public")
		public Boolean get_public() {
			return _public;
		}
		@JsonProperty("public")
		public void set_public(Boolean _public) {
			this._public = _public;
		}
	
	
	
	

}
