package http.filters;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticatorTest {

  @Test
  public void returnsFalseIfAuthenticationFails() {
    Authenticator authenticator = new Authenticator("username", "password");
    String encodedCredentials = encodeCredentials("invalid-user:invalid-password");
    assertThat(authenticator.validAuthentication(encodedCredentials), is(false));
  }

  @Test
  public void returnsTrueIfAuthenticationCredentialsMatch() {
    Authenticator authenticator = new Authenticator("username", "password");
    String encodedCredentials = encodeCredentials("username:password");
    assertThat(authenticator.validAuthentication(encodedCredentials), is(true));
  }

  private String encodeCredentials(String credentials) {
    byte[] encoded = Base64.encodeBase64(credentials.getBytes());
    return new String(encoded);
  }
}
