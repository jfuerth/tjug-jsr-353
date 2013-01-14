package ca.tjug.jsr353;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class NullTestExample {

  static class TweetCoordinatePoint {
    private double lat;
    private double lon;
    public TweetCoordinatePoint(double lat, double lon) {
      this.lat = lat;
      this.lon = lon;
    }
    public double getLat() {
      return lat;
    }
    public double getLon() {
      return lon;
    }
  }

  public static void main(String[] args) throws Exception {
    URL jsonResource = JsonReaderExamples.class.getResource("/twitter_timeline.json");
    JsonReader jsonReader = null;
    try {
      jsonReader = new JsonReader(jsonResource.openStream());
      JsonArray tweets = jsonReader.readArray();

      System.out.println("Tweet coordinates:");
      System.out.println(extractTweetCoordinates(tweets));
    } finally {
      if (jsonReader != null) {
        jsonReader.close();
      }
    }
  }

  private static List<TweetCoordinatePoint> extractTweetCoordinates(JsonArray tweets) {
    List<TweetCoordinatePoint> coordList = new ArrayList<TweetCoordinatePoint>();
    for (JsonValue tweetValue : tweets) {
      JsonObject tweet = (JsonObject) tweetValue;
      if (tweet.get("coordinates") != JsonValue.NULL) {
        JsonObject coordObject = tweet.getValue("coordinates", JsonObject.class);
        if ("Point".equals(coordObject.getStringValue("type"))) {
          JsonArray coordArray = coordObject.getValue("coordinates", JsonArray.class);
          coordList.add(new TweetCoordinatePoint(
                  ((JsonNumber) coordArray.get(0)).getDoubleValue(),
                  ((JsonNumber) coordArray.get(1)).getDoubleValue()));
        }
      } else {
        coordList.add(null);
      }
    }
    return coordList;
  }
}
