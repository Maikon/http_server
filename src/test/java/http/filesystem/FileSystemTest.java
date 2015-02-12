package http.filesystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileSystemTest {

  @Rule
  public TemporaryFolder directory = new TemporaryFolder();

  @Test
  public void itLoadsAllTheFilesFromTheDirectory() throws IOException {
    directory.newFile("file1.txt");
    directory.newFile("file2.txt");
    directory.newFile("file3.txt");
    FileSystem fs = new FileSystem();
    File root = directory.getRoot();
    assertThat(fs.allFiles(root), is(asList("file1.txt", "file2.txt", "file3.txt")));
  }

  @Test
  public void canCreateAFile() {
    FileSystem fs = new FileSystem();
    File root = directory.getRoot();
    fs.createFile(root, "file1.txt");
    assertThat(fs.allFiles(root), is(asList("file1.txt")));
  }
}
