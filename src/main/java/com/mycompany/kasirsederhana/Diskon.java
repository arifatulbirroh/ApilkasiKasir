/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.kasirsederhana;

// INTERFACE: kontrak untuk objek yang bisa memberi diskon (Pertemuan 7)
public interface Diskon {
    double hitungDiskon(double total); // otomatis public abstract

    // Default method (Java 8+)
    default String namaDiskon() {
        return "Tanpa Diskon";
    }
}
