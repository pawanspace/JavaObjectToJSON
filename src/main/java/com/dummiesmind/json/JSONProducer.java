package com.dummiesmind.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ognl.Ognl;
import ognl.OgnlException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dummiesmind.beans.Task;

public class JSONProducer {

	public JSONObject createJSONObjectForProperty(String expression, Object context) throws JSONException, OgnlException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(expression, evaluateExpressionUsingOGNL(expression, context));
		return jsonObject;
	}


	public List<JSONArray> createJSONArrayListForMultipleObjectsProperties(Map<Task, List<String>> testData) throws JSONException, OgnlException {
		List<JSONArray> response = new ArrayList<JSONArray>();

		for(Entry<Task, List<String>> entry : testData.entrySet()){
			JSONArray array = createJSONArrayForProperties(entry.getValue(), entry.getKey());
			response.add(array);
		}
		

		return response;
	}


	public JSONArray createJSONArrayForProperties(List<String> expressions, Object context) throws JSONException, OgnlException {
		JSONArray array = new JSONArray();
		
		for(String expression : expressions){
			array.put(createJSONObjectForProperty(expression, context));
		}
		
		return array;
	}	
	
	
	private Object evaluateExpressionUsingOGNL(String expression, Object root) throws OgnlException {
		return Ognl.getValue(expression, root);
	}
}
