import java.io.*;

public class test {
    public static void main(String[] args) throws IOException {
        Grammar grammar = new Grammar();
        grammar.transform();
        String a = " ";
        String b = " ";
        System.out.println((a + b +b).equals(a));
    }
}
