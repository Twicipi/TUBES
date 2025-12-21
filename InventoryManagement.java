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
                    searchProduct();
                    break;
                case 6:
                    sortProduct();
                    break;
                case 7:
                    // Fungsi: prosesTransaksi()
                    // - Input ID produk & jumlah beli
                    // - Validasi stok cukup atau engga
                    // - Kurangi stok, hitung total harga
                    // - Tampilkan struk transaksi
                    transactionProduct();
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
        System.out.println("5. Cari Produk");
        System.out.println("6. Urutkan Produk");
        System.out.println("7. Proses Transaksi");
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
                System.out.println("✗ Pencarian product dibatalkan!");
                return;
            case 1: // ID Produk
                searchProductByID();
                break;
            case 2: // Nama Produk
                searchProductByName();
                break;
            case 3: // Merek Produk
                searchProductByBrand();
                break;
            case 4: // Kategori Produk
                searchProductByCategory();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
                
        }

    }

    // ========== FUNGSI SEARCH PRODUCT BY ID ==========
    static void searchProductByID() {
        // Input keyword kategori
        System.out.print("\nMasukkan ID: ");
        int keyword = scanner.nextInt();
        scanner.nextLine();

        boolean keywordFound = false;
        System.out.println("\n[DEBUG] Memproses pencarian untuk id: '" + keyword + "'");
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
            System.out.println("[DEBUG] ✗ Produk dengan ID '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // ========== FUNGSI SEARCH PRODUCT BY NAME ==========
    static void searchProductByName() {
        // Input keyword kategori
        System.out.print("\nMasukkan nama: ");
        String keyword = scanner.nextLine();

        boolean keywordFound = false;
        System.out.println("\n[DEBUG] Memproses pencarian untuk nama: '" + keyword + "'");
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
            System.out.println("[DEBUG] ✗ Produk dengan Nama '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // ========== FUNGSI SEARCH PRODUCT BY BRAND ==========
    static void searchProductByBrand() {
        ArrayList<String> listMerek = new ArrayList<>();

        for (Product p : inventory) {
            boolean merekSudahAda = false;
            for (String mrk : listMerek) {
                if (mrk.equalsIgnoreCase(p.merek)) {
                    merekSudahAda = true;
                    break;
                }
            }
            if (!merekSudahAda) {
                listMerek.add(p.merek);
            }
        }
        
        if (listMerek.isEmpty()) {
            System.out.println("Belum ada data produk/merek!");
            return; // Kembali ke menu awal
        }

        // Tampilkan list kategori
        System.out.println("Daftar merek yang tersedia:");
        for (String mrk : listMerek) {
            System.out.println(" - " + mrk);
        }

        // Input keyword kategori
        System.out.print("\nMasukkan merek: ");
        String keyword = scanner.nextLine();

        boolean keywordFound = false;
        System.out.println("\n[DEBUG] Memproses pencarian untuk merek: '" + keyword + "'");
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
            System.out.println("[DEBUG] ✗ Produk dengan merek '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // ========== FUNGSI SEARCH PRODUCT BY CATEGORY ==========
    static void searchProductByCategory() {
        ArrayList<String> listKategori = new ArrayList<>();

        for (Product p : inventory) {
            boolean kategoriSudahAda = false;
            for (String ktg : listKategori) {
                if (ktg.equalsIgnoreCase(p.kategori)) {
                    kategoriSudahAda = true;
                    break;
                }
            }
            if (!kategoriSudahAda) {
                listKategori.add(p.kategori);
            }
        }
        
        if (listKategori.isEmpty()) {
            System.out.println("Belum ada data produk/kategori!");
            return; // Kembali ke menu awal
        }

        // Tampilkan list kategori
        System.out.println("Daftar kategori yang tersedia:");
        for (String ktg : listKategori) {
            System.out.println(" - " + ktg);
        }

        // Input keyword kategori
        System.out.print("\nMasukkan kategori: ");
        String keyword = scanner.nextLine();

        // Tampilkan hasil searching produk
        boolean keywordFound = false;
        System.out.println("\n[DEBUG] Memproses pencarian untuk kategori: '" + keyword + "'");
        for (Product p : inventory) {
            if (p.kategori.equalsIgnoreCase(keyword)) {
                if (!keywordFound) { // langsung stop menampilkan headernya kalau udah ketemu
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
        if (!keywordFound) {  // menampilkan message kalo ga ketemu
            System.out.println("[DEBUG] ✗ Produk dengan Kategori '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Function sortingProduct
    static void sortProduct() {
        System.out.println("═══ URUTKAN PRODUK ═══");
        System.out.println("Pilih urutkan produk berdasarkan:");
        System.out.println("1. ID");
        System.out.println("2. Nama");
        System.out.println("3. Merek");
        System.out.println("4. Kategori");
        System.out.println("5. Harga");
        System.out.println("6. Stok");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\n═══ URUTKAN PRODUK ═══");
        switch(pilihan) {
            case 0: // Cancel Searching Product
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1: // ID Produk
                sortProductByID();
                break;
            case 2: // Nama Produk
                sortProductByName();
                break;
            case 3: // Merek Produk
                sortProductByBrand();
                break;
            case 4: // Kategori Produk
                sortProductByCategory();
                break;
            case 5: // Harga Produk
                sortProductByPrice();
                break;
            case 6: // Stok Produk
                sortProductByStock();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

    }

    // Insertion Sorting by ID
    static void sortProductByID() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.id < inventory.get(i-1).id)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.id > inventory.get(i-1).id)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Kategori secara %s!\n", metodeSorting);
    }

    // Insertion Sorting by Name
    static void sortProductByName() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                // Untuk memabndingkan huruf dri sebuah string menggunakan compareToIgnoreCase()
                while ((i > 0) && (temp.nama.compareToIgnoreCase(inventory.get(i-1).nama) < 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.nama.compareToIgnoreCase(inventory.get(i-1).nama) > 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Nama secara %s!\n", metodeSorting);
    }

    // Insertion Sorting by Brand
    static void sortProductByBrand() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                // Untuk memabndingkan huruf dri sebuah string menggunakan compareToIgnoreCase()
                while ((i > 0) && (temp.merek.compareToIgnoreCase(inventory.get(i-1).merek) < 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.merek.compareToIgnoreCase(inventory.get(i-1).merek) > 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Merek secara %s!\n", metodeSorting);
    }

    // Insertion Sorting by Category
    static void sortProductByCategory() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                // Untuk memabndingkan huruf dri sebuah string menggunakan compareToIgnoreCase()
                while ((i > 0) && (temp.kategori.compareToIgnoreCase(inventory.get(i-1).kategori) < 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.kategori.compareToIgnoreCase(inventory.get(i-1).kategori) > 0)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Kategori secara %s!\n", metodeSorting);
    }
    
    // Insertion Sorting by Price
    static void sortProductByPrice() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending (Harga Terendah - Tertinggi)");
        System.out.println("2. Descending (Harga Tertinggi - Terendah)");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                while (i > 0 && inventory.get(i - 1).harga > temp.harga) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while (i > 0 && inventory.get(i - 1).harga < temp.harga) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Kategori secara %s!\n", metodeSorting);
    }
    
    // Insertion Sorting by Stock
    static void sortProductByStock() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending (Harga Terendah - Tertinggi)");
        System.out.println("2. Descending (Harga Tertinggi - Terendah)");
        System.out.println("0. Batalkan Pengurutan");
        System.out.print("Pilihan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        
        switch(pilihan) {
            case 0: 
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                metodeSorting = "ascending";
                break;
            case 2:
                metodeSorting = "descending";
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }

        // Proccess Insertion Sorting
        int pass = 1;
        while (pass < inventory.size()) {
            Product temp = inventory.get(pass);
            int i = pass;
            // Ascending
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.stok < inventory.get(i-1).stok)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            // Descending
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.stok > inventory.get(i-1).stok)) {
                    inventory.set(i, inventory.get(i-1));
                    i--;
                }
            }
            inventory.set(i, temp);
            pass++;
        }
        System.out.printf("[DEBUG] Produk berhasil diurutkan berdasarkan Kategori secara %s!\n", metodeSorting);
    }

    // Transaction Process Function
    static void transactionProduct() {
        System.out.println("═══ TRANSAKSI PRODUK ═══");
        
        System.out.print("Masukkan ID produk yang ingin dibeli: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Product p = cariProdukById(id);
        
        if(p == null) {
            System.out.println("✗ Produk dengan ID " + id + " tidak ditemukan!");
            return;
        }

        // Menampilkan id produk yang telah di pilih dan akan melakukan input jumlah produk yang igin di beli.
        System.out.println();
        System.out.println("═══ TRANSAKSI PRODUK ═══");
        System.out.println("ID Produk yang dipilih:");
        tampilkanHeader();
        p.displayInfo();
        tampilkanFooter();

        // Input jumlah pemelian produk
        System.out.print("\nMasukkan jumlah yang ingin dibeli: ");
        int jmlhBeli = scanner.nextInt();
        scanner.nextLine();
        
        // Cek stok produk
        if (jmlhBeli <= 0) {
            System.out.println("═══ TRANSAKSI PRODUK ═══");
            System.out.println("✗ Jumlah pembelian minimal 1!");
            return;
        }
        if (jmlhBeli > p.stok) {
            System.out.println("═══ TRANSAKSI PRODUK ═══");
            System.out.println("✗ Stok produk tidak mencukupi! Sisa stok saat ini: " + p.stok);
            return;
        }

        // Menghitung total buy price
        double totalHargaBeli = p.harga * jmlhBeli;
        System.out.println("═══ TRANSAKSI PRODUK ═══");
        System.out.printf("Total harga yang harus dibayar: Rp %.0f\n", totalHargaBeli);
        
        System.out.print("Lanjutkan pembayaran? (y/n): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equals("y")) {
            // Mengurangi stok produk yang dibeli
            p.stok = p.stok - jmlhBeli;
            String hargaFormatted = "Rp " + Math.round(p.harga);
            String totalHargaFormatted = "Rp " + Math.round(totalHargaBeli);

            // Struk transaksi
            System.out.println("\n");
            System.out.println("┌──────────────────────────────────────┐");
            System.out.println("│           STRUK PEMBELIAN            │");
            System.out.println("├──────────────────────────────────────┤");
            transactionWrappedLine("ID Produk", p.id + "", 20);
            transactionWrappedLine("Nama Produk", p.nama, 20);
            transactionWrappedLine("Merek", p.merek, 20);
            transactionWrappedLine("Harga", hargaFormatted, 20);
            transactionWrappedLine("Jumlah Beli", jmlhBeli + "", 20);
            System.out.println("├──────────────────────────────────────┤");
            transactionWrappedLine("Total Harga", totalHargaFormatted, 20);
            System.out.println("└──────────────────────────────────────┘");
            System.out.println("✓ Transaksi telah berhasil! Stok produk telah diperbarui.");
        } else {
            System.out.println("✗ Transaksi dibatalkan!");
        }
    }

    // Function Helper untuk merapihkan nama/angka yang telalu panjang di struk pembelian.
    static void transactionWrappedLine(String label, String value, int wrapLimit) {
        int idx = 0;

        if (value == null) {
            value = "";
        }

        if (value.isEmpty()) {
            System.out.printf("| %-14s %-20s |\n", label, "");
            return;
        }
        while (idx < value.length()) {
            int endIdx = Math.min(idx + wrapLimit, value.length());
            String cutString = value.substring(idx, endIdx);
            if (idx == 0) {
                System.out.printf("| %-13s : %-20s |\n", label, cutString);
            } else {
                System.out.printf("| %-13s   %-20s |\n", "", cutString);
            }
            idx += wrapLimit;
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