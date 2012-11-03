package ca.tjug.jsr353;

import java.util.Iterator;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

public class TestJson {

    public static void main(String[] args) {
        try {
            Content response = Request.Get("http://lcboapi.com/products")
                                      .execute()
                                      .returnContent();
            JsonParser parser = Json.createParser(response.asStream());

            Iterator<Event> iterator = parser.iterator();

            while(iterator.hasNext()) {
                Event event = iterator.next();
                if (event == Event.VALUE_STRING) {
                    System.out.println(parser.getString());
                }
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
