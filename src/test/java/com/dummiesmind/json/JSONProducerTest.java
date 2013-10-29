package com.dummiesmind.json;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.dummiesmind.beans.Note;
import com.dummiesmind.beans.Owner;
import com.dummiesmind.beans.Task;

public class JSONProducerTest {

	private JSONProducer jsonProducer;

	@Before
	public void setup() {
		jsonProducer = new JSONProducer();
	}

	@Test
	public void createsJSONObjectForProperty_ReturnsJSONObject() throws Exception {
		JSONObject results = jsonProducer.createJSONObjectForProperty(asList("description"), new Task(null,
				null, null));

		assertThat(results, instanceOf(JSONObject.class));
	}


	@Test
	public void createsJSONObjectForProperty_GivenTask_ReturnsJSONObject_WithPropertyNameAsKeyAndPropertyValueAsValue()
			throws Exception {
		Task task = new Task(null, null, "My first task");

		JSONObject results = jsonProducer.createJSONObjectForProperty(asList("description"), task);

		assertThat(results.getString("description"), is("My first task"));
	}



	@Test
	public void createsJSONObjectForProperty_GivenNote_ReturnsJSONObject_WithDeepRelation_usingOGNL	()
			throws Exception {
		Note note = new Note("My first note", new Owner("Pawan", "pawan@gmail.com"));

		JSONObject results = jsonProducer.createJSONObjectForProperty(asList("writer.name"), note);

		assertThat(results.getString("writer.name"), is("Pawan"));
	}

	
	
	@Test
	@SuppressWarnings("unchecked")
	public void createsListOfJSONArrayReturns_MultipleValuesMappedInArray_ForMultipleObjects() throws Exception {
		Map<Task, List<String>> testData = createMapForTasksTestData();
		
		JSONArray results = jsonProducer.createJSONArrayForMultipleObjects(testData);
	
		assertThat(results.length(), is(3));
		JSONObject jsonObject = getTask2(results);
		
		assertThat(jsonObject.getString("description"), is("Task 2"));
		assertThat(jsonObject.get("notes"), instanceOf(List.class));
		assertThat(((List<Note>)jsonObject.get("notes")).get(0).getText(), is("My first note"));
		assertThat(jsonObject.getString("assignee.name"), is("Varun"));
		assertThat(jsonObject.getString("assignee.email"), is("varun@gmail.com"));

	}

	private JSONObject getTask2(JSONArray results) throws JSONException {
		JSONObject jsonObject = null;
			
		for (int i = 0; i < results.length(); i++) {
			JSONObject obj = (JSONObject)results.get(i);
			if(obj.getString("description").equals("Task 2")){
				jsonObject = obj;
			}
		}
		return jsonObject;
	}
	
	
	private Map<Task, List<String>> createMapForTasksTestData(){
		Map<Task, List<String>> testData = new HashMap<Task, List<String>>();
		Owner writer1 = new Owner("Pawan", "pawan@gmail.com");
		Owner writer2 = new Owner("Varun", "varun@gmail.com");
		
		Note note1 = new Note("My first note", writer1);
		Note note2 = new Note("My second note", writer2);
		
		Task task1 = new Task(writer1, asList(note1), "Task 1");
		Task task2 = new Task(writer2, asList(note1, note2), "Task 2");
		Task task3 = new Task(writer1, null, "Task 3");
		
		testData.put(task1, asList("description", "notes"));
		testData.put(task2, asList("description", "notes", "assignee.name", "assignee.email"));
		testData.put(task3, asList("description", "assignee.name"));
		
		
		return testData;
	}
	
}
