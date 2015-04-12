package edu.carleton.emeryj;

/**
 * Created by jamie on 4/12/15.
 */

import java.util.Arrays;

public class EncodingHelper {

    private static String inputType = "default";

    private static String outputType = "default";


    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static void printHelpMessage() {

    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static EncodingHelperChar[] getCharListString(String input) {
        return new EncodingHelperChar[1];
    }


    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static EncodingHelperChar[] getCharListCodepoint() {
       return new EncodingHelperChar[1];
    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static EncodingHelperChar[] getCharListUtf8Bits() {
        return new EncodingHelperChar[1];
    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static void printSummary(EncodingHelperChar[] charList) {

    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static boolean inputCheck(String[] args) throws Exception{
        int index = 0;
        if (args.length == 0) {
            throw new IllegalArgumentException("");
        }



        if (args[index].equals("-i") | args[index].equals("--input")) {

            index++;
            if (args[index].toLowerCase().equals("string")) {
                inputType = "string";
                return true;
            } else if (args[index].toLowerCase().equals("utf8")) {
                inputType = "utf8";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                inputType = "codepoint";
                return true;
            }
            throw new IllegalArgumentException("Invalid Input Type " +
                    "\nProper format is -i or --input <input type>\nValid" +
                    " input types are: string, codepoint, utf8");
        }
        return false;
    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static boolean outputCheck(String[] args) throws Exception{
        int index = 0;
        if (args[index].equals("-o") | args[index].equals("--output")) {
            index++;
            if (args[index].toLowerCase().equals("string")) {
                outputType = "string";
                return true;
            } else if (args[index].toLowerCase().equals("utf8")) {
                outputType = "utf8";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                outputType = "codepoint";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                outputType = "codepoint";
                return true;
            }
            throw new IllegalArgumentException("Invalid Output Type " +
                    "\nProper format is -0 or --output <output type>\nValid" +
                    " output types are: string, codepoint, utf8, summary");
        }
        return false;
    }




    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    private static String[] parseUserPrefrences(String[] args) throws
            Exception {
        boolean inputChanged = false;
        boolean outputChanged = false;
        String[] output = args;
        try {
            if (inputCheck(args)) {
                output = Arrays.copyOfRange(args, 2, args.length);
                inputChanged = true;
            } else if (outputCheck(args)) {
                output = Arrays.copyOfRange(args, 2, args.length);
                outputChanged = true;
            }

        } catch (IllegalArgumentException e){
            throw e;
        }

    }



    static void main(String[] args) {

        if (args.length == 0) {
            printHelpMessage();
        } else if (args.length == 1 ) {
            if (args[0].equals("-h") | args[0].equals("--help")) {
                printHelpMessage();
            } else {
                EncodingHelperChar[] userInput = getCharListString(args[0]);
                printSummary(userInput);
            }
        } else {
            try {
                args = parseUserPrefrences();

            } catch (IllegalArgumentException e) {
                System.out.print(e);
            }

        }





    }





}
