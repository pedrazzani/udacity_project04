package br.com.pedrazzani.android.mymusicapp.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pedrazzani on 28/01/2017.
 */

public class
Album implements Parcelable {

    private String nome;
    private String artista;
    private String urlImageSmall;
    private String urlImageMedium;
    private String urlImageLarge;

    public Album() {
    }

    private Album(Parcel from) {
        artista = from.readString();
        nome = from.readString();
        urlImageLarge = from.readString();
        urlImageMedium = from.readString();
        urlImageLarge = from.readString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getUrlImageSmall() {
        return urlImageSmall;
    }

    public void setUrlImageSmall(String urlImageSmall) {
        this.urlImageSmall = urlImageSmall;
    }

    public String getUrlImageMedium() {
        return urlImageMedium;
    }

    public void setUrlImageMedium(String urlImageMedium) {
        this.urlImageMedium = urlImageMedium;
    }

    public String getUrlImageLarge() {
        return urlImageLarge;
    }

    public void setUrlImageLarge(String urlImageLarge) {
        this.urlImageLarge = urlImageLarge;
    }

    public static final Parcelable.Creator<Album>
            CREATOR = new Parcelable.Creator<Album>() {

        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artista);
        dest.writeString(nome);
        dest.writeString(urlImageLarge);
        dest.writeString(urlImageMedium);
        dest.writeString(urlImageSmall);
    }
}
