package org.stellar.anchor.util;

import static org.stellar.anchor.util.Log.debugF;
import static org.stellar.anchor.util.StringHelper.isEmpty;

import io.jsonwebtoken.lang.Strings;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class NetUtil {

  public static String fetch(String url) throws IOException {

    try {
      Request request = OkHttpUtil.buildGetRequest(url);
      Response response = getCall(request).execute();
      String message =
          String.format("Error fetching from URL: %s response: %s", url, response.toString());

      // Check if response was unsuccessful (ie not status code 2xx) and throw IOException
      if (!response.isSuccessful()) {
        debugF(message);
        throw new IOException(message);
      }

      if (response.body() == null) {
        debugF(message);
        throw new IOException(message);
      }

      return response.body().string();
    } catch (IOException e) {
      debugF(e.toString());
      throw e;
    }
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public static boolean isUrlValid(String url) {
    if (isEmpty(url)) {
      return false;
    }
    /* Try creating a valid URL */
    try {
      new URL(url).toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isServerPortValid(String serverPort, boolean hostnameLookup) {
    if (isEmpty(serverPort)) return false;
    String[] tokens = Strings.split(serverPort, ":");
    if (tokens == null) {
      return !hostnameLookup || isHostnameResolvable(serverPort);
    }
    switch (tokens.length) {
      case 2:
        String strPort = tokens[1];
        try {
          int port = Integer.parseInt(strPort);
          if (port > 65535 || port < 0) {
            return !hostnameLookup || isHostnameResolvable(serverPort);
          }
        } catch (NumberFormatException ex) {
          return false;
        }
      case 1:
        return !hostnameLookup || isHostnameResolvable(serverPort);
      case 0:
      default:
        return false;
    }
  }

  public static String getDomainFromURL(String strUri) throws MalformedURLException {
    URL uri = new URL(strUri);
    if (uri.getPort() < 0) {
      return uri.getHost();
    }
    return uri.getHost() + ":" + uri.getPort();
  }

  static boolean isHostnameResolvable(String hostname) {
    try {
      InetAddress.getAllByName(hostname);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }

  static Call getCall(Request request) {
    return OkHttpUtil.buildClient().newCall(request);
  }
}
