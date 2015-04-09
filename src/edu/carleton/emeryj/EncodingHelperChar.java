/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character. 
 */
package edu.carleton.emeryj;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class EncodingHelperChar {
    private int codePoint;

    public EncodingHelperChar(int codePoint) {
        this.codePoint = codePoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) throws Exception {
        if (utf8Bytes.length == 1) {
            codePoint = (int)utf8Bytes[0];
        } else {

            // Walks through each byte in utf8Bytes and bit shifts it's last 6
            // digits to the current end of the codePoint sequence.

            int i = utf8Bytes.length - 1;
            int n = 0;
            codePoint = 0;
            while (i>0) {
                codePoint = (codePoint | ((int)(utf8Bytes[i] & 0x3f)<< (n*6)));
                i--;
                n++;
            }

            // Takes the last byte in the array and adds on the correct
            // data bits from the leading byte of UTF-8 encoding.

            byte lastBitSpaceUsed = (byte)(0xFF >>> (utf8Bytes.length + 1));
            codePoint = (int)(codePoint | ((int)(utf8Bytes[i] &(byte)
                    lastBitSpaceUsed )<< (n*6)));
        }

        // Anticipated exception for passing to controller if the user gives
        // too high a codepoint
        if (codePoint>0x10FFFF){
            throw new IllegalArgumentException("The codepoint " +
                    toCodePointString() + " is not valid, Unicode only " +
                    "supports codepoints up to U+10FFFF");
        }
    }
    
    public EncodingHelperChar(char ch) {
        codePoint = (int) ch; //Note: this only works for codepoints under
        // U+FFFF, after that there will be no guarintees about how it is
        // handled because of the java char primitive
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public void setCodePoint(int codePoint) {
        this.codePoint = codePoint;
    }
    
    /**
     * Converts this character into an array of the bytes in its UTF-8
     * representation.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, whose UTF-8 form consists of the two-byte sequence C3 A9, then
     * this method returns a byte[] of length 2 containing C3 and A9.
     *
     * 
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
        int tempCodePoint = codePoint;
        byte[] output = null;

        // Determines the proper number of UTF-8 bytes the encoding needs
        if (codePoint<0x80){
            output = new byte[1];
            output[0] = (byte)codePoint;
            return output;
        } else if (codePoint<0x0800) {
            output = new byte[2];
        } else if (codePoint<0x10000){
            output = new byte[3];
        } else {
            output = new byte[4];
        }

        // Truncates the code pint to 6 bits and adds the UTF-8 continued
        // character tag to the font of the bit in order.
        for (int i = output.length-1; i > 0; i--) {
            byte currentByte = (byte)((((byte)tempCodePoint) & 0x3f) | 0x80);
            tempCodePoint = tempCodePoint >>> 6;
            output[i] = currentByte;
        }

        // Handles building the leading UTF-8 byte
        byte firstByteStart = (byte)(0xfe << (7 - output.length));
        byte firstByteBits = (byte)(0x7f >>> output.length);
        output[0] = (byte)((firstByteBits & (byte)tempCodePoint) |
                firstByteStart);
        return output;
    }
    
    /**
     * Generates the conventional 4-digit hexadecimal code point notation for
     * this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string U+00E9 (no quotation marks in
     * the returned String).
     *
     * @return the U+ string for this character
     */
    public String toCodePointString() {
        String hexValue = Integer.toHexString(codePoint).toUpperCase();
        for (int i = 4 - hexValue.length(); i>0; i--) {
            hexValue = "0" + hexValue;
        }
        return "U+" + hexValue;
    }
    
    /**
     * Generates a hexadecimal representation of this character suitable for
     * pasting into a string literal in languages that support hexadecimal byte
     * escape sequences in string literals (e.g. C, C++, and Python).
     *   For example, if this character is a lower-case letter e with an acute
     * accent (U+00E9), then this method returns the string \xC3\xA9. Note that
     * quotation marks should not be included in the returned String.
     *
     * @return the escaped hexadecimal byte string
     */
    public String toUtf8String() {
        byte[] bytes = this.toUtf8Bytes();
        StringBuffer output = new StringBuffer();
        for (byte b: bytes) {
            output.append("\\x" + String.format("%x",b).toUpperCase());
        }
        return output.toString();
    }
    
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
        String unicodeHex = toCodePointString().substring(2);
        File unicodeFile = new File("./src/edu/carleton/emeryj/UnicodeData.txt");
        String unicodeInfo = null;
        try {

            // Opens a scanner of the UnicodeData.txt and reads the begining
            // of each line until it finds the unicode hex value
            // corresponding to the current codepoint.
            Scanner scanner = new Scanner(unicodeFile);
            while (scanner.hasNextLine()){
                String curLine = scanner.nextLine();

                // Determines if the start of the line matches the unicode
                // hexidecimal value for the given codepoint.
                if (unicodeHex.equals(curLine.substring(0, unicodeHex.length()
                ))){

                    unicodeInfo = curLine.substring(unicodeHex.length()+1);
                    unicodeInfo = unicodeInfo.substring(0, unicodeInfo.indexOf
                            (";"));

                    // Handles what happenes if the found character is a
                    // control character.
                    if (unicodeInfo.equals("<control>")) {
                        String controlName = curLine.substring(curLine
                                .indexOf(";N;")+3);
                        controlName = controlName.substring(0,controlName
                                .indexOf(";"));
                        unicodeInfo = unicodeInfo + " " + controlName;
                    }
                    break;
                }
            }

        } catch (FileNotFoundException e){
            unicodeInfo = "UnicodeData.txt was not found";
        }
        if (unicodeInfo == null) {
            unicodeInfo = "<unknown> " + toCodePointString();
        }
        return unicodeInfo;
    }
}
