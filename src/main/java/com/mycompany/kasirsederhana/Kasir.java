/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// INHERITANCE: Kasir mewarisi Orang (extends & super)
public class Kasir extends Orang {
    private String idKasir;

    public Kasir(String nama, String idKasir) {
        super(nama); // memanggil constructor Orang
        this.idKasir = idKasir;
    }

    public String getIdKasir() {
        return idKasir;
    }

    @Override
    public String getPeran() {
        return "Kasir (ID: " + idKasir + ")";
    }

    // ASSOCIATION: Kasir hanya "menggunakan" Pelanggan sesaat,
    // tanpa menyimpan atau memilikinya.
    public void layani(Pelanggan pelanggan) {
        System.out.println("Kasir " + nama + " melayani " + pelanggan.getNama() + ".");
    }
}
