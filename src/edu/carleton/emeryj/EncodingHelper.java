package edu.carleton.emeryj;

/**
 * Created by jamie on 4/12/15.
 */

import java.util.Arrays;
import java.util.ArrayList;
import edu.carleton.emeryj.EncodingHelperChar;

public class EncodingHelper {

    private static String inputType = "string";
    //default specified by assignemnt

    private static String outputType = "summary";
    //default specified by assignemnt


    /**
     * Takes the hitherto specified user input and output prefrences stored
     * in the class and prints out to the command line and prints out the
     * valid output based on the users prefrence requests.
     */
    public static String getOutput(EncodingHelperChar[] chars) {

        StringBuffer buff = new StringBuffer();
        if (outputType.equals("summary")|outputType.equals("string")) {
            if (outputType.equals("summary")) {
                if (chars.length == 1) {
                    buff.append("Character: ");
                } else {
                    buff.append("String: ");
                }
            }
            for (EncodingHelperChar c : chars) {
                buff.appendCodePoint(c.getCodePoint());
            }
            buff.append("\n");
        }

        if (outputType.equals("summary")|outputType.equals("codepoint")) {
            String line ="";
            if (outputType.equals("summary")) {
                if (chars.length == 1) {
                    line = "Codepoint: ";
                } else {
                    line = "Codepoints: ";
                }
            }
            for (EncodingHelperChar c : chars) {
                line = line + c.toCodePointString() + " ";
            }
            buff.append(line);
            buff.append("\n");
        }

        if (outputType.equals("summary")&(chars.length==1)) {
            String line = "Name :" + chars[0].getCharacterName();
            buff.append(line);
            buff.append("\n");
        }


        if (outputType.equals("summary")|outputType.equals("utf8")) {
            String line = "";
            if (outputType.equals("summary")) {
                line = "UTF-8: ";
            }
            for (EncodingHelperChar c : chars) {
                line = line + c.toUtf8String();
            }
            buff.append(line);
        }
        return buff.toString();
    }

    /**
     * Prints out a useful help message about the function and usage cases of
     * this class for the user
     */
    public static String helpMessage() {
        return " Usage: [-h|--help] [-i|--input inputType] " +
                "[-o|--output outputType] <data> \n inputType: " +
                "string, utf8, codepoint \n outputType: string, utf8," +
                " codepoint, summary";
    }


    public static String longHelpMessage() {
        return "Usage: [-h|--help] " +
                "[-i|--input inputType] [-o|--output outputType] " +
                "<data> \n help: prints out a detailed help message " +
                "\n input: specifies what inputType the data is \n " +
                "inputType: the data can either be represented as a " +
                "string ('string'), as UTF-8 ('utf8'), or as Unicode " +
                "codepoints ('codepoint') \n output: specifies what " +
                "outputType EncodingHelper should print out \n " +
                "outputType: the data can be any of the types that " +
                "inputType handles or summary ('summary'), which " +
                "prints out all three choices \n data: the data that " +
                "is  to be converted by EncodingHelper from the  " +
                "inputType to the outputType";
    }



