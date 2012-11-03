package ca.tjug.jsr353;

import java.net.URL;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class TestJson {

  public static void main(String[] args) throws Exception {
    URL jsonResource = TestJson.class.getResource("/lcbo_products.json");
    JsonReader jsonReader = new JsonReader(jsonResource.openStream());
    JsonObject root = jsonReader.readObject();
    printLagers(root);
    jsonReader.close();
  }

  private static void printLagers(JsonObject root) {
    JsonArray result = root.getValue("result", JsonArray.class);
    for (JsonValue itemAsValue : result.getValues()) {
      JsonObject item = (JsonObject) itemAsValue;
      String primaryCat = item.getStringValue("primary_category");
      String secondaryCat = item.getStringValue("secondary_category");

      if ("Beer".equals(primaryCat) && "Lager".equals(secondaryCat)) {
        System.out.println(item);
      }
    }
  }
}
