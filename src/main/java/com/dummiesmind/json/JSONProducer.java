package com.dummiesmind.json;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ognl.Ognl;
import ognl.OgnlException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONProducer {

	public JSONObject createJSONObjectForProperty(List<String> expressions,
			Object context) throws JSONException, OgnlException {
		JSONObject jsonObject = new JSONObject();

		for (String expression : expressions) {

			Object value = evaluateExpressionUsingOGNL(expression, context);
			if (value != null) {
				jsonObject.put(expression, value);
			}
		}
		return jsonObject;
	}

	public <T extends Object> JSONArray createJSONArrayForMultipleObjects(
			Map<T, List<String>> testData) throws JSONException, OgnlException {
		JSONArray response = new JSONArray();

		for (Entry<T, List<String>> entry : testData.entrySet()) {
			JSONObject object = createJSONObjectForProperty(entry.getValue(),
					entry.getKey());
			if (object != null) {
				response.put(object);
			}
		}

		return response;
	}

	private Object evaluateExpressionUsingOGNL(String expression, Object root)
			throws OgnlException {
		return Ognl.getValue(expression, root);
	}
}