    /**
     * Takes the command line arguments without user preference settings and
     * returns an array of EncodingHelperChar objects based on the input type
     * that was set previously.
     *
     * @returns an array of EncodingHelperChar objects.
     */
    public static EncodingHelperChar[] getCharList(String[] args) throws
    IllegalArgumentException {
        try {
            if (inputType.equals("string")) {
                String user = args[0];
                if (args.length>1) {
                    throw new IllegalArgumentException("Too many String " +
                            "arguments found, only one string argument is " +
                            "expected as input");
                }
                return getCharListString(user);
            }


            if (inputType.equals("utf8")) {
                String user = args[0];
                int i = 1;
                while (i < args.length) {
                    user = user + " " + args[i];
                    i++;
                }
                return getCharListUtf8Bytes(user);
            }


            if (inputType.equals("codepoint")) {
                ArrayList<Integer> codepointList = new ArrayList<Integer>();
                String user = args[0];
                int i = 1;
                while (i < args.length) {
                    user = user + " " + args[i];
                    i++;
                }
                return parseCodepoint(user);
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return null;
    }


    /**
     * Takes the command line arguments without user preferences and converts
     * the string to a string array filled with individual unicode codepoint
     * values in hex.
     * Throws an exception if there are non-hex values or if the codepoints of the
     * hex values are too high for Unicode.
     */
    public static EncodingHelperChar[] parseCodepoint(String arg) throws
            IllegalArgumentException {

        //Strips the string of fomatting and space seperates it
        arg = arg.replaceAll("[uU\\\\]*[\\+uU]", " ");
        arg = arg.trim();
        String[] scrubbedPoints = arg.split(" +");


        //Converts stripped strings into codepoints
        EncodingHelperChar[] output = new EncodingHelperChar[scrubbedPoints
                .length];
        for (int i = 0; i < scrubbedPoints.length; i++) {
            try {
                int codepoint = Integer.parseInt(scrubbedPoints[i],16);
                output[i] = new EncodingHelperChar(codepoint);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Improperly formatted " +
                        "codepoints \nValid codepoint formats are in " +
                        "hexidecimala follows: U+FFFF, \\uffff, ffff, uffff");
            } catch (IllegalArgumentException e) {
                throw e;
            }

        }
        return output;
    }

    /**
     * Takes a string of the input if the inputType is string, and returns an
     * array of EncodingHelperChar objects for each index in the string.
     *
     * @returns an array of EncodingHelperChar objects.
     */
    public static EncodingHelperChar[] getCharListString(String input) {
        EncodingHelperChar[] output = new EncodingHelperChar[input.length()];
        for (int i = 0; i < input.length(); i++) {
            try {
                String character = input.substring(i,i+1);
                byte[] bytes = character.getBytes("UTF-8");
                output[i] = new EncodingHelperChar(bytes);
            } catch (Exception e) {}
        }
        return output;
    }


    /**
     * Takes the command line arguments without user preferences and converts the
     * string to a EncodingHelperChar array that represents each UTF-8 encoding in
     * in arguments.
     *
     * @return an array of EncodingHelperChar objects
     */
    public static EncodingHelperChar[] getCharListUtf8Bytes(String arg)
            throws IllegalArgumentException {
        arg = arg.replaceAll(" 0x", "");
        arg = arg.replaceAll("\\\\*x","");
        arg = arg.replaceAll(" *","");
        arg = arg.replaceAll("[\'\"]","");
        if ((arg.length()%2)>0) {
            throw new IllegalArgumentException("Improperly formatted byes, " +
                    "expected an even number of hexidecimal characters");
        }
        byte[] utf8Array = new byte[arg.length()/2];

        for (int i = 0; i < arg.length();i+=2) {
            int parsedbyte  = Integer.parseInt(arg.substring(i, i + 2), 16);
            utf8Array[i/2] = (byte)parsedbyte;
        }
        EncodingHelperChar[] output;
        try {
            output = EncodingHelperChar.readBytes(utf8Array);
        } catch (IllegalArgumentException e){
            throw e;
        }
        return output;
    }

    /**
     * Takes the command line arguements as a string array and checks if the first
     * two indices of the string array are input arguments as specified by the
     * assignment. Returns true if they are, and returns false if they are not.
     * Throws an exceptions if the first index is an input flag, but the second
     * index does not contain a valid argument.
     *
     * @return if the first two indices of the string array are input arguments.
     */
    public static boolean inputCheck(String[] args) throws
            IllegalArgumentException{
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
                    "\nProper format is [-i or --input] <input type>\nValid" +
                    " input types are: string, codepoint, utf8");
        }
        return false;
    }

    /**
     * Takes the command line arguements as a string array and checks if the first
     * two indices of the string array are output arguments as specified by the
     * assignment. Returns true if they are, and returns false if they are not.
     * Throws an exceptions if the first index is an output flag, but the second
     * index does not contain a valid argument.
     *
     * @return if the first two indices of the string array are output
     * arguments.
     */
    public static boolean outputCheck(String[] args) throws
            IllegalArgumentException{
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
                outputType = "string";
                return true;
            } else if (args[index].toLowerCase().equals("utf8")) {
                outputType = "utf8";
                return true;
            } else if (args[index].toLowerCase().equals("codepoint")) {
                outputType = "codepoint";
                return true;
            } else if (args[index].toLowerCase().equals("summary")) {
                outputType = "summary";
                return true;
            }
            throw new IllegalArgumentException("Invalid Output Type " +
                    "\nProper format is [-o or --output] <output type>\nValid" +
                    " output types are: string, codepoint, utf8, summary");
        }
        return false;
    }




    /**
     * Takes the command line arguments and checks if the user has specified any
     * I/O preferences by using inputCheck and outputCheck. Sets the classes
     * inputType and outputType variables according to these preferences. Returns
     * the command line arguments with all the I/O related preferences removed.
     * Throws and exception if the user's I/O preferences were not properly
     * formatted.
     *
     * @return the command line arguments without user preference settings.
     */
    public static String[] parseUserPrefrences(String[] args) throws
            IllegalArgumentException {

        boolean inputChanged = false;
        boolean outputChanged = false;
        String[] output = args;
        try {

            //checks to see if the input or output is specified and shortens
            // the array if one is specified
            if (inputCheck(output)) {
                output = Arrays.copyOfRange(output, 2, output.length);
                inputChanged = true;
            } else if (outputCheck(output)) {
                output = Arrays.copyOfRange(output, 2, output.length);
                outputChanged = true;
            }

            //checks if there is a second input or output specified and
            // shortens the array further, throwin exception if array is empty
            if (inputCheck(output)) {
                if (inputChanged) {
                    throw new IllegalArgumentException("Input may only be " +
                            "specified once");
                }
                output = Arrays.copyOfRange(output, 2, output.length);
            } else if (outputCheck(output)) {
                if (outputChanged) {
                    throw new IllegalArgumentException("Output may only be " +
                            "specified once");
                }
                output = Arrays.copyOfRange(output, 2, output.length);
            }
            if (output.length == 0) {
                throw new IllegalArgumentException("No argument given");
            }

        } catch (IllegalArgumentException e){
            throw e;
        }
        return output;
    }



    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(helpMessage());
        } else if (args.length == 1 ) {
            if (args[0].equals("-h") | args[0].equals("--help")) {
                System.out.println(longHelpMessage());
            } else {
                EncodingHelperChar[] userInput = getCharListString(args[0]);
                System.out.println(getOutput(userInput));
            }
        } else {
            try {
                args = parseUserPrefrences(args);
                EncodingHelperChar[] userInput = getCharList(args);
                System.out.println(getOutput(userInput));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
