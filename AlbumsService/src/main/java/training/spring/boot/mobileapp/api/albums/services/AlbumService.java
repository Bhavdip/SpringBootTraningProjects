package training.spring.boot.mobileapp.api.albums.services;

import java.util.List;

import training.spring.boot.mobileapp.api.albums.data.AlbumEntity;

public interface AlbumService {

	public List<AlbumEntity> getAlbumsById(String userId);
}
