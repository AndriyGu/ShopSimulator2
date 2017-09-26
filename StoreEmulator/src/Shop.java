/**
 * Created by AdanaC on 20.09.2017.
 */

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Shop {
    Calendar c = Calendar.getInstance();
    CSVUtils csvUtils = new CSVUtils();
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public BigDecimal cash = BigDecimal.valueOf(0.0);
    public BigDecimal allSupplyCost = BigDecimal.valueOf(0.0);


   // ArrayList<Product> productListWerehouse = new ArrayList<Product>();
    Map<Integer, Product> productListWerehouse = new HashMap<Integer, Product>();


    public void shopWorks() {


        File file = new File(".\\src\\resources\\werehouse.CSV");

        csvUtils.parserWarehouse(file, productListWerehouse);


        LocalDate today = LocalDate.now();
        LocalDate dayCH;
        dayCH=today;


        for(int day = 1; day<30; day++){


            String dayOfWeek = (dayCH.getDayOfWeek()).toString();
            System.out.println(ANSI_GREEN+"                                                                                           ������� ���� : " + dayCH+ " "+ dayOfWeek+ANSI_RESET);
            System.out.println();
            System.out.println();
            /**�������� � 8.00 �������� 21.00
             *
             */
            for(int hour = 8; hour< 21; hour++){


               //

                /**
                 * ������ ��� ������� ����������, ��������, ���� �� �����,
                 *
                 * ����� �����
                 *
                 * �������� �������� �����������
                 */

                int buyer = ((int) (Math.random() * (10))+1);
                System.out.println("    � " + hour+ ".00 ����� "+ buyer+ " �����������");
                for (int i = 0; i<buyer; i++){
                    System.out.println("       ���������� � "+(i+1));

                    List<Product> productListPurshes= preparePurchases(productListWerehouse);//����� ������� �����������

                    cash = cashRegister(hour,productListPurshes,productListWerehouse,dayCH,cash);
                  //  System.out.println("Cash 2 "+cash);
                }
            }
            supplyMetod(productListWerehouse);
            dayCH=dayCH.plusDays(1);
        }



        System.out.println();
        System.out.println();
        System.out.println();


        //�������� ��������


        FileWorker fileWorker = new FileWorker();

        fileWorker.createTXTFile(createReport(productListWerehouse));


        for (int i =1; i<=productListWerehouse.size(); i++){
            System.out.println("ID "+productListWerehouse.get(i).getId()+" price "+productListWerehouse.get(i).getPrice()+" ; current "+productListWerehouse.get(i).getCurentquantity()+"; buy "+productListWerehouse.get(i).getBuyProd()+
                    "; sold "+productListWerehouse.get(i).getSoldProd()+" supplyCost "+productListWerehouse.get(i).getSpendCost());
            allSupplyCost = allSupplyCost.add(productListWerehouse.get(i).getSpendCost());
        }


        //System.out.println("count net cost "+countNetCost(productListWerehouse,cash));


        fileWorker.createTXTFile("������� �������� �� ������ "+(cash.subtract(countNetCost(productListWerehouse,cash))).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        fileWorker.createTXTFile("����������� �������� �� ��������� ������ "+allSupplyCost);
        try {


            csvUtils.createCSVFile(file, productListWerehouse);
        }
        catch (Exception E){E.printStackTrace();}
    }




    public BigDecimal cashRegister(int time, List<Product> productListPurshes, Map<Integer, Product>  productListWerehouse,
                             LocalDate date, BigDecimal cash){

        String dayOfWeek = (date.getDayOfWeek()).toString();
        BigDecimal priceForBuyer= new BigDecimal(0);
        /**
         * ������� ��������
         *
         * � �������� ��������� ����� �� ������
         */
        float margin;
        if (time >= 18 && time <= 20) { // 8%
            margin = 8f;
        } else if (dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY")) {  //15%
            margin = 15f;
        } else { // 10%
            margin = 10f;
        }
        priceForBuyer = priceForBuyer.add(processPurshes(productListPurshes, margin, productListWerehouse));

        System.out.println("��� �� ����� " + priceForBuyer.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        System.out.println();
       // System.out.println("Cash " + cash);
        return cash = cash.add(priceForBuyer);

    }

    public List<Product> preparePurchases(Map<Integer, Product> productListWerehouse){
        int assortment = productListWerehouse.size();

        List<Product> productListPurshes = new ArrayList<Product>();

        int thirst = (int) (Math.random() * 11);
        int differentWisc=0;
        int articul=0;

        if(thirst==0){

        }
        else {
            int quontity = thirst;

            List<Integer> listWish = new ArrayList<Integer>();


            while (quontity>0){
                Product product = new Product();
                articul = (int) (Math.random() * assortment+1);
                differentWisc = ((int) (Math.random() * (quontity))+1);


                if (!listWish.contains(articul)) {
                    product.setId(articul);
                    product.setCurentquantity(differentWisc);
                    product.setPrice(productListWerehouse.get(articul).getPrice());
                    product.setNameProduct(productListWerehouse.get(articul).getNameProduct());
                    listWish.add(articul);
                    quontity=quontity-differentWisc;
                   // System.out.println("art=" + product.getId() + "  quant=" + product.getCurentquantity());
                    productListPurshes.add(product);
                }
            }
        }




        return productListPurshes;
    }

    public int checkingAvailabilityOFGoods(Map<Integer, Product> productListWerehouse, Product product){

        /**
         * ��������� ���� �� ����� �� ������
         */

        if(product.getCurentquantity()<=productListWerehouse.get(product.getId()).getCurentquantity()){
            return product.getCurentquantity();
        }
        else {return productListWerehouse.get(product.getId()).getCurentquantity();}
        }

    /**����� �������� ��������   Curentquantity �� ������
     * � ��������� ����������� ���������� � SoldProd */
    public void minusGoodsFromWerehouse(Map<Integer, Product> productListWerehouse, Product productListPurshes, int quntityProdukts)  {

     //   System.out.println("                                 WWWWWWWWWWWWW            WWWWWWWWWWWW             "+productListWerehouse.get(productListPurshes.getId()).getCurentquantity()+"     "+quntityProdukts);
        productListWerehouse.get(productListPurshes.getId()).setCurentquantity(
                productListWerehouse.get(productListPurshes.getId()).getCurentquantity()-quntityProdukts);

        //��������� ����������� ��������� � SoldProd

        productListWerehouse.get(productListPurshes.getId()).setSoldProd(productListWerehouse.get(productListPurshes.getId()).getSoldProd()+quntityProdukts);
    }

    public BigDecimal getPriceOPT(Product product, int avilabelGoods, float margin){
        float normalPriceQuantity = 2f;
        float salePriceQuantity = product.getCurentquantity()-normalPriceQuantity;
        BigDecimal priceForBauerRetail=((product.getPrice().multiply(BigDecimal.valueOf(margin/100f))).add(product.getPrice()));
        BigDecimal totalPriceForBauerRetail = priceForBauerRetail.multiply(BigDecimal.valueOf(normalPriceQuantity));
        BigDecimal priceForBauerOPT= (product.getPrice().multiply(BigDecimal.valueOf(7f/100f))).add(product.getPrice());
        BigDecimal totalPriceForBauerOPT = priceForBauerOPT.multiply(BigDecimal.valueOf(salePriceQuantity));
        BigDecimal allPrice = totalPriceForBauerOPT.add(totalPriceForBauerRetail);

        System.out.print("������� "+ avilabelGoods+ "��; "+product.getNameProduct()+" �� ����� "+allPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"; ");
        System.out.println( "�� ��� 2 ����� �� ���� " + priceForBauerRetail.setScale(2, BigDecimal.ROUND_HALF_UP).toString() +"(������� "+margin+"%) � "+salePriceQuantity+"" +
                " ����(�) �� ���� "+ priceForBauerOPT.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"(������� ������� 7%)");

        return allPrice;

    }

    public BigDecimal getPriceRetail(Product product, int avilabelGoods, float margin){
        BigDecimal priceForBauerRetail=((product.getPrice().multiply(BigDecimal.valueOf(margin/100f))).add(product.getPrice()));
        BigDecimal totalPriceForBauerRetail = priceForBauerRetail.multiply(BigDecimal.valueOf(avilabelGoods));
        System.out.println("������� "+ avilabelGoods+ "��; "+product.getNameProduct()+" �� ����� "+totalPriceForBauerRetail.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"; �� ���� " + priceForBauerRetail.setScale(2, BigDecimal.ROUND_HALF_UP).toString() +"(������� "+margin+"%)");

        return totalPriceForBauerRetail;
    }

    public BigDecimal processPurshes(List<Product> productListPurshes, float margin, Map<Integer, Product> productListWerehouse){
        BigDecimal priceForBauer = BigDecimal.valueOf(0);

        for(Product product : productListPurshes) {

            //��������� ������� ������, ���� �� ������ ������ ��� ����� ������ ����������, ���������� "�����" ����������
            // ����� �������

            int avilabelGoods = checkingAvailabilityOFGoods(productListWerehouse, product);


            // ���� ������� ������� �� ... �����... ����� "����� ����������"

            if (avilabelGoods > 0) {
                if (avilabelGoods > 2) {
                    priceForBauer = priceForBauer.add(getPriceOPT(product, avilabelGoods, margin));
                } else {
                    priceForBauer = priceForBauer.add(getPriceRetail(product, avilabelGoods, margin));
                }
                minusGoodsFromWerehouse(productListWerehouse, product, avilabelGoods);
            } else {
                System.out.println(product.getNameProduct() + " -- ����������");
            }
            //������� ��������� � �������� ������



        }

        return priceForBauer;

    }

    /**   ����� �������� ����� � ����� �������� ��� */
    public void supplyMetod(Map<Integer, Product> productListWerehouse){
        int prodListLengs = productListWerehouse.size();
        int purQount = 150; // ����� ������ ��� �������

        for (int i = 1; i<=prodListLengs; i++){

            if(productListWerehouse.get(i).getCurentquantity()<10){

                int getCurentquantity=productListWerehouse.get(i).getCurentquantity();
                //�������� ������
                BigDecimal cost = productListWerehouse.get(i).getPrice().multiply(BigDecimal.valueOf(purQount));

                //��������� ���������� ������ �� �������
                productListWerehouse.get(i).setSpendCost(productListWerehouse.get(i).getSpendCost().add(cost));

                //��������� ��������� �����
                productListWerehouse.get(i).setCurentquantity(productListWerehouse.get(i).getCurentquantity()+purQount);

                //������� � ����� �������
                productListWerehouse.get(i).setBuyProd(productListWerehouse.get(i).getBuyProd()+purQount);

                System.out.println(productListWerehouse.get(i).getId()+" ����,"+getCurentquantity+" ��������� "+purQount+" ������ � ������� " +
                        " "+productListWerehouse.get(i).getCurentquantity()+" ��������� "+cost);
            }

        }

    }


    /**����� ������� ������������� ��������� ������      */
   public BigDecimal countNetCost(Map<Integer,Product> productWarehouse, BigDecimal cash){
       BigDecimal netCost = BigDecimal.valueOf(0.0);
       BigDecimal netCosttemp = BigDecimal.valueOf(0.0);

       for (int i = 1; i<=productWarehouse.size(); i++){
           netCosttemp = netCosttemp.add((productWarehouse.get(i).getPrice()).multiply(BigDecimal.valueOf(productWarehouse.get(i).getSoldProd())));
           //System.out.println("net cost "+netCosttemp);
          // System.out.println("price "+productWarehouse.get(i).getPrice()+" sold "+productWarehouse.get(i).getSoldProd());
       }

        return netCosttemp;
   }






    public List<String> createReport(Map<Integer, Product> productListWerehouse){
        List<String> toRaportList = new ArrayList<>();

        for (int i = 1; i<= productListWerehouse.size(); i++) {
            toRaportList.add("ID " + productListWerehouse.get(i).getId() + "; current "
                    + productListWerehouse.get(i).getCurentquantity() + "; buy " + productListWerehouse.get(i).getBuyProd() +
                    "; sold " + productListWerehouse.get(i).getSoldProd());


        }
            return  toRaportList;
    }

}
