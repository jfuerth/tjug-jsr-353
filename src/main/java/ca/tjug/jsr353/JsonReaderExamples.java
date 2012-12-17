package ca.tjug.jsr353;

import java.net.URL;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonReaderExamples {

  public static void main(String[] args) throws Exception {
    URL jsonResource = JsonReaderExamples.class.getResource("/lcbo_products.json");
    JsonReader jsonReader = null;
    try {
      jsonReader = new JsonReader(jsonResource.openStream());
      JsonObject root = jsonReader.readObject();

      printLagers(root);
      printSumOfLagerInventory(root);
    } finally {
      if (jsonReader != null) {
        jsonReader.close();
      }
    }
  }

  /** Sample method that searches for and prints all Lager beers. */
  private static void printLagers(JsonObject root) {
    JsonArray result = root.getValue("result", JsonArray.class);
    for (JsonValue itemAsValue : result) {
      JsonObject item = (JsonObject) itemAsValue;
      String primaryCat = item.getStringValue("primary_category");
      String secondaryCat = item.getStringValue("secondary_category");

      if ("Beer".equals(primaryCat) && "Lager".equals(secondaryCat)) {
        System.out.println(item);
      }
    }
  }

  private static void printSumOfLagerInventory(JsonObject root) {
    JsonArray result = root.getValue("result", JsonArray.class);

    int totalUnits = 0;
    long totalMillilitres = 0;

    for (JsonValue itemAsValue : result) {
      JsonObject item = (JsonObject) itemAsValue;
      String primaryCat = item.getStringValue("primary_category");
      String secondaryCat = item.getStringValue("secondary_category");

      if ("Beer".equals(primaryCat) && "Lager".equals(secondaryCat)) {
        totalUnits += item.getIntValue("inventory_count");
        totalMillilitres += item.getValue("inventory_volume_in_milliliters", JsonNumber.class).getLongValue();
      }
    }

    // this is the "dom" style of writing JSON, by building an object and printing it
    JsonObject summary = new JsonObjectBuilder()
      .add("total_units_of_lager", totalUnits)
      .add("total_volume_of_lager", totalMillilitres)
      .build();
    System.out.println(summary);
  }

}
