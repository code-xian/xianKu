package zzx.jxc.stockLog.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
@Data
@DynamicUpdate
public class StockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String foodName;
    private String foodId;
    private String stockName;
    private String stockId;
    private String documentNumber;
    private Integer documentType;
    private String incOrDec;
    private Integer quantity;
    private Integer restStock;
    private Date createTime;
    private Date updateTime;
}
