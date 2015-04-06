/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character. 
 */
public class EncodingHelperChar {
    private int codePoint;

    public EncodingHelperChar(int codePoint) {
        this.codePoint = codePoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) {
        // Not yet implemented.
    }
    
    public EncodingHelperChar(char ch) {
        // Not yet implemented.
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
        // Not yet implemented.
        return null;
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
        // Not yet implemented.
        return "";
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
        // Not yet implemented.
        return "";
    }
}
