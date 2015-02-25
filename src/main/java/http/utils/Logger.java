package http.utils;

import http.Request;

import java.util.ArrayList;

public class Logger {
  private static ArrayList<String> requests = new ArrayList<>();

  public static void log(Request request) {
    requests.add(request.getStatusLine());
  }

  public static ArrayList<String> loggedRequests() {
    return (ArrayList<String>) requests.clone();
  }

  public static void clear() {
    requests.clear();
  }
}
