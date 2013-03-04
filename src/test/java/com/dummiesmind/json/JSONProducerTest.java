package com.dummiesmind.json;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
		JSONObject results = jsonProducer.createJSONObjectForProperty("description", new Task(null,
				null, null));

		assertThat(results, instanceOf(JSONObject.class));
	}

	@Test
	public void createsJSONObjectForProperty_GivenTask_ReturnsJSONObject_WithPropertyNameAsKeyAndPropertyValueAsValue()
			throws Exception {
		Task task = new Task(null, null, "My first task");

		JSONObject results = jsonProducer.createJSONObjectForProperty("description", task);

		assertThat(results.getString("description"), is("My first task"));
	}



	@Test
	public void createsJSONObjectForProperty_GivenNote_ReturnsJSONObject_WithPropertyNameAsKeyAndPropertyValueAsValue()
			throws Exception {
		Note note = new Note("My first note", null);

		JSONObject results = jsonProducer.createJSONObjectForProperty("text", note);

		assertThat(results.getString("text"), is("My first note"));
	}

	@Test
	public void createsJSONObjectForProperty_GivenNote_ReturnsJSONObject_WithDeepRelation()
			throws Exception {
		Note note = new Note("My first note", new Owner("Pawan", "pawanspace@gmail.com"));

		JSONObject results = jsonProducer.createJSONObjectForProperty("writer.name", note);

		assertThat(results.getString("writer.name"), is("Pawan"));
	}

	
	@Test
	public void createsListOfJSONArrayReturns_MultipleValuesMappedInArray_ForMultipleObjects() throws Exception {
		Map<Task, List<String>> testData = createMapForTasksTestData();
		
		List<JSONArray> results = jsonProducer.createJSONArrayListForMultipleObjectsProperties(testData);
	
		assertThat(results.size(), is(3));
	}
	
	@Test
	public void createsJSONArrayReturns_MultiplePropertiesForOneObjectInJSONArray() throws Exception {
		Owner writer = new Owner("Pawan", "pawanspace@gmail.com");
		Note note = new Note("My first note", writer);
		Task task = new Task(writer, asList(note), "Task 1");

		JSONArray array = jsonProducer.createJSONArrayForProperties(asList("description", "notes", "assignee.name", "assignee.email"), task);
		
		assertThat(array.getJSONObject(0).getString("description"), is("Task 1"));
		assertThat(array.getJSONObject(1).get("notes"), instanceOf(List.class));
		assertThat(array.getJSONObject(2).getString("assignee.name"), is("Pawan"));
		assertThat(array.getJSONObject(3).getString("assignee.email"), is("pawanspace@gmail.com"));
	}
	
	
	private Map<Task, List<String>> createMapForTasksTestData(){
		Map<Task, List<String>> testData = new HashMap<Task, List<String>>();
		Owner writer1 = new Owner("Pawan", "pawanspace@gmail.com");
		Owner writer2 = new Owner("Varun", "varunspace@gmail.com");
		
		Note note1 = new Note("My first note", writer1);
		Note note2 = new Note("My second note", writer2);
		
		Task task1 = new Task(writer1, asList(note1), "Task 1");
		Task task2 = new Task(writer2, asList(note1, note2), "Task 2");
		Task task3 = new Task(writer1, null, "Task 3");
		
		testData.put(task1, asList("description", "notes"));
		testData.put(task2, asList("description", "notes", "assignee.name"));
		testData.put(task3, asList("description", "assignee.name"));
		
		
		return testData;
	}
	
}
