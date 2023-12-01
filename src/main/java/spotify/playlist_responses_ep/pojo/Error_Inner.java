package spotify.playlist_responses_ep.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"status",
"message"
})
	
	public class Error_Inner {
	
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("status")
	public Integer getStatus() {
	return status;
	}
	
	@JsonProperty("status")
	public void setStatus(Integer status) {
	this.status = status;
	}
	
	@JsonProperty("message")
	public String getMessage() {
	return message;
	}
	
	@JsonProperty("message")
	public void setMessage(String message) {
	this.message = message;
	}

}