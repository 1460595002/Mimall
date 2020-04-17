package cn.jinronga.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 11:20
 * E-mail:1460595002@qq.com
 * 类说明:产品实体类
 */
public class Product {


    /**
     * 价格用  BigDecimal来定义 这里为了偷懒不改了
     */

    private int id;
    //产品名
    private  String name;
    //产品标题
    private String subTitle;
    //原始价格
    private float orignalPrice;
     //优惠价格
    private float promotePrice;
    //库存
    private int stock;
     //上线时间
    private Date createDate;

    //产品分类   产品跟分类是多对一的关系
    private Category category;

    //产品默认图片
    private ProductImage fisrtproductImage;
    //产品图片集合
    private ProductImage productImage;
    //产品下的单个图片集合
    private List<ProductImage> productSingleImages;
    //产品详情图片集合
    private List<ProductImage> productDetailImages;
    //产品的评价总数
    private int reviewCount;
    //销量总数
    private  int saleCount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public float getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(float orignalPrice) {
        this.orignalPrice = orignalPrice;
    }

    public float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFisrtproductImage() {
        return fisrtproductImage;
    }

    public void setFisrtproductImage(ProductImage fisrtproductImage) {
        this.fisrtproductImage = fisrtproductImage;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
}
