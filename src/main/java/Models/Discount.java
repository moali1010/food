package Models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Discount {
    public Discount(Integer id, String code, Double amount, LocalDate expireDate, Integer userId) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.expireDate = expireDate;
        this.userId = userId;
        this.discountType = DiscountType.AMOUNT;
    }

    private int id;
    private String code;
    private Integer userId;
    private DiscountType discountType;
    private LocalDate expireDate;
    private Double amount;
    private Integer percentage;
    private Boolean isUsed = false;

    public Discount(Integer id, String code, Integer percentage, LocalDate expireDate, Integer userId) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
        this.expireDate = expireDate;
        this.userId = userId;
        this.discountType = DiscountType.PERCENTAGE;
    }

    public enum DiscountType {
        PERCENTAGE,
        AMOUNT
    }
}
