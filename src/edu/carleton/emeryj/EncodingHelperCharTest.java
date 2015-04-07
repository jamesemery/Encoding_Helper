package edu.carleton.emeryj;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * Created by jamie on 4/4/15.
 */
public class EncodingHelperCharTest {

    @Test
    public void testEncodingHelperCharConstructorByteArray() throws Exception {
        EncodingHelperChar expected = new EncodingHelperChar(65);
        expected.setCodePoint(65);
        byte[] test = "A".getBytes("UTF-8");
        EncodingHelperChar actual = new EncodingHelperChar(test);
        assertEquals("The UTF8 ByteArray constructor input does not match " +
                        "object", expected.getCodePoint(), actual.getCodePoint());
    }

    public void testEncodingHelperCharByteArrayConstrucyorHighValue() throws
            Exception {
        EncodingHelperChar expected = new EncodingHelperChar(65536);
        byte[] test = new byte[(byte)0xF0908080];
        EncodingHelperChar actual = new EncodingHelperChar(test);
        assertEquals("The UTF8 ByteArray constructor input does not match " +
                "object", expected.getCodePoint(), actual.getCodePoint());
    }

    @Test
    public void testEncodingHelperCharCharConstructor() throws Exception {
        EncodingHelperChar expected = new EncodingHelperChar(65);
        expected.setCodePoint(65);
        char test = 'A';
        EncodingHelperChar actual = new EncodingHelperChar(test);
        assertEquals("The char constructor input does not match object",
                expected.getCodePoint(), actual.getCodePoint());
    }


    @Test
    public void testEncodingHelperCharIntConstructor() throws Exception {
        EncodingHelperChar expected = new EncodingHelperChar(65);
        expected.setCodePoint(65);
        EncodingHelperChar actual = new EncodingHelperChar(65);
        assertEquals("The int constructor input does not match object",
                expected.getCodePoint(), actual.getCodePoint());
    }

    @Test
    public void testGetCodePoint() throws Exception {
        EncodingHelperChar demonstrator = new EncodingHelperChar(34);
        int expected = 34;
        int actual = demonstrator.getCodePoint();
        assertEquals("getCodePoint() did not return expected codepoint",
                expected, actual);
    }

    @Test
    public void testSetCodePoint() throws Exception {
        EncodingHelperChar demonstrator = new EncodingHelperChar(98);
        int expected = 34;
        demonstrator.setCodePoint(expected);
        int actual = demonstrator.getCodePoint();
        assertEquals("setCodePoint() did not set correct value" , expected, actual);
    }

    @Test
    public void testToUtf8Bytes() throws Exception {
        byte[] expected = "A".getBytes("UTF-8");
        EncodingHelperChar demonstrator = new EncodingHelperChar(expected);
        byte[] actual = demonstrator.toUtf8Bytes();
        assertEquals("toUtf8Bytes() did not return the expected value" ,
                Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void testToUtf8BytesHighValue() throws Exception {
        byte[] expected = "က".getBytes("UTF-8");
        EncodingHelperChar demonstrator = new EncodingHelperChar(expected);
        byte[] actual = demonstrator.toUtf8Bytes();
        assertEquals("toUtf8Bytes() did not return the expected value for " +
                        "higher unicode values" , Arrays.toString(expected),
                Arrays.toString(actual));
    }

    @Test
    public void testToCodePointString() throws Exception {
        String expected = "U+00E9";
        EncodingHelperChar demonstrator = new EncodingHelperChar('é');
        //EncodingHelperChar demonstrator = new EncodingHelperChar(233);
        String actual = demonstrator.toCodePointString();
        assertEquals("toCodePointString() did not return the expected value" ,
                expected, actual);
    }

    @Test
    public void testToCodePointStringHigherCodepoints() throws Exception {
        String expected = "U+10000";
        EncodingHelperChar demonstrator = new EncodingHelperChar(65536);
        String actual = demonstrator.toCodePointString();
        assertEquals("toCodePointString() did not return the expected value " +
                "for codepoint U+10000" , expected, actual);
    }

    @Test
    public void testToUtf8String() throws Exception {
        String expected = "\\xC3\\xA9";
        EncodingHelperChar demonstrator = new EncodingHelperChar('é');
        String actual = demonstrator.toUtf8String();
        assertEquals("toUtf8String() did not return the expected value" ,
                expected, actual);
    }


    @Test
    public void testGetCharacterName() throws Exception {
        String expected = "LATIN SMALL LETTER E WITH ACUTE";
        EncodingHelperChar demonstrator = new EncodingHelperChar('é');
        String actual = demonstrator.getCharacterName();
        assertEquals("getCharacterName() did not return the expected value" ,
                expected, actual);
    }
}