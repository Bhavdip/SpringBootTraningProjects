package training.spring.boot.mobileapp.api.albums.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import training.spring.boot.mobileapp.api.albums.data.AlbumEntity;

@Service
public class AlbumServiceImp implements AlbumService {

	@Override
	public List<AlbumEntity> getAlbumsById(String userId) {
		 List<AlbumEntity> returnValue = new ArrayList<>();
		 
		 AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setUserId(userId);
        albumEntity.setAlbumId("album1Id");
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(1L);
        albumEntity.setName("album 1 name");
        
        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId("album2Id");
        albumEntity2.setDescription("album 2 description");
        albumEntity2.setId(2L);
        albumEntity2.setName("album 2 name");
        
        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        
		return returnValue;
	}

}
