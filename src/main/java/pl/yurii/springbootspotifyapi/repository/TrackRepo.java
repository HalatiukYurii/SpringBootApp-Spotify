package pl.yurii.springbootspotifyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.yurii.springbootspotifyapi.entity.Track;

@Repository
public interface TrackRepo extends MongoRepository<Track,String> {

}
