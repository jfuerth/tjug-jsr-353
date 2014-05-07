package ca.tjug.jsr353;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class ObjectArrayGenerator {

  public static void main(String[] args) {
    JsonGenerator generator = Json.createGenerator(System.out);
    generator.writeStartArray();

    generator.writeStartObject();
    generator.write("meaningOfLife", BigDecimal.valueOf(42));
    generator.writeEnd();

    generator.writeStartObject();
    generator.write("secondArrayValue", "two");
    generator.writeEnd();

    generator.writeEnd();
    generator.close();
  }
}
