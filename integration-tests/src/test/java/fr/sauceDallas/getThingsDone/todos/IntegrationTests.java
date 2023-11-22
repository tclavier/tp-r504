package fr.sauceDallas.getThingsDone.todos;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {
    private static void assertTodoEquals(JSONObject readResponse, String titre, String description) {
        assertEquals(titre, readResponse.getString("title"));
        assertEquals(description, readResponse.getString("description"));
        JSONArray dueDateTimeExpected = new JSONArray(new int[]{1992, 3, 7, 20, 26, 40});
        JSONArray dueDateTimeActual = readResponse.getJSONArray("dueDateTime");
        assertArrayEquals(dueDateTimeExpected.toList().toArray(), dueDateTimeActual.toList().toArray());
    }

    private static int createSampleTodo(String titre, String description) throws IOException {
        JSONObject entity = new JSONObject();
        entity.put("title", titre);
        entity.put("description", description);
        entity.put("dueDate", 700000000000L);
        JSONObject postResponse = null;
        try {
            postResponse =
                    new JSONObject(
                            Request.post(getBaseURL() + "/todos/")
                                    .bodyString(entity.toString(), ContentType.APPLICATION_JSON)
                                    .execute()
                                    .returnContent()
                                    .asString());
        } catch (IOException e) {
            throw new RuntimeException("Err with base URL: " + getBaseURL(), e);
        }
        int id = postResponse.getInt("id");
        return id;
    }

    private static String getBaseURL() {
        String baseUrl = System.getenv("BASE_URL");
        if (baseUrl == null) return "http://localhost:8080";
        return baseUrl;
    }

    @Test
    void should_add_and_read_todo() throws IOException {
        int id = createSampleTodo("Titre", "Description to add");

        JSONObject readResponse =
                new JSONObject(
                        Request.get(getBaseURL() + "/todos/" + id).execute().returnContent().asString());

        assertTodoEquals(readResponse, "Titre", "Description to add");
    }

    @Test
    void should_add_and_delete_todo() throws IOException {
        int id = createSampleTodo("To Delete", "Description to delete");
        Request.delete(getBaseURL() + "/todos/" + id).execute();
        assertThrows(
                HttpResponseException.class,
                () -> {
                    Request.get(getBaseURL() + "/todos/" + id).execute().returnContent();
                });
    }
}
