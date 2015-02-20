package http;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class TestHelper {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();
}
