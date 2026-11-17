/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

import java.util.LinkedHashMap;
import java.util.Map;

public class Transaksi implements Dapatdicetak {
    private static int counter = 0; // static: dipakai bersama semua objek

    private final int idTransaksi;
    private final Kasir kasir;         // AGGREGATION: dibuat di luar, lalu dititipkan
    private final Pelanggan pelanggan; // AGGREGATION
    // COMPOSITION: keranjang dibuat di dalam Transaksi dan ikut hilang bersamanya
    private final Map<Produk, Integer> keranjang = new LinkedHashMap<>();
    private boolean lunas = false;
    private double uangDibayar = 0;

    public Transaksi(Kasir kasir, Pelanggan pelanggan) {
        counter++;
        this.idTransaksi = counter;
        this.kasir = kasir;
        this.pelanggan = pelanggan;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public boolean isLunas() {
        return lunas;
    }

    public boolean isKosong() {
        return keranjang.isEmpty();
    }

    // METHOD OVERLOADING #1: dengan jumlah
    public void tambahItem(Produk produk, int jumlah) throws KasirException {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }
        int sudahAda = keranjang.getOrDefault(produk, 0);
        if (sudahAda + jumlah > produk.getStok()) {
            throw new KasirException("Stok " + produk.getNama() + " tidak cukup. Tersedia: "
                    + produk.getStok() + ", di keranjang: " + sudahAda + ".");
        }
        keranjang.put(produk, sudahAda + jumlah);
    }

    // METHOD OVERLOADING #2: tanpa jumlah (otomatis 1)
    public void tambahItem(Produk produk) throws KasirException {
        tambahItem(produk, 1);
    }

    public double hitungSubtotal() {
        double total = 0;
        for (Map.Entry<Produk, Integer> e : keranjang.entrySet()) {
            total += e.getKey().getHarga() * e.getValue();
        }
        return total;
    }

    public double hitungDiskon() {
        Diskon diskon = pelanggan; // upcasting ke tipe interface (polymorphism)
        return diskon.hitungDiskon(hitungSubtotal());
    }

    public double hitungTotalBayar() {
        return hitungSubtotal() - hitungDiskon();
    }

    // Mengembalikan kembalian; melempar exception bila uang kurang
    public double bayar(double uang) throws KasirException {
        double total = hitungTotalBayar();
        if (uang < total) {
            throw new KasirException("Uang kurang " + Produk.rp(total - uang)
                    + ". Total bayar: " + Produk.rp(total));
        }
        for (Map.Entry<Produk, Integer> e : keranjang.entrySet()) {
            e.getKey().kurangiStok(e.getValue());
        }
        this.uangDibayar = uang;
        this.lunas = true;
        return uang - total;
    }

    @Override
    public void cetak() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("            STRUK PEMBELIAN");
        System.out.println("==========================================");
        System.out.println("No. Transaksi : #" + idTransaksi);
        System.out.println("Kasir         : " + kasir.getNama());
        System.out.println("Pelanggan     : " + pelanggan.getNama());
        System.out.println("------------------------------------------");
        for (Map.Entry<Produk, Integer> e : keranjang.entrySet()) {
            Produk p = e.getKey();
            int jml = e.getValue();
            System.out.printf("%-20s %3d x %10s%n", p.getNama(), jml, Produk.rp(p.getHarga()));
            System.out.printf("%36s%n", Produk.rp(p.getHarga() * jml));
        }
        System.out.println("------------------------------------------");
        System.out.printf("%-20s %17s%n", "Subtotal", Produk.rp(hitungSubtotal()));
        System.out.printf("%-20s %17s%n", pelanggan.namaDiskon(), "-" + Produk.rp(hitungDiskon()));
        System.out.printf("%-20s %17s%n", "TOTAL", Produk.rp(hitungTotalBayar()));
        if (lunas) {
            System.out.printf("%-20s %17s%n", "Bayar", Produk.rp(uangDibayar));
            System.out.printf("%-20s %17s%n", "Kembali", Produk.rp(uangDibayar - hitungTotalBayar()));
        }
        System.out.println("==========================================");
        System.out.println("       Terima kasih atas kunjungan Anda");
        System.out.println();
    }
}
