package http.filesystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

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
    FileSystem fs = createFileSystem();
    File root = directory.getRoot();
    assertThat(fs.allFiles(root), is(asList("file1.txt", "file2.txt", "file3.txt")));
  }

  @Test
  public void canCreateAFile() throws FileAlreadyExistsException {
    FileSystem fs = createFileSystem();
    File root = directory.getRoot();
    fs.createFile(root, "file1.txt");
    assertThat(fs.allFiles(root), is(asList("file1.txt")));
  }

  @Test(expected = FileAlreadyExistsException.class)
  public void throwsExceptionIfFileAlreadyExists() throws FileAlreadyExistsException {
    FileSystem fs = createFileSystem();
    File root = directory.getRoot();
    fs.createFile(root, "file1.txt");
    fs.createFile(root, "file1.txt");
  }

  @Test
  public void canDeleteAFile() throws IOException {
    FileSystem fs = createFileSystem();
    File root = directory.getRoot();
    fs.createFile(root, "file1.txt");
    fs.deleteFile(root, "file1.txt");
    assertThat(fs.allFiles(root), is(asList()));
  }

  @Test
  public void checksIfAFileExistsInTheSystem() throws IOException {
    directory.newFile("file.txt");
    FileSystem fs = createFileSystem();
    File root = directory.getRoot();
    assertThat(fs.fileExists(root, "file.txt"), is(true));
    assertThat(fs.fileExists(root, "some-other-file.txt"), is(false));
  }

  private FileSystem createFileSystem() {
    return new FileSystem();
  }
}
