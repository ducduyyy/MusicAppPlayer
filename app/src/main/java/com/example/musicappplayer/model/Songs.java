package com.example.musicappplayer.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Songs implements Parcelable{

@SerializedName("IdBaiHat")
@Expose
private String idBaiHat;
@SerializedName("IdAlbum")
@Expose
private String idAlbum;
@SerializedName("IdTheLoai")
@Expose
private String idTheLoai;
@SerializedName("IdPlayList")
@Expose
private String idPlayList;
@SerializedName("TenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("HinhBaiHat")
@Expose
private String hinhBaiHat;
@SerializedName("Casi")
@Expose
private String casi;
@SerializedName("LinkBaiHat")
@Expose
private String linkBaiHat;
@SerializedName("LuotThich")
@Expose
private String luotThich;
@Expose
private String songs;

    public Songs(String idBaiHat, String idAlbum, String idTheLoai, String idPlayList, String tenBaiHat, String hinhBaiHat, String casi, String linkBaiHat, String luotThich, String songs) {
        this.idBaiHat = idBaiHat;
        this.idAlbum = idAlbum;
        this.idTheLoai = idTheLoai;
        this.idPlayList = idPlayList;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.casi = casi;
        this.linkBaiHat = linkBaiHat;
        this.luotThich = luotThich;
        this.songs = songs;
    }

    public Songs(String songname, String songspath) {
    }

    public void setSongs(String songs) {this.songs = songs;}
public String getSongs() {return songs;}
    public String getIdBaiHat() {
        return idBaiHat;
    }
public void setIdBaiHat(String idBaiHat) {
this.idBaiHat = idBaiHat;
}

public String getIdAlbum() {
return idAlbum;
}

public void setIdAlbum(String idAlbum) {
this.idAlbum = idAlbum;
}

public String getIdTheLoai() {
return idTheLoai;
}

public void setIdTheLoai(String idTheLoai) {
this.idTheLoai = idTheLoai;
}

public String getIdPlayList() {
return idPlayList;
}

public void setIdPlayList(String idPlayList) {
this.idPlayList = idPlayList;
}

public String getTenBaiHat() {
return tenBaiHat;
}

public void setTenBaiHat(String tenBaiHat) {
this.tenBaiHat = tenBaiHat;
}

public String getHinhBaiHat() {
return hinhBaiHat;
}

public void setHinhBaiHat(String hinhBaiHat) {
this.hinhBaiHat = hinhBaiHat;
}

public String getCasi() {
return casi;
}

public void setCasi(String casi) {
this.casi = casi;
}

public String getLinkBaiHat() {
return linkBaiHat;
}

public void setLinkBaiHat(String linkBaiHat) {
this.linkBaiHat = linkBaiHat;
}

public String getLuotThich() {
return luotThich;
}

public void setLuotThich(String luotThich) {
this.luotThich = luotThich;
}

    protected Songs(Parcel in){
        idBaiHat = in.readString();
       idAlbum = in.readString();
        idTheLoai = in.readString();
        idPlayList = in.readString();
        tenBaiHat = in.readString();
        hinhBaiHat = in.readString();
        casi = in.readString();
        linkBaiHat = in.readString();
        luotThich = in.readString();

    }

    public Songs(Songs songs){
        this.idBaiHat = songs.getIdBaiHat();
        this.idAlbum = songs.getIdAlbum();
        this.idTheLoai = songs.getIdTheLoai();
        this.idPlayList = songs.getIdPlayList();
        this.tenBaiHat = songs.getTenBaiHat();
        this.hinhBaiHat = songs.getHinhBaiHat();
        this.casi = songs.getCasi();
        this.linkBaiHat = songs.getLinkBaiHat();
        this.luotThich = songs.getLuotThich();
    }

    public Songs() {
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(idBaiHat);
        dest.writeString(idAlbum);
        dest.writeString(idTheLoai);
        dest.writeString(idPlayList);
        dest.writeString(tenBaiHat);
        dest.writeString(hinhBaiHat);
        dest.writeString(casi);
        dest.writeString(linkBaiHat);
        dest.writeString(luotThich);
    }

}
