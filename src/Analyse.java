import java.io.*;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Analyse {
    BufferedReader reader =
            new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\zhiyuan\\Desktop\\token\\src\\reader.txt")));
    //输出结果文件
    BufferedWriter writer =
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\zhiyuan\\Desktop\\token\\src\\writer.txt")));
    String[] KeyWords = {"int", "if", "while", "else", "==", "!=", "public", "return","bool"};
    String str = null;
    List<Token> tokenList = new ArrayList<>();

    public Analyse() throws IOException {

    }

    public void check() throws IOException {
        while ((str = reader.readLine()) != null) {
            char[] chars;
            chars = str.toCharArray();
            String AnswerStr = "";
            for (int i = 0; i <= str.length(); i++) {
                if (i < str.length() && chars[i] != ' ' && chars[i] != ',' && chars[i] != ';' && chars[i] != ':' && chars[i] != '\n') {
                    AnswerStr += (chars[i]);
                } else if (i < str.length() && chars[i] == ' ') {
                    AnswerStr = getString(AnswerStr);
                } else if (i < str.length() && chars[i] == ',' && chars[i] != ' ') {
                    AnswerStr = getString(AnswerStr);
                    Token tempToken = new Token(",", String.valueOf(-1));
                    tokenList.add(tempToken);
                    writer.write(tempToken.toString());
                    writer.newLine();
                    writer.flush();
                } else if (i < str.length() && chars[i] == ';' && chars[i] != ' ') {
                    AnswerStr = getString(AnswerStr);
                    Token tempToken = new Token(";", String.valueOf(44));
                    tokenList.add(tempToken);
                    writer.write(tempToken.toString());
                    writer.newLine();
                    writer.flush();
                } else if (i < str.length() && chars[i] == ':' && chars[i] != ' ') {
                    AnswerStr = getString(AnswerStr);
                    Token tempToken = new Token(":", String.valueOf(45));
                    tokenList.add(tempToken);
                    writer.write(tempToken.toString());
                    writer.newLine();
                    writer.flush();
                }
            }

            if (chars[chars.length-1]=='}'||chars[chars.length-1]=='{') {
                AnswerStr = getString(String.valueOf(chars[chars.length - 1]));
            }

        }
    }

    private String getString(String answerStr) throws IOException {
        for (int j = 0; j < KeyWords.length; j++) {
            if (answerStr.equals(KeyWords[j])) {
                switch (j) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:

                        Token tempToken = new Token(KeyWords[j], String.valueOf(j+1));
                        tokenList.add(tempToken);
                        writer.write(tempToken.toString());
                        writer.newLine();
                        writer.flush();
                        answerStr = "";
                        break;
                }
            }
        }
        int aim = getStr(answerStr);
        if (aim == 10 || aim == 11) {
            switch (aim) {
                case 10:
                case 11:
                    Token tempToken = new Token(answerStr, String.valueOf(aim));
                    tokenList.add(tempToken);
                    writer.write(tempToken.toString());
                    writer.newLine();
                    writer.flush();
                    answerStr = "";
                    break;
            }
        }
        aim = getOperator(answerStr);
        if (aim >= 20 || aim <= 37 || aim == 0) {
            switch (aim) {
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                    Token tempToken = new Token(answerStr, String.valueOf(aim));
                    tokenList.add(tempToken);
                    writer.write(tempToken.toString());
                    writer.newLine();
                    writer.flush();
                    answerStr = "";
                    break;
            }
        }
        return answerStr;
    }

    public int getStr(String str) {
        int index = 0;
        char[] chars = str.toCharArray();
        int value = 0;
        while (index < chars.length) {
            if ((chars[index] >= '0' && chars[index] <= '9') || (chars[index] >= 'a' && chars[index] <= 'z') ||
                    (chars[index] >= 'A' && chars[index] <= 'Z')) {
                if (chars[index] >= '0' && chars[index] <= '9') {
                    value = 10;
                    break;
                }
                if (index == chars.length - 1) {
                    value = 11;
                }
                index++;
            } else {
                if (chars[index] != '=' && chars[index] != '+' && chars[index] != '-' && chars[index] != '*' && chars[index] != '/' &&
                        chars[index] != '>' && chars[index] != '<' && chars[index] != ':') {
                    System.out.println("illegal character " + chars[index] + " have been found!!!");
                }
                break;
            }
        }
        return value;
    }

    public int getOperator(String str) {
        int value = 99;
        switch (str) {
            case "*":
                value = 20;
                break;
            case "/":
                value = 21;
                break;
            case "+":
                value = 22;
                break;
            case "-":
                value = 23;
                break;
            case "=":
                value = 24;
                break;
            case ";":
                value = 25;
                break;
            case "(":
                value = 26;
                break;
            case ")":
                value = 27;
                break;
            case ":":
                value = 28;
                break;
            case ":=":
                value = 29;
                break;
            case ">":
                value = 30;
                break;
            case ">=":
                value = 31;
                break;
            case "<":
                value = 32;
                break;
            case "<+=":
                value = 33;
                break;
            case "{":
                value = 34;
                break;
            case "}":
                value = 35;
                break;
            case "#":
                value = 0;
            case "[":
                value = 36;
            case "]":
                value = 37;
        }
        return value;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }


}