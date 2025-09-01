package com.example.bitacora.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;

@Entity(tableName = "devices")
public class Dispositivo {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String  dispositivo, modelo, fabricante, ubicacion, switche, puertoSwitch, ip, gateway, mask, voltaje, alimentacion, notas;
    private int iconInt;

    public Dispositivo(String dispositivo, String modelo, String ubicacion, String alimentacion) {
        this.dispositivo = dispositivo;
        this.modelo = modelo;
        this.ubicacion = ubicacion;
        this.alimentacion = alimentacion;
    }

    public String getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(String voltaje) {
        this.voltaje = voltaje;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getUbicación() {
        return ubicacion;
    }

    public void setUbicación(String ubicación) {
        this.ubicacion = ubicación;
    }

    public String getSwitche() {
        return switche;
    }

    public void setSwitche(String switche) {
        this.switche = switche;
    }

    public String getPuertoSwitch() {
        return puertoSwitch;
    }

    public void setPuertoSwitch(String puertoSwitch) {
        this.puertoSwitch = puertoSwitch;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getAlimentación() {
        return alimentacion;
    }

    public void setAlimentación(String alimentación) {
        this.alimentacion = alimentación;
    }

    public int getIconInt() {
        return iconInt;
    }

    public void setIconInt(int iconInt) {
        this.iconInt = iconInt;
    }

}
