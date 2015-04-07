/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character. 
 */
package edu.carleton.emeryj;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EncodingHelperChar {
    private int codePoint;

    public EncodingHelperChar(int codePoint) {
        this.codePoint = codePoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) {
        try {
            String charachter = new String(utf8Bytes, "UTF-8");
            codePoint = charachter.codePointAt(0);
        } catch (UnsupportedEncodingException e) {
        }
    }
    
    public EncodingHelperChar(char ch) {
        codePoint = (int) ch;
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
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
        StringBuffer buff = new StringBuffer();
        buff.appendCodePoint(codePoint);
        String value = buff.toString();
        byte[] output = null;
        try {
            output = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){

        }
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
        // Not yet implemented.
        return "";
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
        String unicodeHex = this.toCodePointString().substring(2);
        File unicodeFile = new File("./src/edu/carleton/emeryj/UnicodeData.txt");
        String unicodeInfo = "";
        try {
            Scanner scanner = new Scanner(unicodeFile);
            while (scanner.hasNextLine()){
                unicodeInfo = scanner.nextLine();
                if (unicodeHex.equals(unicodeInfo.substring(0,unicodeHex.length()))){
                    unicodeInfo = unicodeInfo.substring(unicodeHex.length()+1);
                    unicodeInfo = unicodeInfo.substring(0, unicodeInfo.indexOf(";" +
                            ""));
                    break;
                }
            }

        } catch (FileNotFoundException e){
            unicodeInfo = "UnicodeData.txt was not found";
        }
        return unicodeInfo;
    }
}
