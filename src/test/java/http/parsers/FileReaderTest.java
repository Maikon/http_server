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
  public TemporaryFolder directory = new TemporaryFolder();

  @Test
  public void readsContentsOfFile() throws IOException {
    File file = directory.newFile("file.txt");
    FileWriter writer = new FileWriter(file);
    writer.write("Contents of File");
    writer.close();
    FileReader reader = new FileReader(file);
    byte[] contents = "Contents of File".getBytes();
    assertThat(reader.readFile(), is(contents));
  }

  @Test
  public void checksAFileThatDoesNotExist() {
    assertThat(FileReader.fileExists(directory.getRoot(), "some-file.txt"), is(false));
  }

  @Test
  public void checksAFileThatExists() throws IOException {
    directory.newFile("file.txt");
    assertThat(FileReader.fileExists(directory.getRoot(), "file.txt"), is(true));
  }
}
