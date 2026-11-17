/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

import java.util.Locale;

// ENCAPSULATION: semua field private, diakses lewat getter/setter (Pertemuan 4)
public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        setHarga(harga); // memakai setter agar validasi tetap berlaku
        setStok(stok);
    }

    // Getter
    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // Setter dengan validasi
    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        }
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah tambah stok harus lebih dari 0.");
        }
        this.stok += jumlah;
    }

    // throws: method ini bisa melempar checked exception (Pertemuan 8)
    public void kurangiStok(int jumlah) throws KasirException {
        if (jumlah > stok) {
            throw new KasirException("Stok " + nama + " tidak cukup. Sisa: " + stok);
        }
        this.stok -= jumlah;
    }

    // Method yang akan di-override oleh subclass (polymorphism)
    public String getKategori() {
        return "Umum";
    }

    public String infoTambahan() {
        return "-";
    }

    public void tampilkanInfo() {
        System.out.printf("%-6s | %-18s | %-8s | %11s | %4d | %s%n",
                kode, nama, getKategori(), rp(harga), stok, infoTambahan());
    }

    // Method static: format angka menjadi rupiah
    public static String rp(double nilai) {
        return "Rp" + String.format(Locale.US, "%,.0f", nilai).replace(',', '.');
    }
}
