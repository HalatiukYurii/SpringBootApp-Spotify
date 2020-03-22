package pl.yurii.springbootspotifyapi;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pl.yurii.springbootspotifyapi.entity.Track;
import pl.yurii.springbootspotifyapi.model.Item;
import pl.yurii.springbootspotifyapi.model.SpotifyAlbum;
import pl.yurii.springbootspotifyapi.model.dto.SpotifyAlbumDto;
import pl.yurii.springbootspotifyapi.repository.TrackRepo;


@RestController
public class SpotifyAlbumClient {
	
	
	private TrackRepo trackRepo;
	
	


    public SpotifyAlbumClient(TrackRepo trackRepo) {
		this.trackRepo = trackRepo;
	}




	@GetMapping("/album/{authorName}")
    public SpotifyAlbum getAlbumsByAuthor(OAuth2Authentication details, @PathVariable String authorName) {
        String jwt = ((OAuth2AuthenticationDetails)details.getDetails()).getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity<Object>(httpHeaders);

        ResponseEntity<SpotifyAlbum> exchange = restTemplate.exchange("https://api.spotify.com/v1/search?q="+ authorName +"&type=track&market=US&limit=10&offset=5",
                HttpMethod.GET,
                httpEntity,
                SpotifyAlbum.class);
        
                System.out.println(exchange.getBody().getTracks().getItems().get(0).getId());

        List<SpotifyAlbumDto> spotifyAlbumDtos = 
        		exchange.getBody().getTracks()
        		.getItems().stream()
        		.map(item-> new SpotifyAlbumDto(item.getName(), item.getAlbum().getImages().get(0).getUrl()))
        		.collect(Collectors.toList());
        
        return exchange.getBody();
    }
	
	@PostMapping("/add-track")
    public void addTrack(@RequestBody Track track) {
		trackRepo.save(track);
		
	}
}
