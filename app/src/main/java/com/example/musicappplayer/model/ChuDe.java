package com.example.musicappplayer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class ChuDe {

@SerializedName("IdChuDe")
@Expose
private String idChuDe;
@SerializedName("TenChuDe")
@Expose
private String tenChuDe;
@SerializedName("HinhCHuDe")
@Expose
private String hinhCHuDe;

public String getIdChuDe() {
return idChuDe;
}

public void setIdChuDe(String idChuDe) {
this.idChuDe = idChuDe;
}

public String getTenChuDe() {
return tenChuDe;
}

public void setTenChuDe(String tenChuDe) {
this.tenChuDe = tenChuDe;
}

public String getHinhCHuDe() {
return hinhCHuDe;
}

public void setHinhCHuDe(String hinhCHuDe) {
this.hinhCHuDe = hinhCHuDe;
}

}