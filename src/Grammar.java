import java.io.*;

//~代替
public class Grammar {
    File file = new File("C:\\Users\\zhiyuan\\Desktop\\token\\src\\GrammarText.txt");
    BufferedReader reader =
            new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    public void transform() throws IOException {
        String str =  null;
        reader.mark((int)file.length()+1);
        int row = 0,col = 0;
        String rows = "";
        String cols = "";
        while ((str = reader.readLine())!=null){
            char []chars;
            chars = str.toCharArray();
            int i = 0;
            String rowSon = "";
            while(chars[i]!='-'){
                rowSon += chars[i];
                i++;
            }
            if(!rows.contains(rowSon)){
                rows += rowSon;
                rows += "?";
                row++;
            }
        /*    System.out.println(rows);
            System.out.println(row);*/
        }
        reader.reset();
        while ((str = reader.readLine())!=null){
            char []chars;
            chars = str.toCharArray();
            int i = 0;
            boolean flag = false;
            String colSon = "";
            while(i<chars.length){
                if(flag && chars[i]!='ε'){
                    colSon += chars[i];
                    if(!rows.contains(colSon)){
                        col++;
                        cols += chars[i];
                        cols += "?";
                    }
                    colSon = "";
                }
                if(chars[i]=='>'){
                    flag = true;
                }
                i++;
            }
 /*           System.out.println(cols);
            System.out.println(col);*/
        }
        row++;
        col++;
        String [][]rules = new String[row][col];
        rules[0][0] = null;
        int index = 1;
        for(int i = 0 ; i < rows.length() ; i++){
            char []charRow;
            charRow = rows.toCharArray();
            if (charRow[i]!='?'){
                rules[index][0] = ""+charRow[i];
                index++;
            }
        }
        index = 1;
        for(int i = 0 ; i < cols.length() ; i++){
            char []charCol;
            charCol = cols.toCharArray();
            if (charCol[i]!='?'){
                rules[0][index] = ""+charCol[i];
                index++;
            }
        }
        for(int i = 0 ; i < rules.length ; i++){
            for(int j = 0 ; j < rules[0].length ; j++) {
                System.out.print(rules[i][j]+"    ");
            }
            System.out.println();
        }
    }
    public Grammar() throws FileNotFoundException {
    }
}
