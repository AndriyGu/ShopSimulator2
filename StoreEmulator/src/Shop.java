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


    private BigDecimal cash = BigDecimal.valueOf(0.0);
   // ArrayList<Product> productListWerehouse = new ArrayList<Product>();
    Map<Integer, Product> productListWerehouse = new HashMap<Integer, Product>();


    public void shopWorks(){
        File file = new File(".\\src\\resoueces\\werehouse.CSV");
        csvUtils.parserWarehouse(file, productListWerehouse);
        LocalDate today = LocalDate.now();
        LocalDate dayCH;
        dayCH=today;


        for(int day = 1; day<5; day++){
            dayCH=dayCH.plusDays(day-1);
            String dayOfWeek = (dayCH.getDayOfWeek()).toString();
            System.out.println("                                                                                           ������� ���� : " + dayCH+ " "+ dayOfWeek);

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

                    cashRegister(hour,productListPurshes,productListWerehouse,dayCH,cash);

                }

            }
            supplyMetod(productListWerehouse);

           // System.out.println("����� "+day+" ����� "+dayCH);
        }





        System.out.println("Prod List Werehouse "+ productListWerehouse.size());

        System.out.println("Cash"+cash);
        //�������� ��������


    }




    public void cashRegister(int time, List<Product> productListPurshes, Map<Integer, Product>  productListWerehouse,
                             LocalDate date, BigDecimal cash){

        String dayOfWeek = (date.getDayOfWeek()).toString();
        BigDecimal priceForBauer= new BigDecimal(0);
        /**
         * ������� ��������
         *
         * � �������� ��������� ����� �� ������
         */

        if (time >= 18 && time <= 20) { // 8%
            float margin = 8f;
            priceForBauer = priceForBauer.add(processPurshes(productListPurshes, margin, productListWerehouse));


            System.out.println("��� �� ����� " + priceForBauer);
        } else if (dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY")) {  //15%
            float margin = 15f;

            priceForBauer = priceForBauer.add(processPurshes(productListPurshes, margin, productListWerehouse));
            System.out.println("��� �� ����� " + priceForBauer);
        } else { // 10%
            float margin = 10f;

            priceForBauer = priceForBauer.add(processPurshes(productListPurshes, margin, productListWerehouse));
            System.out.println("��� �� ����� " + priceForBauer);
        }
        cash=cash.add(priceForBauer);
    }

    public List<Product> preparePurchases(Map<Integer, Product> productListWerehouse){
        int assortment = productListWerehouse.size();

        List<Product> productListPurshes = new ArrayList<Product>();

        int thirst = (int) (Math.random() * 11);
        int differentWisc=0;
        int articul=0;
        System.out.println("thirst="+thirst);
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
                    System.out.println("art=" + product.getId() + "  quant=" + product.getCurentquantity());
                    productListPurshes.add(product);
                }
            }
        }

        System.out.println("List size " + productListPurshes.size());
        System.out.println("1)  = "+thirst+" 2) = "+ differentWisc+ "  3) = "+ articul);


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

        System.out.println("                                 WWWWWWWWWWWWW            WWWWWWWWWWWW             "+productListWerehouse.get(productListPurshes.getId()).getCurentquantity()+"     "+quntityProdukts);
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

        System.out.print("������� "+ avilabelGoods+ "��; "+product.getNameProduct()+"�� ����� "+allPrice+"; ");
        System.out.println( "�� ��� 2 ����� �� ���� " + priceForBauerRetail +"(������� "+margin+"%) � "+salePriceQuantity+"" +
                " ����� �� ���� "+ priceForBauerOPT+"(������� ������� 7%)");

        return allPrice;

    }

    public BigDecimal getPriceRetail(Product product, int avilabelGoods, float margin){
        BigDecimal priceForBauerRetail=((product.getPrice().multiply(BigDecimal.valueOf(margin/100f))).add(product.getPrice()));
        BigDecimal totalPriceForBauerRetail = priceForBauerRetail.multiply(BigDecimal.valueOf(avilabelGoods));
        System.out.println("������� "+ avilabelGoods+ "��; "+product.getNameProduct()+"�� ����� "+totalPriceForBauerRetail+"; �� ���� " + priceForBauerRetail +"(������� "+margin+"%)");

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
        int purQount = 150;

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
                productListWerehouse.get(i).setBuyProd(productListWerehouse.get(i).getSoldProd()+purQount);

                System.out.println(productListWerehouse.get(i).getId()+" ����,"+getCurentquantity+" ��������� "+purQount+" ������ � ������� " +
                        ""+productListWerehouse.get(i).getCurentquantity()+"���������"+cost);
            }

        }

    }




}
