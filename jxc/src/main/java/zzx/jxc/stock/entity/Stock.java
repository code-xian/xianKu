package zzx.jxc.stock.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Stock {

    @Id
    private String stockId;
    private String stockFzr;
    private String stockType;
    private String stockName;
    private String stockNote;
}
