package edu.carleton.emeryj;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jamie on 4/12/15.
 */
public class EncodingHelperTest {

    //Tiny helper function that converts arrays of EncodingHelperChar to int
    // arrays corresponding to their codepoint
    private int[] convert(EncodingHelperChar[] chars) {
        int[] output = new int[chars.length];
        for (int i = 0; i < chars.length; i++){
            output[i] = chars[i].getCodePoint();
        }
        return output;
    }

    @Test
    public void testGetCharList() throws Exception {
        String[] args = {"\\x00x41x00","0x41","0041"};
        EncodingHelperChar[] actual = EncodingHelper.getCharList(args);
        int[] expected = {0x0000,0x0041,0x0000,0x0041,0x0000,0x0041 };
        assertEquals("testGetCharList() did not return the expected value for" +
                " byte array inputs", convert(actual), expected);
    }

    @Test
    public void testParseCodepoint() throws Exception {
        String args = "u0003u222fu888fu11111";
        EncodingHelperChar[] actual = EncodingHelper.parseCodepoint(args);
        int[] expected = {0x0003,0x222f,0x888f,0x11111 };
        assertEquals("parseCodePoint() did not return the expected value for" +
                " string input", convert(actual), expected);
    }

    @Test
    public void testGetCharListString() throws Exception {
        String args = "être";
        EncodingHelperChar[] actual = EncodingHelper.getCharListString(args);
        int[] expected = {0x00EA,0x0074,0x0072,0x0065 };
        assertEquals("getCharListString() did not return the expected value " +
                "for string input", convert(actual), expected);
    }

    @Test
    public void testGetCharListUtf8Bytes() throws Exception {
        String args = "\\x0\\x41\\x0\\x41\\x0\\x41";
        EncodingHelperChar[] actual = EncodingHelper.getCharListUtf8Bytes(args);
        int[] expected = {0x0000,0x0041,0x0000,0x0041,0x0000,0x0041 };
        assertEquals("getCharListUtf8Bytes() did not return the expected " +
                "value for string input", convert(actual), expected);
    }

    @Test
    public void testInputCheck() throws Exception {
            String[] args = {"-i", "String"};
            boolean actual = EncodingHelper.inputCheck(args);
            boolean expected = true;
            assertEquals("inputCheck() did not return the expected " +
                    "value for string input", actual, expected);
    }

    @Test
    public void testOutputCheck() throws Exception {
        String[] args = {"-o", "String"};
        boolean actual = EncodingHelper.outputCheck(args);
        boolean expected = true;
        assertEquals("outputCheck() did not return the expected " +
                "value for string input", actual, expected);
    }

    @Test
    public void testParseUserPrefrences() throws Exception {
        String[] args = {"-o", "String","-i", "codepoint","Hello World?"};
        String[] actual = EncodingHelper.parseUserPrefrences(args);
        String[] expected = {"Hello World?"};
        assertArrayEquals("parseUserPrefrences() did not return the expected" +
                " value for string input", actual, expected);
    }
}