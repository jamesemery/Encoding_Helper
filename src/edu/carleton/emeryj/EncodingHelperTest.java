package edu.carleton.emeryj;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jamie on 4/12/15.
 */
public class EncodingHelperTest {

    @Test
    public void testMain() throws Exception {

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

    //For testing of private methods they have simply been coppied into this
    // file past this point. Everything below is not test code, it is code
    // coppied from EncodingHelper into here so we can run unit tests
    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static boolean inputCheck(String[] args) throws IllegalArgumentException{
        int index = 0;
        if (args.length == 0) {
            throw new IllegalArgumentException("No argument given");
        }
        if (args[index].equals("-i") | args[index].equals("--input")) {
            if (args.length == 1) {
                throw new IllegalArgumentException("No Input Argument Given " +
                        "\nProper format is [-i or --input] <input " +
                        "type>\nValid" +
                        " input types are: string, codepoint, utf8");
            }
            index++;
            if (args[index].toLowerCase().equals("string")) {
                //inputType = "string";
                return true;
            } else if (args[index].toLowerCase().equals("utf8")) {
                //inputType = "utf8";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                //inputType = "codepoint";
                return true;
            }
            throw new IllegalArgumentException("Invalid Input Type " +
                    "\nProper format is [-i or --input] <input type>\nValid" +
                    " input types are: string, codepoint, utf8");
        }
        return false;
    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static boolean outputCheck(String[] args) throws IllegalArgumentException{
        int index = 0;
        if (args.length == 0) {
            throw new IllegalArgumentException("No argument given");
        }
        if (args[index].equals("-o") | args[index].equals("--output")) {
            if (args.length == 1) {
                throw new IllegalArgumentException("No Onput Argument Given " +
                        "\nProper format is [-o or --output] <output " +
                        "type>\nValid" +
                        " output types are: string, codepoint, utf8");
            }
            index++;
            if (args[index].toLowerCase().equals("string")) {
                //outputType = "string";
                return true;
            } else if (args[index].toLowerCase().equals("utf8")) {
                //outputType = "utf8";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                //outputType = "codepoint";
                return true;
            } else if (args[index].toLowerCase().equals("summary")) {
                //outputType = "summary";
                return true;
            }
            throw new IllegalArgumentException("Invalid Output Type " +
                    "\nProper format is [-o or --output] <output type>\nValid" +
                    " output types are: string, codepoint, utf8, summary");
        }
        return false;
    }







}