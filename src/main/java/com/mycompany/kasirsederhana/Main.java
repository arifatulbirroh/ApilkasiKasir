/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final List<Produk> daftarProduk = new ArrayList<>();
    private static final List<Transaksi> riwayat = new ArrayList<>();

    // ---------- Helper input ----------
    private static String bacaTeks(String pesan) {
        System.out.print(pesan);
        if (!input.hasNextLine()) {
            System.out.println("\nInput berakhir. Program ditutup.");
            System.exit(0);
        }
        return input.nextLine().trim();
    }

    private static int bacaInt(String pesan) {
        while (true) {
            try {
                return Integer.parseInt(bacaTeks(pesan));
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    private static double bacaDouble(String pesan) {
        while (true) {
            try {
                return Double.parseDouble(bacaTeks(pesan));
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Coba lagi.");
            }
        }
    }

    // ---------- Data produk ----------
    private static void isiDataAwal() {
        // Upcasting: ProdukMakanan disimpan sebagai tipe Produk
        daftarProduk.add(new ProdukMakanan("M001", "Roti Tawar", 15000, 20, "30-12-2026"));
        daftarProduk.add(new ProdukMakanan("M002", "Mie Instan", 3500, 100, "01-06-2027"));
        daftarProduk.add(new Produk("D001", "Air Mineral", 4000, 50));
        daftarProduk.add(new Produk("D002", "Teh Botol", 6000, 40));
        daftarProduk.add(new Produk("E001", "Charger HP", 75000, 10));
        daftarProduk.add(new Produk("E002", "Earphone", 50000, 8));
    }

    private static Produk cariProduk(String kode) throws KasirException {
        for (Produk p : daftarProduk) {
            if (p.getKode().equalsIgnoreCase(kode.trim())) {
                return p;
            }
        }
        throw new KasirException("Produk dengan kode '" + kode + "' tidak ditemukan.");
    }

    private static void tampilkanSemuaProduk() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-6s | %-18s | %-8s | %11s | %4s | %s%n",
                "KODE", "NAMA", "KATEGORI", "HARGA", "STOK", "INFO");
        System.out.println("-------------------------------------------------------------------------");
        for (Produk p : daftarProduk) {
            p.tampilkanInfo(); // polymorphism: info tergantung jenis produk
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    // ---------- Menu: tambah produk ----------
    private static void menuTambahProduk() {
        System.out.println("\n--- Tambah Produk Baru ---");
        System.out.println("1. Makanan");
        System.out.println("2. Produk Umum");
        int jenis = bacaInt("Pilih jenis (1-2): ");
        if (jenis != 1 && jenis != 2) {
            System.out.println("Jenis tidak valid.");
            return;
        }
        String kode = bacaTeks("Kode produk : ");
        String nama = bacaTeks("Nama produk : ");
        if (kode.isEmpty() || nama.isEmpty()) {
            System.out.println("Kode dan nama tidak boleh kosong.");
            return;
        }
        double harga = bacaDouble("Harga       : ");
        int stok = bacaInt("Stok        : ");

        try {
            Produk baru; // upcasting
            if (jenis == 1) {
                String exp = bacaTeks("Kadaluarsa (dd-mm-yyyy): ");
                baru = new ProdukMakanan(kode, nama, harga, stok, exp);
            } else {
                baru = new Produk(kode, nama, harga, stok);
            }
            daftarProduk.add(baru);
            System.out.println("Produk berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah produk: " + e.getMessage());
        }
    }

    // ---------- Menu: tambah stok ----------
    private static void menuTambahStok() {
        System.out.println("\n--- Tambah Stok ---");
        String kode = bacaTeks("Kode produk : ");
        try {
            Produk p = cariProduk(kode);
            int jumlah = bacaInt("Jumlah tambah stok untuk " + p.getNama() + ": ");
            p.tambahStok(jumlah);
            System.out.println("Stok " + p.getNama() + " sekarang: " + p.getStok());
        } catch (KasirException | IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // ---------- Menu: transaksi baru ----------
    private static void menuTransaksi(Kasir kasir) {
        System.out.println("\n--- Transaksi Baru ---");
        String namaPelanggan = bacaTeks("Nama pelanggan: ");
        if (namaPelanggan.isEmpty()) {
            namaPelanggan = "Umum";
        }
        boolean member = bacaTeks("Member? (y/n): ").equalsIgnoreCase("y");

        Pelanggan pelanggan = new Pelanggan(namaPelanggan, member);
        kasir.layani(pelanggan);                         // association
        Transaksi trx = new Transaksi(kasir, pelanggan); // aggregation

        tampilkanSemuaProduk();
        System.out.println("Ketik kode produk untuk menambah ke keranjang, 'selesai' untuk checkout.");

        while (true) {
            String kode = bacaTeks("\nKode produk (selesai/batal): ");
            if (kode.equalsIgnoreCase("selesai")) {
                break;
            }
            if (kode.equalsIgnoreCase("batal")) {
                System.out.println("Transaksi dibatalkan.");
                return;
            }
            try {
                Produk p = cariProduk(kode);
                int jumlah = bacaInt("Jumlah " + p.getNama() + ": ");
                trx.tambahItem(p, jumlah);
                System.out.println(jumlah + " x " + p.getNama() + " ditambahkan. Subtotal sementara: "
                        + Produk.rp(trx.hitungSubtotal()));
            } catch (KasirException e) {
                System.out.println("Gagal: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input tidak valid: " + e.getMessage());
            }
        }

        if (trx.isKosong()) {
            System.out.println("Keranjang kosong. Transaksi dibatalkan.");
            return;
        }

        trx.cetak(); // ringkasan sebelum bayar

        // Pembayaran dengan try-catch-finally
        while (!trx.isLunas()) {
            double uang = bacaDouble("Uang dibayar (0 untuk batal): ");
            if (uang == 0) {
                System.out.println("Pembayaran dibatalkan. Transaksi tidak disimpan.");
                return;
            }
            try {
                double kembalian = trx.bayar(uang);
                System.out.println("Pembayaran berhasil. Kembalian: " + Produk.rp(kembalian));
                riwayat.add(trx);
                trx.cetak();
            } catch (KasirException e) {
                System.out.println("Pembayaran gagal: " + e.getMessage());
            } finally {
                System.out.println("[Proses pembayaran selesai diperiksa]");
            }
        }
    }

    // ---------- Menu: riwayat ----------
    private static void menuRiwayat() {
        System.out.println("\n--- Riwayat Transaksi ---");
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        double omzet = 0;
        for (Transaksi t : riwayat) {
            System.out.println("Transaksi #" + t.getIdTransaksi()
                    + " | Total: " + Produk.rp(t.hitungTotalBayar()));
            omzet += t.hitungTotalBayar();
        }
        System.out.println("Total pendapatan: " + Produk.rp(omzet));
    }

    // ---------- Menu: demo polymorphism ----------
    private static void menuIdentitas(Kasir kasir) {
        System.out.println("\n--- Demo Polymorphism: Daftar Orang ---");
        // Array bertipe Orang menampung Kasir dan Pelanggan (upcasting)
        Orang[] daftar = {
            kasir,
            new Pelanggan("Contoh Pelanggan"),
            new Pelanggan("Contoh Member", true)
        };
        for (Orang o : daftar) {
            o.tampilkanIdentitas(); // getPeran() berbeda-beda tiap objek
            if (o instanceof Dapatdicetak) {
                System.out.println("(objek ini dapat dicetak)");
            }
            System.out.println();
        }
    }

    // ---------- main ----------
    public static void main(String[] args) {
        isiDataAwal();

        System.out.println("==========================================");
        System.out.println("        APLIKASI KASIR SEDERHANA");
        System.out.println("==========================================");

        String namaKasir = bacaTeks("Masukkan nama kasir: ");
        if (namaKasir.isEmpty()) {
            namaKasir = "Kasir";
        }
        Kasir kasir = new Kasir(namaKasir, "KSR-01");
        System.out.println();
        kasir.tampilkanIdentitas();

        boolean jalan = true;
        while (jalan) {
            System.out.println("\n========== MENU UTAMA ==========");
            System.out.println("1. Lihat Daftar Produk");
            System.out.println("2. Transaksi Baru");
            System.out.println("3. Tambah Produk Baru");
            System.out.println("4. Tambah Stok Produk");
            System.out.println("5. Riwayat Transaksi");
            System.out.println("6. Demo Polymorphism");
            System.out.println("0. Keluar");
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilkanSemuaProduk();
                    break;
                case 2:
                    menuTransaksi(kasir);
                    break;
                case 3:
                    menuTambahProduk();
                    break;
                case 4:
                    menuTambahStok();
                    break;
                case 5:
                    menuRiwayat();
                    break;
                case 6:
                    menuIdentitas(kasir);
                    break;
                case 0:
                    jalan = false;
                    System.out.println("Terima kasih. Program selesai.");
                    break;
                default:
                    System.out.println("Menu tidak tersedia.");
            }
        }
    }
}
