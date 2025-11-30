import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ILOCScheduler {

    // make current "line" a static variable here? with a pointer to show which char we're on
    private static String line = null;
    private static int linePtr = 0;
    private static int lineLength = 0;
    private static final String[] syntaxGroup = new String[]{"BAD", "MEMOP", "LOADI", "ARITHOP", "OUTPUT", "NOP", "CONSTANT", "REG", "COMMA", "INTO", "NEWLINE", "ENDFILE"};
    private static final String[] lexemeMap = new String[]{"", "load", "store", "loadI", "add", "sub", "mult", "lshift", "rshift", "output", "nop", "\",\"", "\"=>\"", "\"\n\""};
    private static int lineNum = 1;
    private static IRNode head = new IRNode(-1, 0, 0, 0, 0);
    private static IRNode tail = new IRNode(-1, 0, 0, 0, 0);

    public static Token nextToken() {

        if (linePtr == lineLength) {
            return new Token(10, 13, 0);
        }

        char curr = line.charAt(linePtr);
        
        while (linePtr < lineLength) {
            curr = line.charAt(linePtr);
            if (Character.isWhitespace(curr)) {
                linePtr += 1;
            } else {
                break;
            }
        }

        if (linePtr == lineLength) {
            return new Token(10, 13, 0);
        }

        if (curr == 'l') {                  // load, loadI, lshift
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 'o') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'a') {
                            linePtr += 1;

                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 'd') {
                                    linePtr += 1;

                                    if (linePtr < lineLength) {
                                        curr = line.charAt(linePtr);
                                        if (curr == 'I') {
                                            linePtr += 1;
                                            return new Token(2, 3, 0);
                                        }
                                        else {
                                            return new Token(1, 1, 0);
                                        }
                                    }
                                    else {
                                        return new Token(1, 1, 0);
                                    }
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"loa");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"loa\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"lo");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"lo\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else if (curr == 's') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'h') {
                            linePtr += 1;
                            
                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 'i') {
                                    linePtr += 1;

                                    if (linePtr < lineLength) {
                                        curr = line.charAt(linePtr);
                                        if (curr == 'f') {
                                            linePtr += 1;

                                            if (linePtr < lineLength) {
                                                curr = line.charAt(linePtr);
                                                if (curr == 't') {
                                                    linePtr += 1;
                                                    return new Token(3, 7, 0);
                                                }
                                                else {
                                                    System.err.print("ERROR ");
                                                    System.err.print(lineNum);
                                                    System.err.print(":\t\"lshif");
                                                    System.err.print(curr);
                                                    System.err.println("\" is not a valid word.");
                                                    return new Token(0, 0, 0);
                                                }
                                            }
                                            else {
                                                System.err.print("ERROR ");
                                                System.err.print(lineNum);
                                                System.err.println(":\t\"lshif\" is not a valid word.");
                                                return new Token(0, 0, 0);
                                            }
                                        }
                                        else {
                                            System.err.print("ERROR ");
                                            System.err.print(lineNum);
                                            System.err.print(":\t\"lshi");
                                            System.err.print(curr);
                                            System.err.println("\" is not a valid word.");
                                            return new Token(0, 0, 0);
                                        }
                                    }
                                    else {
                                        System.err.print("ERROR ");
                                        System.err.print(lineNum);
                                        System.err.println(":\t\"lshi\" is not a valid word.");
                                        return new Token(0, 0, 0);
                                    }
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"lsh");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"lsh\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"ls");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"ls\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"l");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"l\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 'a') {           // add
            linePtr += 1;
            
            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 'd') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'd') {
                            linePtr += 1;
                            return new Token(3, 4, 0);
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"ad");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"ad\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"a");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"a\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 's') {           // store, sub
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 't') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'o') {
                            linePtr += 1;

                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 'r') {
                                    linePtr += 1;

                                    if (linePtr < lineLength) {
                                        curr = line.charAt(linePtr);
                                        if (curr == 'e') {
                                            linePtr += 1;
                                            return new Token(1, 2, 0);
                                        }
                                        else {
                                            System.err.print("ERROR ");
                                            System.err.print(lineNum);
                                            System.err.print(":\t\"stor");
                                            System.err.print(curr);
                                            System.err.println("\" is not a valid word.");
                                            return new Token(0, 0, 0);
                                        }
                                    }
                                    else {
                                        System.err.print("ERROR ");
                                        System.err.print(lineNum);
                                        System.err.println(":\t\"stor\" is not a valid word.");
                                        return new Token(0, 0, 0);
                                    }
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"sto");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"sto\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"st");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"st\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else if (curr == 'u') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'b') {
                            linePtr += 1;
                            return new Token(3, 5, 0);
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"su");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"su\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"s");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"s\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 'm') {           // mult
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 'u') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'l') {
                            linePtr += 1;

                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 't') {
                                    linePtr += 1;
                                    return new Token(3, 6, 0);
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"mul");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"mul\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"mu");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"mu\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"m");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"m\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 'r') {           // rshift, register
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 's') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'h') {
                            linePtr += 1;

                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 'i') {
                                    linePtr += 1;

                                    if (linePtr < lineLength) {
                                        curr = line.charAt(linePtr);
                                        if (curr == 'f') {
                                            linePtr += 1;

                                            if (linePtr < lineLength) {
                                                curr = line.charAt(linePtr);
                                                if (curr == 't') {
                                                    linePtr += 1;
                                                    return new Token(3, 8, 0);
                                                }
                                                else {
                                                    System.err.print("ERROR ");
                                                    System.err.print(lineNum);
                                                    System.err.print(":\t\"rshif");
                                                    System.err.print(curr);
                                                    System.err.println("\" is not a valid word.");
                                                    return new Token(0, 0, 0);
                                                }
                                            }
                                            else {
                                                System.err.print("ERROR ");
                                                System.err.print(lineNum);
                                                System.err.println(":\t\"rshif\" is not a valid word.");
                                                return new Token(0, 0, 0);
                                            }
                                        }
                                        else {
                                            System.err.print("ERROR ");
                                            System.err.print(lineNum);
                                            System.err.print(":\t\"rshi");
                                            System.err.print(curr);
                                            System.err.println("\" is not a valid word.");
                                            return new Token(0, 0, 0);
                                        }
                                    }
                                    else {
                                        System.err.print("ERROR ");
                                        System.err.print(lineNum);
                                        System.err.println(":\t\"rshi\" is not a valid word.");
                                        return new Token(0, 0, 0);
                                    }
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"rsh");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"rsh\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"rs");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"rs\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else if (Character.isDigit(curr)) {                 // matches to a register
                    int total = 0;
                    int digit;

                    while (Character.isDigit(curr)) {
                        digit = curr - '0';
                        total = 10 * total + digit;
                        linePtr += 1;

                        if (linePtr < lineLength) {
                            curr = line.charAt(linePtr);
                        }
                        else {
                            return new Token(7, 0, total);
                        }
                    }

                    return new Token(7, 0, total);

                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"r");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"r\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 'o') {           // output
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 'u') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 't') {
                            linePtr += 1;

                            if (linePtr < lineLength) {
                                curr = line.charAt(linePtr);
                                if (curr == 'p') {
                                    linePtr += 1;

                                    if (linePtr < lineLength) {
                                        curr = line.charAt(linePtr);
                                        if (curr == 'u') {
                                            linePtr += 1;

                                            if (linePtr < lineLength) {
                                                curr = line.charAt(linePtr);
                                                if (curr == 't') {
                                                    linePtr += 1;
                                                    return new Token(4, 9, 0);
                                                }
                                                else {
                                                    System.err.print("ERROR ");
                                                    System.err.print(lineNum);
                                                    System.err.print(":\t\"outpu");
                                                    System.err.print(curr);
                                                    System.err.println("\" is not a valid word.");
                                                    return new Token(0, 0, 0);
                                                }
                                            }
                                            else {
                                                System.err.print("ERROR ");
                                                System.err.print(lineNum);
                                                System.err.println(":\t\"outpu\" is not a valid word.");
                                                return new Token(0, 0, 0);
                                            }
                                        }
                                        else {
                                            System.err.print("ERROR ");
                                            System.err.print(lineNum);
                                            System.err.print(":\t\"outp");
                                            System.err.print(curr);
                                            System.err.println("\" is not a valid word.");
                                            return new Token(0, 0, 0);
                                        }
                                    }
                                    else {
                                        System.err.print("ERROR ");
                                        System.err.print(lineNum);
                                        System.err.println(":\t\"outp\" is not a valid word.");
                                        return new Token(0, 0, 0);
                                    }
                                }
                                else {
                                    System.err.print("ERROR ");
                                    System.err.print(lineNum);
                                    System.err.print(":\t\"out");
                                    System.err.print(curr);
                                    System.err.println("\" is not a valid word.");
                                    return new Token(0, 0, 0);
                                }
                            }
                            else {
                                System.err.print("ERROR ");
                                System.err.print(lineNum);
                                System.err.println(":\t\"out\" is not a valid word.");
                                return new Token(0, 0, 0);
                            }
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"ou");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"ou\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"o");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"o\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == 'n') {           // nop
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == 'o') {
                    linePtr += 1;

                    if (linePtr < lineLength) {
                        curr = line.charAt(linePtr);
                        if (curr == 'p') {
                            linePtr += 1;
                            return new Token(5, 10, 0);
                        }
                        else {
                            System.err.print("ERROR ");
                            System.err.print(lineNum);
                            System.err.print(":\t\"no");
                            System.err.print(curr);
                            System.err.println("\" is not a valid word.");
                            return new Token(0, 0, 0);
                        }
                    }
                    else {
                        System.err.print("ERROR ");
                        System.err.print(lineNum);
                        System.err.println(":\t\"no\" is not a valid word.");
                        return new Token(0, 0, 0);
                    }
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"n");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"n\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (curr == ',') {           // comma
            linePtr += 1;
            return new Token(8, 11, 0);
        }
        else if (curr == '=') {           // into
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == '>') {
                    linePtr += 1;
                    return new Token(9, 12, 0);
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"=");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"=\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else if (Character.isDigit(curr)) {           // constant
            int total = 0;
            int digit;

            while (Character.isDigit(curr)) {
                digit = curr - '0';
                total = 10 * total + digit;
                linePtr += 1;

                if (linePtr < lineLength) {
                    curr = line.charAt(linePtr);
                }
                else {
                    return new Token(6, 0, total);
                }
            }

            return new Token(6, 0, total);

        }
        else if (curr == '/') {            // comment
            linePtr += 1;

            if (linePtr < lineLength) {
                curr = line.charAt(linePtr);
                if (curr == '/') {
                    return new Token(10, 13, 0);
                }
                else {
                    System.err.print("ERROR ");
                    System.err.print(lineNum);
                    System.err.print(":\t\"/");
                    System.err.print(curr);
                    System.err.println("\" is not a valid word.");
                    return new Token(0, 0, 0);
                }
            }
            else {
                System.err.print("ERROR ");
                System.err.print(lineNum);
                System.err.println(":\t\"/\" is not a valid word.");
                return new Token(0, 0, 0);
            }
        }
        else {
            System.err.print("ERROR ");
            System.err.print(lineNum);
            System.err.print(":\t\"");
            System.err.print(curr);
            System.err.println("\" is not a valid word.");
            return new Token(0, 0, 0);
        }
    }

    public static void main(String[] args) throws Exception {

        // precedence: bad command line argument or file path OVER -h
        // includes attempting to set k multiple times
        // precedence: -h over valid command line arguments
        // k flag is -1, if -1 then unset, if not then it's being set multiple times
        // take most recent string filename as the one used

        boolean hFlag = false;
        boolean xFlag = false;
        boolean failFlag = false;
        int regCnt = -1;
        int maxSR = -1;
        FileReader reader = null;

        if (args.length == 0) {
            System.err.println("ERROR: No command-line flags found.");
        }

        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                switch (arg.charAt(1)) {
                    case 'h' -> hFlag = true;
                    case 'x' -> xFlag = true;
                    default -> {
                        // TODO: redo -h flag print statements
                        System.err.println("ERROR: Command line argument '" + arg.charAt(1) + "' not recognized.");
                        System.out.println("""
                            -h flag invoked.

                            Required command-line arguments:
                                fileName:  the absolute or relative pathname to the input file
                            
                            Optional flags:
                                -h: prints this help message
                            
                            At most one of the following three flags:
                                -r: reads, scans, and parses input file; builds intermediate representation
                                    and prints it out in a human-readable format (if parse is successful)
                                -p: reads, scans, and parses input file; builds intermediate representation
                                    and reports either success or failure; if success, reports how many
                                    operations were parsed
                                -s: reads and scans input file, printing to standard output stream a list of
                                    tokens found by the scanner
                        """);
                        return;
                    }
                }
            }
            else if (Character.isDigit(arg.charAt(0))) {
                if (regCnt >= 0) {
                    System.err.println("ERROR: Attempt to set k multiple times.");
                    return;
                }

                int total = 0;
                int digit;
                char curr;

                for (int i = 0; i < arg.length(); i++) {
                    curr = arg.charAt(i);
                    if (Character.isDigit(curr)) {
                        digit = curr - '0';
                        total = 10 * total + digit;
                    }
                    else {
                        break;      // encountered non-digit character in argument starting w/ a digit
                    }
                }
                regCnt = total;
            }
            else {          // arg is not a command line flag nor a constant, assume it is a file name
                File file = new File(arg);

                try {
                    reader = new FileReader(file);
                }
                catch (FileNotFoundException e) {
                    System.err.println("ERROR: Failure to open '" + arg + "' as the input file.");
                    return;
                }
            }
        }

        head.next = tail;
        head.prev = tail;
        tail.next = head;
        tail.prev = head;

        int flagCnt = 0;
        if (hFlag) {
            flagCnt += 1;
        }
        if (xFlag) {
            flagCnt += 1;
        }
        if (regCnt >= 0) {
            flagCnt += 1;
        }

        if (flagCnt > 1) {
            System.err.println("ERROR: Multiple modes specified, please try again with only one mode specified.");
            return;
        }

        if (hFlag) {
            System.out.println("""
                -h flag invoked.

                Required command-line arguments:
                    fileName:  the absolute or relative pathname to the input file
                
                Optional flags:
                    -h: prints this help message
                
                At most one of the following three flags:
                    -r: reads, scans, and parses input file; builds intermediate representation
                        and prints it out in a human-readable format (if parse is successful)
                    -p: reads, scans, and parses input file; builds intermediate representation
                        and reports either success or failure; if success, reports how many
                        operations were parsed
                    -s: reads and scans input file, printing to standard output stream a list of
                        tokens found by the scanner
            """);
        }
        else if (xFlag) {
            if (reader == null) {       // TODO: need this for allocation flag too
                System.err.println("ERROR: No input filename specified.");
                return;
            }

            BufferedReader bufReader = new BufferedReader(reader);

            line = bufReader.readLine();
            Token nextToken;

            while (line != null) {
                lineLength = line.length();
                
                int opcode;
                int arg1;
                int arg2;
                int arg3;

                if (lineLength > 0) {
                    nextToken = nextToken();

                    switch (nextToken.tokenType) {
                        case 1 -> {
                            opcode = nextToken.lexeme;

                            if (linePtr < lineLength) {         // check for whitespace
                                char curr = line.charAt(linePtr);
                                if (Character.isWhitespace(curr)) {
                                    linePtr += 1;
                                    nextToken = nextToken();

                                    if (nextToken.tokenType != 7) {
                                        failFlag = true;
                                        System.err.println("ERROR " + lineNum + ": \tMissing source register in load or store.");
                                    }
                                    else {
                                        arg1 = nextToken.numVal;
                                        if (arg1 > maxSR) {
                                            maxSR = arg1;
                                        }
                                        nextToken = nextToken();
                                        
                                        if (nextToken.tokenType != 9) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tMissing '=>' in load or store.");
                                        }
                                        else {
                                            nextToken = nextToken();

                                            if (nextToken.tokenType != 7) {
                                                failFlag = true;
                                                System.err.println("ERROR " + lineNum + ": \tMissing target register in load or store.");
                                            }
                                            else {
                                                arg3 = nextToken.numVal;
                                                if (arg3 > maxSR) {
                                                    maxSR = arg3;
                                                }
                                                nextToken = nextToken();

                                                if (nextToken.tokenType == 10) {
                                                    IRNode newOp = new IRNode(lineNum, opcode, arg1, -1, arg3);
                                                    newOp.prev = tail.prev;
                                                    newOp.next = tail;
                                                    tail.prev.next = newOp;
                                                    tail.prev = newOp;
                                                }
                                                else if (nextToken.tokenType == 6) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                                else if (nextToken.tokenType == 7) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line r" + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                                else if (nextToken.tokenType != 0) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.lexeme + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    failFlag = true;
                                    System.err.println("ERROR " + lineNum + ": \tMissing whitespace following opcode.");
                                }
                            }
                            else {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tMissing source register in load or store.");
                            }
                        }
                        case 2 -> {
                            opcode = nextToken.lexeme;

                            if (linePtr < lineLength) {         // check for whitespace
                                char curr = line.charAt(linePtr);
                                if (Character.isWhitespace(curr)) {
                                    linePtr += 1;
                                    nextToken = nextToken();

                                    if (nextToken.tokenType != 6) {
                                        failFlag = true;
                                        System.err.println("ERROR " + lineNum + ": \tMissing constant in loadI.");
                                    }
                                    else {
                                        arg1 = nextToken.numVal;
                                        nextToken = nextToken();
                                        
                                        if (nextToken.tokenType != 9) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tMissing '=>' in loadI.");
                                        }
                                        else {
                                            nextToken = nextToken();

                                            if (nextToken.tokenType != 7) {
                                                failFlag = true;
                                                System.err.println("ERROR " + lineNum + ": \tMissing target register in loadI.");
                                            }
                                            else {
                                                arg3 = nextToken.numVal;
                                                if (arg3 > maxSR) {
                                                    maxSR = arg3;
                                                }
                                                nextToken = nextToken();

                                                if (nextToken.tokenType == 10) {
                                                    IRNode newOp = new IRNode(lineNum, opcode, arg1, -1, arg3);
                                                    newOp.prev = tail.prev;
                                                    newOp.next = tail;
                                                    tail.prev.next = newOp;
                                                    tail.prev = newOp;
                                                }
                                                else if (nextToken.tokenType == 6) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                                else if (nextToken.tokenType == 7) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line r" + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                                else if (nextToken.tokenType != 0) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.lexeme + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    failFlag = true;
                                    System.err.println("ERROR " + lineNum + ": \tMissing whitespace following opcode.");
                                }
                            }
                            else {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tMissing constant in loadI.");
                            }
                        }
                        case 3 -> {
                            opcode = nextToken.lexeme;

                            if (linePtr < lineLength) {         // check for whitespace
                                char curr = line.charAt(linePtr);
                                if (Character.isWhitespace(curr)) {
                                    linePtr += 1;
                                    nextToken = nextToken();

                                    if (nextToken.tokenType != 7) {
                                        failFlag = true;
                                        System.err.println("ERROR " + lineNum + ": \tMissing first source register in " + opcode + ".");
                                    }
                                    else {
                                        arg1 = nextToken.numVal;
                                        if (arg1 > maxSR) {
                                            maxSR = arg1;
                                        }
                                        nextToken = nextToken();
                                        
                                        if (nextToken.tokenType != 8) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tMissing comma in " + opcode + ".");
                                        }
                                        else {
                                            nextToken = nextToken();

                                            if (nextToken.tokenType != 7) {
                                                failFlag = true;
                                                System.err.println("ERROR " + lineNum + ": \tMissing second source register in" + opcode + ".");
                                            }
                                            else {
                                                arg2 = nextToken.numVal;
                                                if (arg2 > maxSR) {
                                                    maxSR = arg2;
                                                }
                                                nextToken = nextToken();

                                                if (nextToken.tokenType != 9) {
                                                    failFlag = true;
                                                    System.err.println("ERROR " + lineNum + ": \tMissing '=>' in " + opcode + ".");
                                                }
                                                else {
                                                    nextToken = nextToken();

                                                    if (nextToken.tokenType != 7) {
                                                        failFlag = true;
                                                        System.err.println("ERROR " + lineNum + ": \tMissing target register in" + opcode + ".");
                                                    }
                                                    else {
                                                        arg3 = nextToken.numVal;
                                                        if (arg3 > maxSR) {
                                                            maxSR = arg3;
                                                        }
                                                        nextToken = nextToken();

                                                        if (nextToken.tokenType == 10) {
                                                            IRNode newOp = new IRNode(lineNum, opcode, arg1, arg2, arg3);
                                                            newOp.prev = tail.prev;
                                                            newOp.next = tail;
                                                            tail.prev.next = newOp;
                                                            tail.prev = newOp;
                                                        }
                                                        else if (nextToken.tokenType == 6) {
                                                            failFlag = true;
                                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                        }
                                                        else if (nextToken.tokenType == 7) {
                                                            failFlag = true;
                                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line r" + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                        }
                                                        else if (nextToken.tokenType != 0) {
                                                            failFlag = true;
                                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.lexeme + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    failFlag = true;
                                    System.err.println("ERROR " + lineNum + ": \tMissing whitespace following opcode.");
                                }
                            }
                            else {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tMissing first source register in " + nextToken.lexeme + ".");
                            }
                        }
                        case 4 -> {
                            opcode = nextToken.lexeme;

                            if (linePtr < lineLength) {         // check for whitespace
                                char curr = line.charAt(linePtr);
                                if (Character.isWhitespace(curr)) {
                                    linePtr += 1;
                                    nextToken = nextToken();

                                    if (nextToken.tokenType != 6) {
                                        failFlag = true;
                                        System.err.println("ERROR " + lineNum + ": \tMissing constant in output.");
                                    }
                                    else {
                                        arg1 = nextToken.numVal;
                                        
                                        nextToken = nextToken();
                                        
                                        if (nextToken.tokenType == 10) {
                                            IRNode newOp = new IRNode(lineNum, opcode, arg1, -1, -1);
                                            newOp.prev = tail.prev;
                                            newOp.next = tail;
                                            tail.prev.next = newOp;
                                            tail.prev = newOp;
                                        }
                                        else if (nextToken.tokenType == 6) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                        }
                                        else if (nextToken.tokenType == 7) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line r" + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                        }
                                        else if (nextToken.tokenType != 0) {
                                            failFlag = true;
                                            System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.lexeme + " (" + syntaxGroup[nextToken.tokenType] + ")");
                                        }
                                    }
                                }
                                else {
                                    failFlag = true;
                                    System.err.println("ERROR " + lineNum + ": \tMissing whitespace following opcode.");
                                }
                            }
                            else {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tMissing constant in output.");
                            }
                        }
                        case 5 -> {
                            opcode = nextToken.lexeme;

                            nextToken = nextToken();
                            
                            if (nextToken.tokenType == 10) {
                                IRNode newOp = new IRNode(lineNum, opcode, -1, -1, -1);
                                newOp.prev = tail.prev;
                                newOp.next = tail;
                                tail.prev.next = newOp;
                                tail.prev = newOp;
                            }
                            else if (nextToken.tokenType == 6) {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                            }
                            else if (nextToken.tokenType == 7) {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tExtra token at end of line r" + nextToken.numVal + " (" + syntaxGroup[nextToken.tokenType] + ")");
                            }
                            else if (nextToken.tokenType != 0) {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tExtra token at end of line " + nextToken.lexeme + " (" + syntaxGroup[nextToken.tokenType] + ")");
                            }
                        }
                        case 0 -> {
                            failFlag = true;
                        }
                        case 10 -> {

                        }
                        default -> {
                            if (nextToken.tokenType == 6) {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tOperation starts with an invalid opcode: " + nextToken.numVal + ".");    
                            }
                            else if (nextToken.tokenType == 7) {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tOperation starts with an invalid opcode: r" + nextToken.numVal + ".");    
                            }
                            else {
                                failFlag = true;
                                System.err.println("ERROR " + lineNum + ": \tOperation starts with an invalid opcode: " + nextToken.lexeme + ".");                               
                            }
                        }
                    }
                }
                else {
                    // TODO: this is an empty line, return newline token ==> this can be optimized
                }
                // TODO: need to append EOL regardless??? think about this

                line = bufReader.readLine();
                lineNum += 1;
                linePtr = 0;
            }

            if (failFlag) {             // should fail here on a bad scan or parse
                System.out.println("Due to the syntax error, run terminates.");
                return;
            }

            // at this point, maxSR contains the maximum SR number
            // btw an opcode of 3 is equal to a loadI (for rematerializables)

            // IRNode currNode = head.next;
            // while (currNode.lineNum > 0) {
            //     switch (currNode.opcode) {
            //         case 1 -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ sr" + currNode.args[0] + "], [ ], [sr" + currNode.args[8] + " ]");
            //         }
            //         case 2 -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ sr" + currNode.args[0] + "], [ ], [sr" + currNode.args[8] + " ]");
            //         }
            //         case 3 -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ val " + currNode.args[0] + "], [ ], [sr " + currNode.args[8] + " ]");
            //         }
            //         case 9 -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ val " + currNode.args[0] + "], [ ], [ ]");
            //         }
            //         case 10 -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ ], [ ], [ ]");
            //         }
            //         default -> {
            //             System.out.println(lexemeMap[currNode.opcode] + "\t[ sr" + currNode.args[0] + "], [ sr" + currNode.args[4] + "], [sr" + currNode.args[8] + " ]");
            //         }
            //     }
            //     currNode = currNode.next;
            // }

            int currVR = 0;
            int[] srToVR = new int[maxSR + 1];
            double[] lastUse = new double[maxSR + 1];

            for (int i = 0; i <= maxSR; i++) {
                srToVR[i] = -1;
                lastUse[i] = Double.POSITIVE_INFINITY;
            }

            int currOperand;
            int maxLive = 0;
            int liveDiff = 0;
            IRNode currNode = tail.prev;
            while (currNode.lineNum >= 0) {          // potential values for opcode: 1 (load), 2 (store), 3 (loadI), 4-8 (arithops), 9 (output), 10 (nop)
                // System.out.println("Renaming line " + currNode.lineNum + ": op is " + lexemeMap[currNode.opcode]);
                // first iterate through the defs
                if (currNode.opcode == 9 || currNode.opcode == 10) {
                    // output and nop operations don't need renaming
                    // System.out.println("Line " + currNode.lineNum + ": skip b/c output or nop");
                    currNode = currNode.prev;
                    continue;
                }
                if (currNode.opcode != 2) {       // store does not treat arg3 as a def
                    // System.out.println("Not a store");
                    currOperand = currNode.args[8];
                    // System.out.println("Target register is r" + currOperand + "; srToVR has " + srToVR[currOperand] + " as its VR");
                    if (srToVR[currOperand] < 0) {          // TODO: corresponds to an unused def, need special behavior? should it contribute to maxlive?
                        srToVR[currOperand] = currVR;
                        currVR += 1;
                    }
                    currNode.args[9] = srToVR[currOperand];
                    currNode.args[11] = (int) lastUse[currOperand];
                    srToVR[currOperand] = -1;
                    lastUse[currOperand] = Double.POSITIVE_INFINITY;
                    liveDiff -= 1;
                    // System.out.println("Reset srToVR for SR " + currOperand + ", now value is " + srToVR[currOperand]);
                }
                // else if (currNode.opcode == 3) {        // corresponds to loadI, which could be useful for rematerializables

                // }
                // then iterate through the uses
                if (currNode.opcode == 2) {         // store treats arg3 as a use
                    currOperand = currNode.args[8];
                    if (srToVR[currOperand] < 0) {
                        // System.out.println("Line " + currNode.lineNum + ": Found last use, create new VR " + currVR);
                        srToVR[currOperand] = currVR;
                        currVR += 1;
                        liveDiff += 1;
                    }
                    currNode.args[9] = srToVR[currOperand];
                    currNode.args[11] = (int) lastUse[currOperand];
                    lastUse[currOperand] = currNode.lineNum;
                }
                if (currNode.opcode != 3) {         // every valid operation atp besides loadI treats arg1 as a use
                    currOperand = currNode.args[0];
                    if (srToVR[currOperand] < 0) {
                        // System.out.println("Line " + currNode.lineNum + ": Found last use, create new VR " + currVR);
                        srToVR[currOperand] = currVR;
                        currVR += 1;
                        liveDiff += 1;
                    }
                    currNode.args[1] = srToVR[currOperand];
                    currNode.args[3] = (int) lastUse[currOperand];
                    lastUse[currOperand] = currNode.lineNum;
                }
                if (currNode.opcode >= 4) {         // every arithop treats arg2 as a use
                    currOperand = currNode.args[4];
                    if (srToVR[currOperand] < 0) {
                        // System.out.println("Line " + currNode.lineNum + ": Found last use, create new VR " + currVR);
                        srToVR[currOperand] = currVR;
                        currVR += 1;
                        liveDiff += 1;
                    }
                    currNode.args[5] = srToVR[currOperand];
                    currNode.args[7] = (int) lastUse[currOperand];
                    lastUse[currOperand] = currNode.lineNum;
                }

                maxLive += liveDiff;
                liveDiff = 0;
                currNode = currNode.prev;
            }

            // System.out.println("\n\n\nBIG BREAK!!!\n\n\n");

            currNode = head.next;
            while (currNode.lineNum >= 0) {
                switch (currNode.opcode) {
                    case 1 -> {
                        System.out.println(lexemeMap[currNode.opcode] + "\tr" + currNode.args[1] + "\t=> r" + currNode.args[9]);
                    }
                    case 2 -> {
                        System.out.println(lexemeMap[currNode.opcode] + "\tr" + currNode.args[1] + "\t=> r" + currNode.args[9]);
                    }
                    case 3 -> {
                        System.out.println(lexemeMap[currNode.opcode] + "\t" + currNode.args[0] + "\t=> r" + currNode.args[9]);
                    }
                    case 9 -> {
                        System.out.println(lexemeMap[currNode.opcode] + "\t" + currNode.args[0]);
                    }
                    case 10 -> {
                        System.out.println(lexemeMap[currNode.opcode]);
                    }
                    default -> {
                        System.out.println(lexemeMap[currNode.opcode] + "\tr" + currNode.args[1] + ",r" + currNode.args[5] + "\t=> r" + currNode.args[9]);
                    }
                }
                currNode = currNode.next;
            }
        }
        else if (regCnt >= 0 || reader != null) {         // allocator mode
            if (reader == null) {
                System.err.println("ERROR: No input filename specified.");
                return;
            }

            if (regCnt < 0) {
                System.out.println("Warning: k not set; asumming 32.");
                regCnt = 32;
            }
            else if (regCnt < 3 || regCnt > 64) {
                System.err.println("ERROR: Specified k is outside of valid range from 3 and 64, inclusive.");
                return;
            }
        }
    }

    // TODO: consider what to do for unused definitions, or undefined uses
}