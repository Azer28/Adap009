/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stream.api.adapter.com.unibank;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Azer F. Mehdiyev (Azar.mehdiyev@unibank.az)
 */
public class Bill implements stream.api.Bill{

    private String id ;
    private String name;
    private Double amount;
    
    private Double min;
    private Double max;
    private Boolean isFixed;
    private String currency;
    private List<Double> fixedAmountList =
            new ArrayList();
    private String color;
    private List<stream.api.Detail> detailList =
            new ArrayList();
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Double getMin() {
        return min;
    }

    @Override
    public Double getMax() {
        return max;
    }

    @Override
    public Boolean isFixed() {
        return isFixed;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public List<Double> getFixedAmountList() {
        return fixedAmountList;
    }

    @Override
    public String getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFixedAmountList(List<Double> fixedAmountList) {
        this.fixedAmountList = fixedAmountList;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDetailList(List<stream.api.Detail> detailList) {
        this.detailList = detailList;
    }
    
    
    @Override
    public List<stream.api.Detail> getDetailList() {
        return detailList;
    }

    public void addDetail(String key, String value) {
        this.detailList.add(new Detail(key, value));
    }
}
