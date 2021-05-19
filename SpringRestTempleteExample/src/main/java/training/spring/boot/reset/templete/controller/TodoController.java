package training.spring.boot.reset.templete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import training.spring.boot.reset.templete.model.TodoModel;
import training.spring.boot.reset.templete.model.TodoModelRequestBody;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
	
	@Autowired
	private RestTemplate mRestTemplate;
	
	@Value("${todos.endpoint.hostname}")
	private String mTodoEndPointURL;

	/**
	 * Fetch all Todo's list from given URL with help of RestTempelete of spring boot
	 * 
	 * https://jsonplaceholder.typicode.com/todos
	 * 
	 * @return List<TodoModel>
	 */
	@GetMapping
	public List<TodoModel> getTodos() {
	 	ResponseEntity<List<TodoModel>> response = mRestTemplate.exchange(mTodoEndPointURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoModel>>() {});
	 	List<TodoModel> resultTodoList = response.getBody();
	 	return resultTodoList;
	}
	
	/**
	 * RestTemplate HTTP POST Request
	 * 
	 * @return TodoModel
	 */
	@PostMapping
	public TodoModel createATodo(@RequestBody TodoModelRequestBody requestBody) {
		
		//create HttpEntity to prepare Request body
		HttpEntity<TodoModelRequestBody> requestEntity = new HttpEntity<TodoModelRequestBody>(requestBody);
		
		//Use Response as class Type
		ResponseEntity<TodoModel> reponseModel = mRestTemplate.exchange(mTodoEndPointURL, HttpMethod.POST, requestEntity, TodoModel.class);
		
		return reponseModel.getBody();
	}
	
	@PutMapping("/{id}")
	public TodoModel updateTodo(@PathVariable String id, @RequestBody TodoModelRequestBody requestBody) {
		//create HttpEntity to prepare Request body
		HttpEntity<TodoModelRequestBody> requestEntity = new HttpEntity<TodoModelRequestBody>(requestBody);
		
		ResponseEntity<TodoModel> reponseModel = mRestTemplate.exchange(mTodoEndPointURL+"/"+id, HttpMethod.PUT, requestEntity, TodoModel.class);
		
		return reponseModel.getBody();
	}
	
	@DeleteMapping("/{id}")
	public String removeTodo(@PathVariable String todoId) {
		mRestTemplate.delete(mTodoEndPointURL + "/" + todoId);
		return "Todo id " + todoId + "has been deleted";
	}
}
