package com.connyay.domainr.gson;

import android.os.Parcel;
import android.os.Parcelable;

public class Registrars implements Parcelable {
    private String registrar;
    private String name;
    private String register_url;

    public Registrars() {

    }

    public Registrars(Parcel in) {
	readFromParcel(in);
    }

    public String getRegistrar() {
	return registrar;
    }

    public void setRegistrar(String registrar) {
	this.registrar = registrar;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getRegister_url() {
	return register_url;
    }

    public void setRegister_url(String register_url) {
	this.register_url = register_url;
    }

    @Override
    public int describeContents() {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	dest.writeString(registrar);
	dest.writeString(name);
	dest.writeString(register_url);
    }

    private void readFromParcel(Parcel in) {
	registrar = in.readString();
	name = in.readString();
	register_url = in.readString();
    }

    public static final Parcelable.Creator<Registrars> CREATOR = new Parcelable.Creator<Registrars>() {
	public Registrars createFromParcel(Parcel in) {
	    return new Registrars(in);
	}

	public Registrars[] newArray(int size) {
	    return new Registrars[size];
	}
    };
}
