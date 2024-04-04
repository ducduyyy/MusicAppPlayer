package com.example.musicappplayer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Album implements Serializable {

@SerializedName("Idalbum")
@Expose
private String idalbum;
@SerializedName("TenAlbum")
@Expose
private String tenAlbum;
@SerializedName("TenCASiAlbum")
@Expose
private String tenCASiAlbum;
@SerializedName("HinhAlbum")
@Expose
private String hinhAlbum;

public String getIdalbum() {
return idalbum;
}

public void setIdalbum(String idalbum) {
this.idalbum = idalbum;
}

public String getTenAlbum() {
return tenAlbum;
}

public void setTenAlbum(String tenAlbum) {
this.tenAlbum = tenAlbum;
}

public String getTenCASiAlbum() {
return tenCASiAlbum;
}

public void setTenCASiAlbum(String tenCASiAlbum) {
this.tenCASiAlbum = tenCASiAlbum;
}

public String getHinhAlbum() {
return hinhAlbum;
}

public void setHinhAlbum(String hinhAlbum) {
this.hinhAlbum = hinhAlbum;
}

}