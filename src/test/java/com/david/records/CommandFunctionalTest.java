package com.david.records;

import static org.hamcrest.CoreMatchers.is;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import static org.hamcrest.Matchers.contains;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

public class CommandFunctionalTest {

  private static final String PIPE_DELIMITED_FILE = "src/test/resources/pipe-delimited-records.txt";
  private static final String COMMA_DELIMITED_FILE = "src/test/resources/comma-delimited-records.txt";
  private static final String SPACE_DELIMITED_FILE = "src/test/resources/space-delimited-records.txt";

  @Test
  public void testCommandNoArgs() throws IOException {
    testCommand("target/no-args-actual.log", "src/test/resources/no-args-expected.log",  new String[]{});
  }

  @Test
  public void testCommandWhenIngestFileDoesntExist() throws IOException {
    testCommand("target/ingest-file-doesnt-exist-actual.log", "src/test/resources/ingest-file-doesnt-exist-expected.log", new String[]{"thisfileisbad.txt"});
  }

  @Test
  public void testCommandIngestingPipeDelimitedFile() throws IOException {
    testCommand("target/ingest-pipe-delimited-actual.log", "src/test/resources/ingest-pipe-delimited-expected.log", new String[]{PIPE_DELIMITED_FILE});
  }

  @Test
  public void testCommandIngestingCommaDelimitedFile() throws IOException {
    testCommand("target/ingest-comma-delimited-actual.log", "src/test/resources/ingest-comma-delimited-expected.log", new String[]{COMMA_DELIMITED_FILE});
  }

  @Test
  public void testCommandIngestingSpaceDelimitedFile() throws IOException {
    testCommand("target/ingest-space-delimited-actual.log", "src/test/resources/ingest-space-delimited-expected.log", new String[]{SPACE_DELIMITED_FILE});
  }

  @Test
  public void testCommandIngestingMultipleFiles() throws IOException {
    String[] args = new String[]{PIPE_DELIMITED_FILE, COMMA_DELIMITED_FILE, SPACE_DELIMITED_FILE};
    testCommand("target/ingest-multi-file-actual.log", "src/test/resources/ingest-multi-file-expected.log", args);
  }

  private void testCommand(String outputFile, String expectedFile, String[] args) throws IOException {
    // Reroute stdout to a log file so we can compare the output later
    System.setOut(new PrintStream(new File(outputFile)));
    RecordsCommand.main(args);

    List<String> expectedLines = readFile(expectedFile);
    List<String> actualLines = readFile(outputFile);
    compareOutput(expectedLines, actualLines);
  }

  private List<String> readFile(String filename) throws IOException {
    Path expectedPath = Paths.get(filename);
    return Files.readAllLines(expectedPath);
  }

  private void compareOutput(List<String> expectedLines, List<String> actualLines) {
    // To provide better debugging info, we iterate through the lines of the
    // actual and expected output even if they aren't the same size
    int limit = Math.min(actualLines.size(), expectedLines.size());
    int i;
    for (i = 0; i < limit; i++) {
      assertThat(String.format("Line number %d of the command output does not match the expected output", i), actualLines.get(i), is(expectedLines.get(i)));
    }
    if (actualLines.size() > expectedLines.size()) {
      fail("The command output contains more lines than the expected output");
    } else if (actualLines.size() < expectedLines.size()) {
      fail("The expected output contains more lines than the expected output");
    }
  }
}
