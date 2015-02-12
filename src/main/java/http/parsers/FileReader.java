package http.parsers;

import java.io.*;

public class FileReader {
  private File file;

  public FileReader(File file) {
    this.file = file;
  }

  public String readFile() {
    String result = "";
    try {
      FileInputStream stream = new FileInputStream(file);
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      char[] buffer = new char[(int) file.length()];
      reader.read(buffer);
      for (char s : buffer) {
        result += s;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
