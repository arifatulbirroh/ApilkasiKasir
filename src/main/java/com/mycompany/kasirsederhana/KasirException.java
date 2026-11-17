/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// CUSTOM EXCEPTION (checked): dipakai untuk semua kesalahan transaksi kasir
// (stok tidak cukup, produk tidak ditemukan, uang pembayaran kurang)
public class KasirException extends Exception {
    public KasirException(String pesan) {
        super(pesan); // meneruskan pesan ke constructor Exception
    }
}
