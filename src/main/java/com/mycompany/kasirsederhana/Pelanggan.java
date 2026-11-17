/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// Pelanggan mewarisi Orang DAN mengimplementasikan dua interface sekaligus
public class Pelanggan extends Orang implements Diskon, Dapatdicetak {
    public static final double PERSEN_MEMBER = 10; // konstanta

    private boolean member;

    public Pelanggan(String nama, boolean member) {
        super(nama);
        this.member = member;
    }

    // Constructor overloading: pelanggan umum (bukan member)
    public Pelanggan(String nama) {
        this(nama, false);
    }

    public boolean isMember() {
        return member;
    }

    @Override
    public String getPeran() {
        return member ? "Pelanggan Member" : "Pelanggan Umum";
    }

    @Override
    public double hitungDiskon(double total) {
        return member ? total * PERSEN_MEMBER / 100.0 : 0;
    }

    @Override
    public String namaDiskon() {
        return member ? "Diskon Member " + (int) PERSEN_MEMBER + "%" : "Tanpa Diskon";
    }

    @Override
    public void cetak() {
        System.out.println("--- Kartu Pelanggan ---");
        tampilkanIdentitas();
    }
}
