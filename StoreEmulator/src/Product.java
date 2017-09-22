import java.math.BigDecimal;

/**
 * Created by AdanaC on 20.09.2017.
 */
public class Product {
    private int id;
    private int alcohol;
    private String nameProduct;
    private BigDecimal price;
    private String typeProduct;
    private String volume;
    private String composition;
    private int curentquantity;
    private int soldProd;
    private int buyProd;
    private BigDecimal spendCost;

    public int getSoldProd() {
        return soldProd;
    }

    public void setSoldProd(int soldProd) {
        this.soldProd = soldProd;
    }

    public int getBuyProd() {
        return buyProd;
    }

    public void setBuyProd(int buyProd) {
        this.buyProd = buyProd;
    }

    public BigDecimal getSpendCost() {
        return spendCost;
    }

    public void setSpendCost(BigDecimal spendCost) {
        this.spendCost = spendCost;
    }

    public Product(){};

    public Product(int id, int alcohol, String nameProduct, BigDecimal price, String typeProduct, String volume, String composition, int curentquantity) {
        this.id = id;
        this.alcohol = alcohol;
        this.nameProduct = nameProduct;
        this.price = price;
        this.typeProduct = typeProduct;
        this.volume = volume;
        this.composition = composition;
        this.curentquantity = curentquantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public int getCurentquantity() {
        return curentquantity;
    }

    public void setCurentquantity(int curentquantity) {
        this.curentquantity = curentquantity;
    }
}
