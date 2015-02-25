package http.filters;

import org.apache.commons.codec.binary.Base64;

public class Authenticator {
  private final String username;
  private final String password;

  public Authenticator(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean validAuthentication(String encodedCredentials) {
    String[] decodedCredentials = decode(encodedCredentials);
    String requestUsername = decodedCredentials[0];
    String requestPassword = decodedCredentials[1];
    return username.equals(requestUsername) && password.equals(requestPassword);
  }

  private String[] decode(String encodedCredentials) {
    byte[] decoded = Base64.decodeBase64(encodedCredentials);
    return new String(decoded).split(":");
  }
}
