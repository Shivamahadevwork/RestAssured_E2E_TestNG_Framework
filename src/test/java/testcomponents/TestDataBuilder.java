package testcomponents;

import spotify.playlist_requests.pojo.CreatePlaylist_Request;
import spotify.playlist_requests.pojo.UpdatePlaylist_Request;

public class TestDataBuilder {
	
		public CreatePlaylist_Request testdataHPCreatePlaylist() {
		CreatePlaylist_Request preserializedrequest = new CreatePlaylist_Request();
		preserializedrequest.setName("Urdu Ghazals");
		preserializedrequest.setDescription("Urdu Ghazals of 2023");
		preserializedrequest.set_public(false);	
		return preserializedrequest;			
		}
		
		public CreatePlaylist_Request testdataEPCreatePlaylist() {
		CreatePlaylist_Request preserializedrequest = new CreatePlaylist_Request();
		preserializedrequest.setName("");
		preserializedrequest.setDescription("Urdu Ghazals of 2023");
		preserializedrequest.set_public(false);	
		return preserializedrequest;			
		}		
		
		public UpdatePlaylist_Request testdataHPUpdatePlaylist() {
		UpdatePlaylist_Request preserializedrequest = new UpdatePlaylist_Request();
		preserializedrequest.set_public(true);
		preserializedrequest.setName("Urdu Ghazals and Hits");
		preserializedrequest.setDescription("Urdu Ghazals and Hits of 2023");
		return preserializedrequest;			
		}
		
		
		

}
