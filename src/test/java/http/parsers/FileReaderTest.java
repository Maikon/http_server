package http.parsers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileReaderTest {

  @Rule
  public TemporaryFolder root = new TemporaryFolder();

  @Test
  public void readsContentsOfFile() throws IOException {
    File file = root.newFile("file.txt");
    FileWriter writer = new FileWriter(file);
    writer.write("Contents of File");
    writer.close();
    FileReader fileReader = new FileReader(file);
    assertThat(fileReader.readFile(), is("Contents of File"));
  }
}
