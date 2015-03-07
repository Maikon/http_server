package http.filesystem;

import http.Request;
import http.utils.Logger;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;

public class FileIO {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(FileIO.class));
    private final File directory;

    public FileIO(File directory) {
        this.directory = directory;
    }

    public File[] getDirectoryFiles() {
        return directory.listFiles();
    }

    public String getFileContents(Request request) {
        Optional<File> file = findFile(request);
        if (file.isPresent()) {
            return getFileContents(file.get());
        }
        return "";
    }

    public Optional<File> findFile(Request request) {
        return fileThatMatchesURI(request);
    }

    public String getFileContents(File file) {
        byte[] content = getContent(file);
        return new String(content);
    }

    public byte[] getFileBytes(File file) {
        return getContent(file);
    }

    public boolean fileExists(Request request) {
        return fileThatMatchesURI(request).isPresent();
    }

    public boolean fileExists(String fileName) {
        return new File(directory.getAbsolutePath() + fileName).exists();
    }

    public File createFile(String uri) {
        File file = new File(directory.getAbsolutePath() + uri);
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.logError(e);
        }
        return file;
    }

    public void writeToFile(File file, String content) {
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.write(content);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            logger.logError(e);
        }
    }

    public byte[] getRange(int from, int to, File file) {
        byte[] content = getContent(file);
        int maxLength = content.length;
        int start = getStartIndex(from, to, maxLength);
        int end = getEndIndex(from, to, maxLength);
        return getRange(content, start, end);
    }

    private int getStartIndex(int startingPoint, int to, int max) {
        if (startsAtTheEnd(startingPoint)) {
            return max - to;
        }
        return startingPoint;
    }

    private boolean startsAtTheEnd(int startingPoint) {
        return startingPoint == -1;
    }

    private int getEndIndex(int from, int to, int max) {
        if (boundsArePositive(from, to)) {
            return to + 1;
        }
        return max;
    }

    private boolean boundsArePositive(int from, int to) {
        return from > -1 && to > -1;
    }

    private byte[] getRange(byte[] content, int startingIndex, int endIndex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = startingIndex; i < endIndex; i++) {
            out.write(content[i]);
        }
        return out.toByteArray();
    }

    private byte[] getContent(File file) {
        byte[] content = new byte[0];
        try {
            content = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.logError(e);
        }
        return content;
    }

    private Optional<File> fileThatMatchesURI(Request request) {
        String uri = request.getUri().substring(1);
        return Stream.of(getDirectoryFiles()).filter(f -> f.getName().equals(uri))
          .findFirst();
    }
}
