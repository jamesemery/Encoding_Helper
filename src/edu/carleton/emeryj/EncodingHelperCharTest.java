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
        byte[] test = new byte[1];
        test[0] = 0x41;
        EncodingHelperChar actual = new EncodingHelperChar(test);
        assertEquals("The UTF8 ByteArray constructor input does not match " +
                        "object", expected.getCodePoint(), actual.getCodePoint());
    }

    public void testEncodingHelperCharByteArrayConstrucyorHighValue() throws
            Exception {
        EncodingHelperChar expected = new EncodingHelperChar(65536);
        byte[] test = new byte[0xF0908080];
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
        byte[] expected = new byte[1];
        expected[0] = 0x41;
        EncodingHelperChar demonstrator = new EncodingHelperChar('A');
        byte[] actual = demonstrator.toUtf8Bytes();
        assertArrayEquals("toUtf8Bytes() did not return the expected value",
                expected, actual);
    }

    @Test
    public void testToUtf8BytesTwoBytes() throws Exception {
        byte[] expected = new byte[2];
        expected[0] = (byte)0xC4;
        expected[1] = (byte)0xa0;
        EncodingHelperChar demonstrator = new EncodingHelperChar(288);
        byte[] actual = demonstrator.toUtf8Bytes();
        assertArrayEquals("toUtf8Bytes() did not return the expected value " +
                        "for an expected 2 byte value",
                expected, actual);
    }

    @Test
    public void testToUtf8BytesThreeBytes() throws Exception {
        byte[] expected = new byte[3];
        expected[0] = (byte)0xE1;
        expected[1] = (byte)0x80;
        expected[2] = (byte)0x80;//"က".getBytes("UTF-8");
        EncodingHelperChar demonstrator = new EncodingHelperChar(4096);
        byte[] actual = demonstrator.toUtf8Bytes();
        assertArrayEquals("toUtf8Bytes() did not return the expected value " +
                "for an expected 3 byte value", expected, actual);
    }

    @Test
    public void testToUtf8BytesFourBytes() throws Exception {
        byte[] expected = new byte[4];
        expected[0] = (byte)0xf1;
        expected[1] = (byte)0x80;
        expected[2] = (byte)0x80;
        expected[3] = (byte)0xa4;//"က".getBytes("UTF-8");
        EncodingHelperChar demonstrator = new EncodingHelperChar(expected);
        byte[] actual = demonstrator.toUtf8Bytes();
        assertArrayEquals("toUtf8Bytes() did not return the expected value " +
                "for an expected 4 byte value", expected, actual);
    }

    @Test
    public void testToCodePointString() throws Exception {
        String expected = "U+00E9";
        EncodingHelperChar demonstrator = new EncodingHelperChar('é');
        //EncodingHelperChar demonstrator = new EncodingHelperChar(233);
        String actual = demonstrator.toCodePointString();
        assertEquals("toCodePointString() did not return the expected value",
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
    public void testGetCharacterNameControlCharacter() throws Exception {
    String expected = "<control> BELL";
        EncodingHelperChar demonstrator = new EncodingHelperChar(0x0007);
        String actual = demonstrator.getCharacterName();
        assertEquals("getCharacterName() did not return the expected value " +
                "when the codepoint was a control character", expected,
                actual);
    }

    @Test
    public void testGetCharacterNameUnknownCharacter() throws Exception {
        String expected = "<unknown> U+3FFF";
        EncodingHelperChar demonstrator = new EncodingHelperChar(0x3fff);
        String actual = demonstrator.getCharacterName();
        assertEquals("getCharacterName() did not return the expected value " +
                "when the codepoint was unassigned", expected, actual);
    }

    @Test
    public void testGetCharacterName() throws Exception {
        String expected = "LATIN SMALL LETTER E WITH ACUTE";
        EncodingHelperChar demonstrator = new EncodingHelperChar('é');
        String actual = demonstrator.getCharacterName();
        assertEquals("getCharacterName() did not return the expected value",
                expected, actual);
    }


    @Test
    public void testReadBytesSingle() throws Exception {
        String testString = "é";
        byte[] testInput = testString.getBytes("UTF-8");
        EncodingHelperChar[] actual = EncodingHelperChar.readBytes(testInput);

        EncodingHelperChar[] expected = new EncodingHelperChar[1];
        expected[0] = new EncodingHelperChar('é');
        assertArrayEquals("readBytes() did not return the expected value for " +
                "entre" +
                " input test", convert(expected), convert(actual));
    }

    @Test
    public void testReadBytesShort() throws Exception {
        String testString = "être";
        byte[] testInput = new byte[]{(byte)0xC3,(byte)0xAA,0x74,0x72,0x65};
        EncodingHelperChar[] actual = EncodingHelperChar.readBytes(testInput);
        int[] expected = {0xea,0x74,0x72,0x65};
        assertArrayEquals("readBytes() did not return the expected value for " +
                "entre input test", expected, convert(actual));
    }

    @Test
    public void testReadBytesLarge() throws Exception {
        byte[] testInput = {(byte)0xf0,(byte)0xa0,(byte)0x80,(byte)0x96,
                (byte)0x41};
        EncodingHelperChar[] actual = EncodingHelperChar.readBytes(testInput);
        int[] expected = {0x20016,0x41};
        assertArrayEquals("readBytes() did not return the expected value for " +
                "big input test", expected, convert(actual));
    }



    //Tiny helper function that converts arrays of EncodingHelperChar to int
    // arrays corresponding to their codepoint
    private int[] convert(EncodingHelperChar[] chars) {
        int[] output = new int[chars.length];
        for (int i = 0; i < chars.length; i++){
            output[i] = chars[i].getCodePoint();
        }
        return output;
    }

}