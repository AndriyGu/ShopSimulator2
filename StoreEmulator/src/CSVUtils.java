

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class CSVUtils {



    public char DEFAULT_SEPARATOR = ',';
    public char DEFAULT_QUOTE = '"';


    public  void parserWarehouse(File csvFile, Map<Integer, Product> productList){




        try {
            String sCurrentLine;
            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            while ((sCurrentLine = br.readLine()) != null) {

                Product product = new Product(); //если вынести эту строчку в обявление класса то заполняется только последним продуктом

               List<String> line = parseLine(sCurrentLine);
               //System.out.println("Country [id=" + line.get(0) + ", alcohol=" + line.get(1) + " , name=" + line.get(2) +
               //         " , price=" + line.get(3) + " , type=" + line.get(4) +" , volume=" + line.get(5) +
               //         " , composition=" + line.get(6) +" , curentquantity=" + line.get(7) +"]");




                   while (line.get(3).startsWith(" ")) {
                       line.set(3, line.get(3).replace(" ", ""));

                    }
                while (line.get(5).startsWith(" ")) {
                    line.set(5, line.get(5).replace(" ", ""));

                }
                while (line.get(7).startsWith(" ")) {
                    line.set(7, line.get(7).replace(" ", ""));

                }



// parse the string


               product.setId(Integer.parseInt(line.get(0)));
               product.setAlcohol(Integer.parseInt(line.get(1)));
               product.setNameProduct(line.get(2));
               product.setPrice(parserBigDecimal(line.get(3)));
               product.setTypeProduct(line.get(4));
               product.setVolume(line.get(5));
               product.setComposition(line.get(6));
               product.setCurentquantity(Integer.parseInt(line.get(7)));
               product.setBuyProd(0);
               product.setSoldProd(0);
               product.setSpendCost(BigDecimal.valueOf(0));

              // productList.add(product);

               // System.out.println("prod.get.ID  " +product.getId()+ "prof.get.name"+product.getNameProduct());


                productList.put(product.getId(), product);
              // System.out.println(product.getId() +"    prog ="+product.getNameProduct());


            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



    public  List<String> parseLine(String cvsLine) {

        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public  List<String> parseLine(String cvsLine, char separators) {

        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public  List<String> parseLine(String cvsLine, char separators, char customQuote) {


        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

    public  BigDecimal parserBigDecimal(String string){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal bigDecimal=null;

        try {
            bigDecimal = (BigDecimal) decimalFormat.parse(string);

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("не правельный ввод данних в поле \"Price\"");
        }

        return bigDecimal;

    }

}