/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// INHERITANCE + OVERRIDING: ProdukMakanan mewarisi Produk
public class ProdukMakanan extends Produk {
    private String kadaluarsa;

    public ProdukMakanan(String kode, String nama, double harga, int stok, String kadaluarsa) {
        super(kode, nama, harga, stok); // memanggil constructor Produk
        this.kadaluarsa = kadaluarsa;
    }

    @Override
    public String getKategori() {
        return "Makanan";
    }

    @Override
    public String infoTambahan() {
        return "Exp: " + kadaluarsa;
    }
}
