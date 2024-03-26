package com.yoru.model.Entity;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {

    public static final String COLUMNLABEL1 = "ID_ordine";
    public static final String COLUMNLABEL2 = "costo_totale_ordine";
    public static final String COLUMNLABEL3 = "data_pagamento";
    public static final String COLUMNLABEL4 = "ID_pagamento";
    public static final String COLUMNLABEL5 = "importo_pagamento";
    public static final String COLUMNLABEL6 = "email";

    private int id;
    private int id_pagamento;
    private Date dataPagamento;
    private float importoPagamento;
    private float costoTotOrdine;
    private String email;

    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pagamento() {
        return id_pagamento;
    }

    public void setId_pagamento(int id_pagamento) {
        this.id_pagamento = id_pagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public float getImportoPagamento() {
        return importoPagamento;
    }

    public void setImportoPagamento(float importoPagamento) {
        this.importoPagamento = importoPagamento;
    }

    public float getCostoTotOrdine() {
        return costoTotOrdine;
    }

    public void setCostoTotOrdine(float costoTotOrdine) {
        this.costoTotOrdine = costoTotOrdine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItem> getOrderDetailList() {
        return orderItemList;
    }

    public void setOrderDetailList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }


    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", id_pagamento=" + id_pagamento +
                ", dataPagamento=" + dataPagamento +
                ", importoPagamento=" + importoPagamento +
                ", costoTotOrdine=" + costoTotOrdine +
                ", email='" + email + '\'' +
                '}';
    }
}
