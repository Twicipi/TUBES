import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManagement {
    
    // Data storage
    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int nextId = 1;
    
    // Class Product
    static class Product {
        int id;
        String nama;
        String merek;
        String kategori;
        double harga;
        int stok;
        
        Product(int id, String nama, String merek, String kategori, double harga, int stok) {
            this.id = id;
            this.nama = nama;
            this.merek = merek;
            this.kategori = kategori;
            this.harga = harga;
            this.stok = stok;
        }
        
        void displayInfo() {
            System.out.printf("| %-4d | %-20s | %-15s | %-15s | Rp %-11.0f | %-5d |\n", 
                id, nama, merek, kategori, harga, stok);
        }
    }
    
    public static void main(String[] args) {
        // Data dummy buat testing
        tambahProdukOtomatis("Laptop ASUS ROG", "ASUS", "Laptop", 15000000, 5);
        tambahProdukOtomatis("iPhone 15 Pro", "Apple", "Smartphone", 18000000, 10);
        tambahProdukOtomatis("Samsung Galaxy S24", "Samsung", "Smartphone", 12000000, 8);
        
        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println();
            
            switch(pilihan) {
                case 1:
                    tambahProduk();
                    break;
                case 2:
                    tampilkanSemuaProduk();
                    break;
                case 3:
                    editProduk();
                    break;
                case 4:
                    hapusProduk();
                    break;
                case 5:
                    // ARUL: KERJAIN BAGIAN INI RUL!
                    // Fungsi: cariProduk()
                    // - Tampilkan submenu: cari by nama, merek, atau kategori
                    // - Input keyword dari user
                    // - Loop inventory, cek yang match
                    // - Tampilkan hasil pencarian
                    // System.out.println(">>> FITUR INI DIKERJAIN ARUL <<<");
                    searchProduct();
                    break;
                case 6:
                    // Fungsi: sortProduk()
                    // - Tampilkan submenu: sort by nama, harga, atau stok
                    // - Pake Collections.sort() dengan Comparator
                    // - Tampilkan hasil sorting
                    System.out.println(">>> FITUR INI DIKERJAIN ARUL <<<");
                    break;
                case 7:
                    // Fungsi: prosesTransaksi()
                    // - Input ID produk & jumlah beli
                    // - Validasi stok cukup atau engga
                    // - Kurangi stok, hitung total harga
                    // - Tampilkan struk transaksi
                    System.out.println(">>> FITUR INI DIKERJAIN ARUL <<<");
                    break;
                case 8:
                    tampilkanLaporan();
                    break;
                case 0:
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            
            if(pilihan != 0) {
                System.out.println("\nTekan Enter untuk lanjut...");
                scanner.nextLine();
            }
            
        } while(pilihan != 0);
        
        scanner.close();
    }
    
    // ========== FUNGSI MENU ==========
    static void tampilkanMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   SISTEM MANAJEMEN INVENTARIS TOKO ELEKTRONIK  ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("1. Tambah Produk Baru");
        System.out.println("2. Tampilkan Semua Produk");
        System.out.println("3. Edit Produk");
        System.out.println("4. Hapus Produk");
        System.out.println("5. Cari Produk (ARUL)");
        System.out.println("6. Urutkan Produk (ARUL)");
        System.out.println("7. Proses Transaksi (ARUL)");
        System.out.println("8. Tampilkan Laporan");
        System.out.println("0. Keluar");
        System.out.println("════════════════════════════════════════════════");
    }
    
    // ========== FUNGSI TAMBAH PRODUK ==========
    static void tambahProduk() {
        System.out.println("═══ TAMBAH PRODUK BARU ═══");
        
        System.out.print("Nama Produk: ");
        String nama = scanner.nextLine();
        
        System.out.print("Merek: ");
        String merek = scanner.nextLine();
        
        System.out.print("Kategori: ");
        String kategori = scanner.nextLine();
        
        System.out.print("Harga: Rp ");
        double harga = scanner.nextDouble();
        
        System.out.print("Stok: ");
        int stok = scanner.nextInt();
        scanner.nextLine();
        
        Product produkBaru = new Product(nextId++, nama, merek, kategori, harga, stok);
        inventory.add(produkBaru);
        
        System.out.println("\n✓ Produk berhasil ditambahkan dengan ID: " + produkBaru.id);
    }
    
    // Helper buat data dummy
    static void tambahProdukOtomatis(String nama, String merek, String kategori, double harga, int stok) {
        inventory.add(new Product(nextId++, nama, merek, kategori, harga, stok));
    }
    
    // ========== FUNGSI TAMPILKAN SEMUA PRODUK ==========
    static void tampilkanSemuaProduk() {
        if(inventory.isEmpty()) {
            System.out.println("Inventory masih kosong!");
            return;
        }
        
        System.out.println("═══ DAFTAR SEMUA PRODUK ═══");
        tampilkanHeader();
        
        for(Product p : inventory) {
            p.displayInfo();
        }
        
        tampilkanFooter();
    }
    
    // ========== FUNGSI EDIT PRODUK ==========
    static void editProduk() {
        System.out.println("═══ EDIT PRODUK ═══");
        
        System.out.print("Masukkan ID produk yang ingin diedit: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Product produk = cariProdukById(id);
        
        if(produk == null) {
            System.out.println("✗ Produk dengan ID " + id + " tidak ditemukan!");
            return;
        }
        
        System.out.println("\nData saat ini:");
        tampilkanHeader();
        produk.displayInfo();
        tampilkanFooter();
        
        System.out.println("\nPilih data yang ingin diubah:");
        System.out.println("1. Nama");
        System.out.println("2. Merek");
        System.out.println("3. Kategori");
        System.out.println("4. Harga");
        System.out.println("5. Stok");
        System.out.println("6. Edit Semua");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 1:
                System.out.print("Nama baru: ");
                produk.nama = scanner.nextLine();
                break;
            case 2:
                System.out.print("Merek baru: ");
                produk.merek = scanner.nextLine();
                break;
            case 3:
                System.out.print("Kategori baru: ");
                produk.kategori = scanner.nextLine();
                break;
            case 4:
                System.out.print("Harga baru: Rp ");
                produk.harga = scanner.nextDouble();
                scanner.nextLine();
                break;
            case 5:
                System.out.print("Stok baru: ");
                produk.stok = scanner.nextInt();
                scanner.nextLine();
                break;
            case 6:
                System.out.print("Nama baru: ");
                produk.nama = scanner.nextLine();
                System.out.print("Merek baru: ");
                produk.merek = scanner.nextLine();
                System.out.print("Kategori baru: ");
                produk.kategori = scanner.nextLine();
                System.out.print("Harga baru: Rp ");
                produk.harga = scanner.nextDouble();
                System.out.print("Stok baru: ");
                produk.stok = scanner.nextInt();
                scanner.nextLine();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }
        
        System.out.println("\n✓ Produk berhasil diupdate!");
    }
    
    // ========== FUNGSI HAPUS PRODUK ==========
    static void hapusProduk() {
        System.out.println("═══ HAPUS PRODUK ═══");
        
        System.out.print("Masukkan ID produk yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Product produk = cariProdukById(id);
        
        if(produk == null) {
            System.out.println("✗ Produk dengan ID " + id + " tidak ditemukan!");
            return;
        }
        
        System.out.println("\nProduk yang akan dihapus:");
        tampilkanHeader();
        produk.displayInfo();
        tampilkanFooter();
        
        System.out.print("\nYakin ingin menghapus? (y/n): ");
        String konfirmasi = scanner.nextLine();
        
        if(konfirmasi.equalsIgnoreCase("y")) {
            inventory.remove(produk);
            System.out.println("✓ Produk berhasil dihapus!");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
    
    // ========== FUNGSI TAMPILKAN LAPORAN ==========
    static void tampilkanLaporan() {
        if(inventory.isEmpty()) {
            System.out.println("Inventory masih kosong!");
            return;
        }
        
        System.out.println("═══ LAPORAN INVENTARIS ═══");
        
        int totalProduk = inventory.size();
        int totalStok = 0;
        double totalNilai = 0;
        
        for(Product p : inventory) {
            totalStok += p.stok;
            totalNilai += (p.harga * p.stok);
        }
        
        System.out.println("Total Jenis Produk  : " + totalProduk);
        System.out.println("Total Stok Barang   : " + totalStok + " unit");
        System.out.printf("Total Nilai Inventory: Rp %.0f\n", totalNilai);
        
        System.out.println("\n--- Produk dengan Stok Menipis (< 5) ---");
        boolean adaStokMenipis = false;
        
        for(Product p : inventory) {
            if(p.stok < 5) {
                if(!adaStokMenipis) {
                    tampilkanHeader();
                    adaStokMenipis = true;
                }
                p.displayInfo();
            }
        }
        
        if(adaStokMenipis) {
            tampilkanFooter();
        } else {
            System.out.println("Semua produk memiliki stok yang cukup.");
        }
    }
    
    // ========== FUNGSI HELPER ==========
    static Product cariProdukById(int id) {
        for(Product p : inventory) {
            if(p.id == id) {
                return p;
            }
        }
        return null;
    }
    
    // Fungsi searchProduct
    static void searchProduct() {
        System.out.println("═══ CARI PRODUK ═══");
        System.out.println("Pilih pencarian berdasarkan:");
        System.out.println("1. ID");
        System.out.println("2. Nama");
        System.out.println("3. Merek");
        System.out.println("4. Kategori");
        System.out.println("0. Batalkan Pencarian");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\n═══ CARI PRODUK ═══");
        switch(pilihan) {
            case 0: // Cancel Searching Product
                System.out.print("[DEBUG] Pencarian product dibatalkan!");
                return;
            case 1: // ID Produk
                System.out.print("Masukkan ID: ");
                searchProductByID(scanner.nextInt());
                scanner.nextLine();
                break;
            case 2: // Nama Produk
                System.out.print("Masukkan Nama: ");
                searchProductByName(scanner.nextLine());
                break;
            case 3: // Merek Produk
                tampilkanMerekTersedia();
                System.out.print("Masukkan Merek: ");
                searchProductByBrand(scanner.nextLine());
                break;
            case 4: // Kategori Produk
                tampilkanKategoriTersedia();
                System.out.print("Masukkan Kategori: ");
                searchProductByCategory(scanner.nextLine());
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
                
        }

    }

    // Fungsi tampilkanKategoriTersdia
    static void tampilkanKategoriTersedia() {
        System.out.println("[DEBUG] Berikut adalah daftar list kategori yang tersedia:");

        ArrayList<String> listKategori = new ArrayList<>();

        for (Product p : inventory) {
            // Mencegah duplicate kategori
            boolean kategoriSudahAda = false;
            for (String ktg : listKategori) {
                // Menggunakan equalsIgnoreCase agar jika ada kategori "laptop" akan di anggap sama dgn "Laptop"
                if (ktg.equalsIgnoreCase(p.kategori)) {
                   kategoriSudahAda = true;
                   break; 
                }
            }
            
            if (!kategoriSudahAda) {
                listKategori.add(p.kategori);
            }
        }

        // Menampilkan list kategori yang tersedianya
        if (listKategori.isEmpty()) {
            System.out.println("[DEBUG] Belum ada kategori!");
        } else {
            for (int i = 0; i < listKategori.size(); i++) {
                System.out.println(" - " + listKategori.get(i));
            }
        }
    }
    
    // Fungsi tampilkaMerekiTersdia
    static void tampilkanMerekTersedia() {
        System.out.println("[DEBUG] Berikut adalah daftar list merek yang tersedia:");

        ArrayList<String> listMerek = new ArrayList<>();

        for (Product p : inventory) {
            // Mencegah duplicate merek
            boolean merekSudahAda = false;
            for (String mrk : listMerek) {
                // Menggunakan equalsIgnoreCase agar jika ada merek "laptop" akan di anggap sama dgn "Laptop"
                if (mrk.equalsIgnoreCase(p.merek)) {
                   merekSudahAda = true;
                   break; 
                }
            }
            
            if (!merekSudahAda) {
                listMerek.add(p.merek);
            }
        }

        // Menampilkan list merek yang tersedianya
        if (listMerek.isEmpty()) {
            System.out.println("[DEBUG] Belum ada merek!");
        } else {
            for (int i = 0; i < listMerek.size(); i++) {
                System.out.println(" - " + listMerek.get(i));
            }
        }
    }

    // Searching Product by ID
    static void searchProductByID(int keyword) {
        boolean keywordFound = false;
        System.out.println("[DEBUG] Hasil pencarian berdasarkan keyword id produk: ");
        for (Product p : inventory) {
            if (p.id == keyword) {
                if (!keywordFound) { // langsung stop menampilkan headernya kalau udah ketemu
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {  // menampilkan message kalo ga ketemu
            System.out.println("[DEBUG] [X] ID produk dengan keyword '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Searching Product by Name
    static void searchProductByName(String keyword) {
        boolean keywordFound = false;
        System.out.println("[DEBUG] Hasil pencarian berdasarkan keyword nama produk: ");
        for (Product p : inventory) {
            // biar ga case-sensitive make toLowerCase()
            if (p.nama.toLowerCase().contains(keyword.toLowerCase())) {
                if (!keywordFound) { // langsung stop menampilkan headernya kalau udah ketemu
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {  // menampilkan message kalo ga ketemu
            System.out.println("[DEBUG] [X] Nama produk dengan keyword '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Searching Product by Brand
    static void searchProductByBrand(String keyword) {
        boolean keywordFound = false;
        System.out.println("[DEBUG] Hasil pencarian berdasarkan keyword merek produk: ");
        for (Product p : inventory) {
            // biar ga case-sensitive make toLowerCase()
            if (p.merek.toLowerCase().equals(keyword.toLowerCase())) {
                if (!keywordFound) { // langsung stop menampilkan headernya kalau udah ketemu
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {  // menampilkan message kalo ga ketemu
            System.out.println("[DEBUG] [X] Merek produk dengan keyword '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Searching Product by Category
    static void searchProductByCategory(String keyword) {
        boolean keywordFound = false;
        System.out.println("[DEBUG] Hasil pencarian berdasarkan keyword kategori produk: ");
        for (Product p : inventory) {
            // biar ga case-sensitive make toLowerCase()
            if (p.kategori.toLowerCase().equals(keyword.toLowerCase())) {
                if (!keywordFound) { // langsung stop menampilkan headernya kalau udah ketemu
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {  // menampilkan message kalo ga ketemu
            System.out.println("[DEBUG] [X] Kategori produk dengan keyword '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }
    
    static void tampilkanHeader() {
        System.out.println("┌──────┬──────────────────────┬─────────────────┬─────────────────┬────────────────┬───────┐");
        System.out.println("│  ID  │ Nama Produk          │ Merek           │ Kategori        │ Harga          │ Stok  │");
        System.out.println("├──────┼──────────────────────┼─────────────────┼─────────────────┼────────────────┼───────┤");
    }
    
    static void tampilkanFooter() {
        System.out.println("└──────┴──────────────────────┴─────────────────┴─────────────────┴────────────────┴───────┘");
    }
}