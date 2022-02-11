package tst;// This is JUnit test program stub
// 1) You are to reproduce test1-test8 given and the expected output
// 2) You are to add your own JUnit test for testing your removeAll method

import org.junit.*;
import src.HtmlTag;
import src.HtmlValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class HtmlValidatorTest {

    private static final String EXPECTED_TEMPLATE = "src/expected_output/validate_result_for_test%d.txt";
    private static final String INPUT_TEMPLATE = "src/input_html/test%d.html";

    private static void testAgainstFiles(int testNumber) {
        testValidatorWithFiles(String.format(EXPECTED_TEMPLATE, testNumber), String.format(INPUT_TEMPLATE, testNumber));
    }

    private static void testValidatorWithFiles(String expectedOutputFilePath, String validatorInputFilePath) {
        String rawInput = dumpFileContentsToString(validatorInputFilePath);
        String expected = dumpFileContentsToString(expectedOutputFilePath);
        HtmlValidator validator = new HtmlValidator(HtmlTag.tokenize(rawInput));

        String validatorOutput = captureValidatorOutput(validator);
        Assert.assertEquals("Validator output must match expected value", expected, validatorOutput);
    }

    private static String captureValidatorOutput(HtmlValidator validator) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        validator.validate();

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString();
    }

    private static String dumpFileContentsToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath);
            return null;
        }
    }

    @Test
    public void test1() {
        testAgainstFiles(1);
    }

    @Test
    public void test2() {
        testAgainstFiles(2);
    }

    @Test
    public void test3() {
        testAgainstFiles(3);
    }

    @Test
    public void test4() {
        testAgainstFiles(4);
    }

    @Test
    public void test5() {
        testAgainstFiles(5);
    }

    @Test
    public void test6() {
        testAgainstFiles(6);
    }

    @Test
    public void test7() {
        testAgainstFiles(7);
    }

    @Test
    public void test8() {
        testAgainstFiles(8);
    }

    // TODO: finish tests 6-8

    @Test
    public void addTagTest() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        Objects.requireNonNull(validator);
        tags.forEach(validator::addTag);

        Assert.assertEquals("Expected tags do not match actual tags", tags, validator.getTags());
    }


    @Test(expected = IllegalArgumentException.class)
    //// TODO: write the method addNullTagTest to account null tags.
    public void addNullTagTest() {
        HtmlValidator val = new HtmlValidator();
        val.addTag(null);
    }


    @Test
    public void removeAllTest() {
        HtmlValidator validator = new HtmlValidator();
        HtmlTag pokemon = new HtmlTag("pokemon");
        HtmlTag hello = new HtmlTag("hello");
        for (int i = 0; i < 2; i++){
            validator.addTag(pokemon);
            validator.addTag(hello);
        }
        HtmlTag[] tags = {pokemon, pokemon};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tags));

        // TODO: Write the correct assert statement to test the tags
        validator.removeAll("hello");
        Assert.assertEquals(validator.getTags(), tags);
        validator.removeAll("pokemon");
        Assert.assertEquals(validator.getTags(), new ArrayList<>(List.of()));
    }

    // TODO: Generate the correct test and method for removeAllNullTest
    @Test(expected = IllegalArgumentException.class)
    public void removeAllNullTest(){
        HtmlValidator val = new HtmlValidator();
        val.removeAll(null);

    }
}