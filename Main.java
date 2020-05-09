        package encryptdecrypt;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.util.Scanner;

public class Main {

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static String unicode(String mode, String data, int key) {
        String outString = "";
        switch (mode) {
            case "enc":
            {
                for(int i = 0; i < data.length(); i++) {
                    //char c = Character.getNumericValue(inputString.charAt(i));
                    outString += (char) (data.charAt(i) + key);
                }
                break;
            }
            case "dec":
            {
                for(int i = 0; i < data.length(); i++) {
                    char c = data.charAt(i);
                    outString += (char) (data.charAt(i) - key);
                }
            }
        }
        return outString;
    }
    public static String shift(String mode,String inputString, int key){
        String outString = "";
        String inVocab = new String("abcdefghijklmnopqrstuvwxyz");
        //String outVocab = new String("zyxwvutsrqponmlkjihgfedcba");
        switch (mode) {
            case "enc":
            {
        for(int i = 0; i < inputString.length(); i++)
        {
            char c = inputString.charAt(i);
            if (!((c == ' ')||(c == '!'))) {
                int pos = inVocab.indexOf(Character.toLowerCase(c));
                int newPos;
                if ((pos + key) < inVocab.length()) {
                    newPos = pos + key;
                } else
                {
                    newPos = (pos + key)%inVocab.length();
                }
                char newC;
                if (Character.isUpperCase(c))
                    newC = Character.toUpperCase(inVocab.charAt(newPos));
                else {
                    newC = inVocab.charAt(newPos);
                }
                outString +=  newC;
            }
            else {
                outString += c;
            }
        }
        break;}
            case "dec":
            {
                for(int i = 0; i < inputString.length(); i++)
                {
                    char c = inputString.charAt(i);
                    if (!((c == ' ')||(c == '!'))) {
                        int pos = inVocab.indexOf(Character.toLowerCase(c));
                        int newPos;
                        if ((pos - key) < 0) {
                            newPos = inVocab.length() - (key-1-pos)-1;
                        } else
                        {
                            newPos = (pos - key);
                        }
                        char newC;
                        if (Character.isUpperCase(c))
                            newC = Character.toUpperCase(inVocab.charAt(newPos));
                                else {
                            newC = inVocab.charAt(newPos);
                        }
                        outString +=  newC;
                    }
                    else {
                        outString += c;
                    }
                }
                break;
            }
        }
        return outString;
    }
    public static String coder(String mode, String inputString, int key, String alg) {
    switch (alg){
        case "shift": {
            return Main.shift(mode, inputString, key);
        }
        case "unicode": {
            return Main.unicode(mode, inputString, key);
        }
        default: return Main.shift(mode, inputString, key);
    }
    }

    public static void main(String[] args) {
        String mode = "enc";
        String data = "";
        String in = "";
        String out = "";
        String alg = "shift";
        int key = 0;
        String alert = "";

        /*****   TEST INIT BLOK  ************
         mode = "dec";
         data = "\\jqhtrj%yt%m~ujwxpnqq&";
         in = "D:\\Download\\File1.txt";
         out = "D:\\Download\\File2.txt";
         key = 5;
         ***********************************
        *****   TEST INIT BLOK  ************
         mode = "dec";
         data = "Welcome to hyperskill!";
        data = "Bjqhtrj yt mdujwxpnqq!";
        //data = "EEEEE";
         //data = "\\jqhtrj%yt%m~ujwxpnqq&";
         in = "";
         out = "";
         key = 5;
         alg = "shift";
         ***********************************/


        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                mode = args[i+1];
                i ++;
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i+1]);
                i ++;
            }
            if (args[i].equals("-data")) {
                data = args[i+1];
                i ++;
            }
            if (args[i].equals("-in")) {
                in = args[i+1];
                i ++;
            }
            if (args[i].equals("-out")) {
                out = args[i+1];
                i ++;
            }
            if (args[i].equals("-alg")) {
                alg = args[i+1];
                i ++;
            }
        }


        String strIn = "";
        String strOut = "";
        if (!in.equals("")) {
            try {
                strIn = readFileAsString(in);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
        else
            strIn = data;
        strOut = Main.coder(mode, strIn, key, alg);
        if (out.equals("")) {
            System.out.println(strOut);
        }
        else
        {
            File file = new File(out);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(strOut);
            } catch (IOException e) {
                System.out.printf("Error");
            }
        }
        //System.out.println(Main.coder("enc", "Welcome to hyperskill!", 5));
        //System.out.println(Main.coder("dec", "\\jqhtrj%yt%m~ujwxpnqq&", 5));

    }
}
