//import java.io.*;
//import java.rmi.UnexpectedException;
//import java.util.List;
//import java.util.stream.IntStream;
//
//public class Analyse {
//    BufferedReader reader =
//            new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\zhiyuan\\Desktop\\token\\src\\reader.txt")));
//    //输出结果文件
//    BufferedWriter writer =
//            new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\zhiyuan\\Desktop\\token\\src\\writer.txt")));
//    String[] KeyWords = {"int", "short", "long", "char", "class", "finally","public"};
//    String str = null;
//    public Analyse() throws IOException {
//
//    }
//    public void check() throws IOException {
//        while ((str = reader.readLine())!=null) {
//            char []chars;
//            chars = str.toCharArray();
//            String AnswerStr = "";
//            for (int i = 0; i <= str.length(); i++) {
//                if (i < str.length() && chars[i] != ' ' && chars[i] != ',' && chars[i] != ';' && chars[i] != ':' && chars[i] != '\n') {
//                    AnswerStr += (chars[i]);
//                } else if (i < str.length() && chars[i] == ' ') {
//                    AnswerStr = getString(AnswerStr);
//                } else if (i < str.length() && chars[i] == ',' && chars[i] != ' ') {
//                    AnswerStr = getString(AnswerStr);
//                    writer.write("(,, -1)");
//                    writer.newLine();
//                    writer.flush();
//                } else if (i < str.length() && chars[i] == ';' && chars[i] != ' ') {
//                    AnswerStr = getString(AnswerStr);
//                    writer.write("(;, 34)");
//                    writer.newLine();
//                    writer.flush();
//                } else if (i < str.length() && chars[i] == ':' && chars[i] != ' ') {
//                    AnswerStr = getString(AnswerStr);
//                    writer.write("(:, 35)");
//                    writer.newLine();
//                    writer.flush();
//                }
//            }
//        }
//    }
//    private String getString(String answerStr) throws IOException {
//        for (int j = 0; j < KeyWords.length; j++) {
//            if (answerStr.equals(KeyWords[j])) {
//                switch (j) {
//                    case 0:
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 4:
//                    case 5:
//                    case 6:
//                    case 7:
//                        writer.write("(" + KeyWords[j] + ",\t" + (j + 1) + ")");
//                        writer.newLine();
//                        writer.flush();
//                        answerStr = "";
//                        break;
//                }
//            }
//        }
//        int aim = getStr(answerStr);
//        if(aim==10||aim==11) {
//            switch (aim) {
//                case 10:
//                case 11:
//                    writer.write("(" + answerStr + ",\t" + aim + ")");
//                    writer.newLine();
//                    writer.flush();
//                    answerStr = "";
//                    break;
//            }
//        }
//        aim = getOperator(answerStr);
//        if(aim >= 20 || aim <= 33 || aim == 0){
//            switch (aim){
//                case 20:
//                case 21:
//                case 22:
//                case 23:
//                case 24:
//                case 25:
//                case 26:
//                case 27:
//                case 28:
//                case 29:
//                case 30:
//                case 31:
//                case 32:
//                case 33:
//                    writer.write("(" + answerStr + ",\t" + aim + ")");
//                    writer.newLine();
//                    writer.flush();
//                    answerStr = "";
//                    break;
//            }
//        }
//        return answerStr;
//    }
//
//    public int getStr(String str){
//        int index = 0;
//        char []chars = str.toCharArray();
//        int value = 0;
//        while(index<chars.length) {
//            if ((chars[index] >= '0' && chars[index] <= '9') || (chars[index] >= 'a' && chars[index] <= 'z') ||
//                    (chars[index] >= 'A' && chars[index] <= 'Z')) {
//                if (chars[index] >= '0' && chars[index] <= '9') {
//                    value = 10;
//                    break;
//                }
//                if(index == chars.length-1){
//                    value = 11;
//                }
//                index++;
//            }
//            else {
//                if (chars[index] != '=' && chars[index] != '+' && chars[index] != '-' && chars[index] != '*' && chars[index] != '/' &&
//                        chars[index] != '>' && chars[index] != '<' && chars[index] != ':') {
//                    System.out.println("illegal character " + chars[index] + " have been found!!!");
//                }
//                break;
//            }
//        }
//        return value;
//    }
//    public int getOperator(String str){
//        int value = 99;
//        switch(str){
//            case "*":
//                value =  20;
//                break;
//            case "/":
//                value =  21;
//                break;
//            case "+":
//                value =  22;
//                break;
//            case "-":
//                value =  23;
//                break;
//            case "=":
//                value =  24;
//                break;
//            case ";":
//                value =  25;
//                break;
//            case "(":
//                value =  26;
//                break;
//            case ")":
//                value =  27;
//                break;
//            case ":":
//                value =  28;
//                break;
//            case ":=":
//                value =  29;
//                break;
//            case ">":
//                value =  30;
//                break;
//            case ">=":
//                value =  31;
//                break;
//            case "<":
//                value =  32;
//                break;
//            case "<+=":
//                value =  33;
//                break;
//            case "#":
//                value =  0;
//        }
//        return value;
//    }
//    public static void main(String[] args) throws IOException {
//        Analyse analyse = new Analyse();
//        analyse.check();
//    }
//
//}