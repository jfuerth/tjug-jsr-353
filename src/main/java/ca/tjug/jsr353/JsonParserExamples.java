package ca.tjug.jsr353;

import java.net.URL;

import javax.json.Json;
import javax.json.stream.JsonParser;

public class JsonParserExamples {

  public static void main(String[] args) throws Exception {
    URL jsonResource = JsonReaderExamples.class.getResource("/lcbo_products.json");
    JsonParser parser = null;
    try {
      parser = Json.createParser(jsonResource.openStream());
      printLagers(parser);
    } finally {
      if (parser != null) {
        parser.close();
        parser = null;
      }
    }
    try {
      parser = Json.createParser(jsonResource.openStream());
      printSumOfLagerInventory(parser);
    } finally {
      if (parser != null) {
        parser.close();
      }
    }

  }

  private static void printLagers(JsonParser parser) {
    String primaryCategory = null;
    String secondaryCategory = null;
    String name = null;
    while (parser.hasNext()) {
      switch (parser.next()) {
      case KEY_NAME:
        String key = parser.getString();
        parser.next();
        if ("primary_category".equals(key)) {
          primaryCategory = parser.getString();
        }
        else if ("secondary_category".equals(key)) {
          secondaryCategory = parser.getString();
        }
        else if ("name".equals(key)) {
          name = parser.getString();
        }
        break;

      case END_OBJECT:
        if ("Beer".equals(primaryCategory) && "Lager".equals(secondaryCategory)) {
          System.out.println(name);
        }
        name = null;
        primaryCategory = null;
        secondaryCategory = null;
        break;

      default:
        break;
      }
    }
  }

  private static void printSumOfLagerInventory(JsonParser parser) {
    String primaryCategory = null;
    String secondaryCategory = null;
    int units = 0;
    long volume = 0;

    int totalLagerUnits = 0;
    long totalLagerVolume = 0;

    while (parser.hasNext()) {
      switch (parser.next()) {
      case KEY_NAME:
        String key = parser.getString();
        parser.next();
        if ("primary_category".equals(key)) {
          primaryCategory = parser.getString();
        }
        else if ("secondary_category".equals(key)) {
          secondaryCategory = parser.getString();
        }
        else if ("inventory_count".equals(key)) {
          units = parser.getIntValue();
        }
        else if ("inventory_volume_in_milliliters".equals(key)) {
          volume = parser.getLongValue();
        }
        break;

      case END_OBJECT:
        if ("Beer".equals(primaryCategory) && "Lager".equals(secondaryCategory)) {
          totalLagerUnits += units;
          totalLagerVolume += volume;
        }
        primaryCategory = null;
        secondaryCategory = null;
        break;

      default:
        break;
      }
    }
    System.out.printf(" Total units of lager beer: %12d\n", totalLagerUnits);
    System.out.printf("Total volume of lager beer: %12d mL\n", totalLagerVolume);
  }


}
