/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// ABSTRACT CLASS: induk dari Kasir dan Pelanggan (Pertemuan 7)
public abstract class Orang {
    protected String nama; // protected agar bisa diakses subclass (Pertemuan 5)

    public Orang(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    // Abstract method: wajib di-override oleh subclass
    public abstract String getPeran();

    // Method konkret yang memanggil abstract method (polymorphism)
    public void tampilkanIdentitas() {
        System.out.println("Nama : " + nama);
        System.out.println("Peran: " + getPeran());
    }
}
