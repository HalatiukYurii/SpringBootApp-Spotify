package pl.yurii.springbootspotifyapi.model;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import pl.yurii.springbootspotifyapi.SpotifyAlbumClient;

@Route
public class SearchGui extends VerticalLayout {

	public SearchGui(OAuth2Authentication oAuthentication, SpotifyAlbumClient spotifyAlbumClient) {
		
		spotifyAlbumClient.getAlbumsByAuthor(oAuthentication, "taylor swift");
	}
	
	
}
