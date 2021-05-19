package training.spring.boot.mobileapp.api.albums.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import training.spring.boot.mobileapp.api.albums.data.AlbumEntity;
import training.spring.boot.mobileapp.api.albums.model.AlbumResponseModel;
import training.spring.boot.mobileapp.api.albums.services.AlbumService;

@RestController
@RequestMapping("/users/{userId}/albums")
public class AlbumsController {

	@Autowired
	AlbumService mAlbumService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<AlbumResponseModel> userAlbums(@PathVariable String userId) {
		List<AlbumResponseModel> resultDataSet = new ArrayList<AlbumResponseModel>();
		
		// request for Albums by userId
		List<AlbumEntity> albumsEntities = mAlbumService.getAlbumsById(userId);
		
		if (albumsEntities == null || albumsEntities.isEmpty()) {
			return resultDataSet;
		}
		
		//define AlbumResponseModel list type
		Type listType = new TypeToken<List<AlbumResponseModel>>() {}.getType();
		//mapping from album entity list  to List type
		resultDataSet = new ModelMapper().map(albumsEntities, listType);
		
		return resultDataSet;
	}
}
