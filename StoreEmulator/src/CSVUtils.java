

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class CSVUtils {



    public void parserWarehouse(File csvFile, Map<Integer, Product> productList) {


        try {
            FileReader fr = new FileReader(csvFile);
            CSVReader csvReader = new CSVReader(fr);
            List<String[]> cellValues = new ArrayList<>();
            List<String> line = new ArrayList<>();

            for (String[] data = csvReader.readNext(); data != null; data = csvReader.readNext()) {
                System.out.println("------------");
                List<String> row = new ArrayList<>();
                for (String data1 : data) {
                    System.out.println(data1);
                    row.add(data1);

                }
                cellValues.add(row.toArray(new String[0]));
            }

            for (String[] temp:cellValues) {
                String [] erwe = new String[cellValues.size()];
                Product product = new Product();
                for (int i = 0; i<cellValues.size(); i++) {


                    if (temp[i].startsWith(" ")) {
                        while (temp[i].startsWith(" ")) {
                            temp[i] = temp[i].replace(" ", "");

                        }

                    }
                }

                    product.setId(Integer.parseInt(temp[0]));
                    product.setAlcohol(Integer.parseInt(temp[1]));
                    product.setNameProduct(temp[2]);
                    product.setPrice(parserBigDecimal(temp[3]));
                    product.setTypeProduct(temp[4]);
                    product.setVolume(temp[5]);
                    product.setComposition(temp[6]);
                    product.setCurentquantity(Integer.parseInt(temp[7]));
                    product.setBuyProd(0);
                    product.setSoldProd(0);
                    product.setSpendCost(BigDecimal.valueOf(0));

                    productList.put(product.getId(), product);

            }

        }
        catch (Exception E){
            E.printStackTrace();
        }


    }


public void createCSVFile(File csvNEWFile, Map<Integer, Product> productList) throws IOException {
        List<String []> cellValues = new ArrayList<>();


        CSVWriter writer = new CSVWriter(new FileWriter(csvNEWFile));


        for (int i = 1; i <=productList.size(); i++){
            String[] temp = new String[8];
            temp[0] = ""+productList.get(i).getId();
            temp[1] = ""+productList.get(i).getAlcohol();
            temp[2] = ""+productList.get(i).getNameProduct();
            temp[3] = ""+productList.get(i).getPrice();
            temp[4] = ""+productList.get(i).getTypeProduct();
            temp[5] = ""+productList.get(i).getVolume();
            temp[6] = ""+productList.get(i).getComposition();
            temp[7] = ""+productList.get(i).getCurentquantity();
                    cellValues.add(temp);
            }

        for(String[] row : cellValues) {
        writer.writeNext(row, false);
          }




        writer.close();
}




        //System.out.println(fsdsd);

















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
            System.out.println("не правильный ввод данних в поле \"Price\"");
        }

        return bigDecimal;

    }

}