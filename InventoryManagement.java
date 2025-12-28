import java.util.Scanner;

public class InventoryManagement {
    
    static final int MAX_PRODUK = 100; // Kapasitas maksimal produk di toko
    static Product[] inventory = new Product[MAX_PRODUK];
    static int productCount = 0; // Untuk tracking jumlah produk saat ini
    
    static Scanner scanner = new Scanner(System.in);
    static int nextId = 1;  // buat auto increment ID produk
    
    // Kelas buat nyimpen data produk
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
        
        // Fungsi buat nampilin info produk dalam format tabel
        void displayInfo() {
            System.out.printf("| %-4d | %-20s | %-15s | %-15s | Rp %-11.0f | %-5d |\n", 
                id, nama, merek, kategori, harga, stok);
        }
    }
    
    public static void main(String[] args) {
        // Pengisian produk awal di toko
        tambahProdukOtomatis("Laptop ASUS ROG", "ASUS", "Laptop", 15000000, 5);
        tambahProdukOtomatis("iPhone 15 Pro", "Apple", "Smartphone", 18000000, 10);
        tambahProdukOtomatis("Samsung Galaxy S24", "Samsung", "Smartphone", 12000000, 8);
        
        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // untuk mengatasi buffering
            
            System.out.println();
            
            switch(pilihan) {
                case 1:
                    // Fitur untuk menambahkan product ke inventaris toko
                    tambahProduk();
                    break;
                case 2:
                    // Fitur untuk menampilkan semua produk di inventaris toko
                    tampilkanSemuaProduk();
                    break;
                case 3:
                    // Fitur untuk mengedit produk yang sudah ada di inventaris toko
                    editProduk();
                    break;
                case 4:
                    // Fitur untuk menghapus produk yang ada di inventaris toko
                    hapusProduk();
                    break;
                case 5:
                    // Fitur untuk mencari produk yang tersedia
                    // Bisa mencari berdasarkan ID, Nama, Merek, dan Kategori
                    searchProduct();
                    break;
                case 6:
                    // Fitur untuk mengurutkan produk yang tersedia
                    // Bisa mencari berdasarkan ID, Nama, Merek, Kategori, Harga, dan Stok
                    sortProduct();
                    break;
                case 7:
                    // Fitur untuk proses transaksi pembelian produk
                    // Pembelian produk berdasarkan ID, bisa mengatur jumlah pembelian, dan terdapat struk pembelian.
                    transactionProduct();
                    break;
                case 8:
                    // tampilin total produk, stok, dan stok menipis
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
    
    // Tampilin menu utama
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
    
    // Tambah produk manual lewat input user
    static void tambahProduk() {
        if (productCount >= MAX_PRODUK) {
            System.out.println("Kapasitas inventory penuh!");
            return;
        }

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
        
        inventory[productCount] = produkBaru;
        productCount++;
        
        System.out.println("\n✓ Produk berhasil ditambahkan dengan ID: " + produkBaru.id);
    }
    
    // Fungsi bantu buat menambahkan data awal
    static void tambahProdukOtomatis(String nama, String merek, String kategori, double harga, int stok) {
        if (productCount < MAX_PRODUK) {
            inventory[productCount] = new Product(nextId++, nama, merek, kategori, harga, stok);
            productCount++;
        }
    }
    
    // Tampilin semua produk dalam bentuk tabel
    static void tampilkanSemuaProduk() {
        if(productCount == 0) {
            System.out.println("Inventory masih kosong!");
            return;
        }
        
        System.out.println("═══ DAFTAR SEMUA PRODUK ═══");
        tampilkanHeader();
        
        for(int i = 0; i < productCount; i++) {
            inventory[i].displayInfo();
        }
        
        tampilkanFooter();
    }
    
    // Edit produk berdasarkan ID
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
    
    // Hapus produk berdasarkan ID
    static void hapusProduk() {
        System.out.println("═══ HAPUS PRODUK ═══");
        
        System.out.print("Masukkan ID produk yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        // Cari indexnya dulu
        int indexFound = -1;
        for (int i = 0; i < productCount; i++) {
            if (inventory[i].id == id) {
                indexFound = i;
                break;
            }
        }
        
        if(indexFound == -1) {
            System.out.println("✗ Produk dengan ID " + id + " tidak ditemukan!");
            return;
        }
        
        Product produk = inventory[indexFound];
        
        System.out.println("\nProduk yang akan dihapus:");
        tampilkanHeader();
        produk.displayInfo();
        tampilkanFooter();
        
        System.out.print("\nYakin ingin menghapus? (y/n): ");
        String konfirmasi = scanner.nextLine();
        
        if(konfirmasi.equalsIgnoreCase("y")) {
            for (int i = indexFound; i < productCount - 1; i++) {
                inventory[i] = inventory[i + 1];
            }
            inventory[productCount - 1] = null;
            productCount--;
            
            System.out.println("✓ Produk berhasil dihapus!");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
    
    // Menampilkan laporan toko
    // Berisi total produk, total stok, total nilai, dan stok menipis
    static void tampilkanLaporan() {
        if(productCount == 0) {
            System.out.println("Inventory masih kosong!");
            return;
        }
        
        System.out.println("═══ LAPORAN INVENTARIS ═══");
        
        int totalProduk = productCount;
        int totalStok = 0;
        double totalNilai = 0;
        
        for(int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            totalStok += p.stok;
            totalNilai += (p.harga * p.stok);
        }
        
        System.out.println("Total Jenis Produk  : " + totalProduk);
        System.out.println("Total Stok Barang   : " + totalStok + " unit");
        System.out.printf("Total Nilai Inventory: Rp %.0f\n", totalNilai);
        
        System.out.println("\n--- Produk dengan Stok Menipis (< 5) ---");
        boolean adaStokMenipis = false;
        
        for(int i = 0; i < productCount; i++) {
            Product p = inventory[i];
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
    
    // Cari produk berdasarkan ID
    static Product cariProdukById(int id) {
        for(int i = 0; i < productCount; i++) {
            if(inventory[i].id == id) {
                return inventory[i];
            }
        }
        return null;
    }
    
    // Menu utama search product
    // Pilih bisa cari bebrdasarkan ID, nama, merek, atau kategori
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
            case 0:
                System.out.println("✗ Pencarian product dibatalkan!");
                return;
            case 1:
                searchProductByID();
                break;
            case 2:
                searchProductByName();
                break;
            case 3:
                searchProductByBrand();
                break;
            case 4:
                searchProductByCategory();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }
    }

    // Cari produk berdasarkan ID
    static void searchProductByID() {
        System.out.print("\nMasukkan ID: ");
        int keyword = scanner.nextInt();
        scanner.nextLine();

        boolean keywordFound = false;
        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            if (p.id == keyword) {
                if (!keywordFound) {
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {
            System.out.println("✗ Produk dengan ID '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Cari produk berdasarkan nama (case-insensitive)
    static void searchProductByName() {
        System.out.print("\nMasukkan nama: ");
        String keyword = scanner.nextLine();

        boolean keywordFound = false;
        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            if (p.nama.toLowerCase().contains(keyword.toLowerCase())) {
                if (!keywordFound) {
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {
            System.out.println("✗ Produk dengan Nama '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Cari produk berdasarkan merek
    static void searchProductByBrand() {
        String[] listMerek = new String[productCount];
        int countMerek = 0;

        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            boolean merekSudahAda = false;
            for (int j = 0; j < countMerek; j++) {
                if (listMerek[j].equalsIgnoreCase(p.merek)) {
                    merekSudahAda = true;
                    break;
                }
            }
            if (!merekSudahAda) {
                listMerek[countMerek++] = p.merek;
            }
        }
        
        if (countMerek == 0) {
            System.out.println("Belum ada data produk/merek!");
            return;
        }

        System.out.println("Daftar merek yang tersedia:");
        for (int i = 0; i < countMerek; i++) {
            System.out.println(" - " + listMerek[i]);
        }

        System.out.print("\nMasukkan merek: ");
        String keyword = scanner.nextLine();

        boolean keywordFound = false;
        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            if (p.merek.toLowerCase().equals(keyword.toLowerCase())) {
                if (!keywordFound) {
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
    
        if (!keywordFound) {
            System.out.println("✗ Produk dengan merek '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Cari produk berdasarkan kategori
    static void searchProductByCategory() {
        String[] listKategori = new String[productCount];
        int countKategori = 0;

        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            boolean kategoriSudahAda = false;
            // Cek manual di array
            for (int j = 0; j < countKategori; j++) {
                if (listKategori[j].equalsIgnoreCase(p.kategori)) {
                    kategoriSudahAda = true;
                    break;
                }
            }
            if (!kategoriSudahAda) {
                listKategori[countKategori++] = p.kategori;
            }
        }
        
        if (countKategori == 0) {
            System.out.println("Belum ada data produk/kategori!");
            return;
        }

        System.out.println("Daftar kategori yang tersedia:");
        for (int i = 0; i < countKategori; i++) {
            System.out.println(" - " + listKategori[i]);
        }

        System.out.print("\nMasukkan kategori: ");
        String keyword = scanner.nextLine();

        boolean keywordFound = false;
        for (int i = 0; i < productCount; i++) {
            Product p = inventory[i];
            if (p.kategori.equalsIgnoreCase(keyword)) {
                if (!keywordFound) {
                    tampilkanHeader();
                }
                p.displayInfo();
                keywordFound = true;
            }
        }
        if (!keywordFound) {
            System.out.println("✗ Produk dengan Kategori '" + keyword + "' tidak ditemukan!");
        } else {
            tampilkanFooter();
        }
    }

    // Menu utama sorting product
    // Bisa mengurutkan bedasarkan ID, Nama, Merek, Kategori, Harga, Stok
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
            case 0:
                System.out.println("✗ Pengurutan product dibatalkan!");
                return;
            case 1:
                sortProductByID();
                break;
            case 2:
                sortProductByName();
                break;
            case 3:
                sortProductByBrand();
                break;
            case 4:
                sortProductByCategory();
                break;
            case 5:
                sortProductByPrice();
                break;
            case 6:
                sortProductByStock();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                return;
        }
    }

    // Mengurutkan produk berdasarkan ID (menggunakan insertion sort)
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.id < inventory[i-1].id)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.id > inventory[i-1].id)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan ID secara %s!\n", metodeSorting);
    }

    // Mengurutkan produk berdasarkan nama (string, case-insensitive)
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.nama.compareToIgnoreCase(inventory[i-1].nama) < 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.nama.compareToIgnoreCase(inventory[i-1].nama) > 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan Nama secara %s!\n", metodeSorting);
    }

    // Mengurutkan produk berdasarkan merek (string)
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.merek.compareToIgnoreCase(inventory[i-1].merek) < 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.merek.compareToIgnoreCase(inventory[i-1].merek) > 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan Merek secara %s!\n", metodeSorting);
    }

    // Mengurutkan produk berdasarkan kategori (string)
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.kategori.compareToIgnoreCase(inventory[i-1].kategori) < 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.kategori.compareToIgnoreCase(inventory[i-1].kategori) > 0)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan Kategori secara %s!\n", metodeSorting);
    }
    
    // Mengurutkan produk berdasarkan harga (double)
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while (i > 0 && inventory[i - 1].harga > temp.harga) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while (i > 0 && inventory[i - 1].harga < temp.harga) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan Harga secara %s!\n", metodeSorting);
    }
    
    // Mengurutkan produk berdasarkan stok (integer)
    static void sortProductByStock() {
        String metodeSorting = "";
        System.out.println("Pilih metode pengurutan produk:");
        System.out.println("1. Ascending (Stok Terendah - Tertinggi)");
        System.out.println("2. Descending (Stok Tertinggi - Terendah)");
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

        int pass = 1;
        while (pass < productCount) {
            Product temp = inventory[pass];
            int i = pass;
            if (metodeSorting.equals("ascending")) {
                while ((i > 0) && (temp.stok < inventory[i-1].stok)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            else if (metodeSorting.equals("descending")) {
                while ((i > 0) && (temp.stok > inventory[i-1].stok)) {
                    inventory[i] = inventory[i-1];
                    i--;
                }
            }
            inventory[i] = temp;
            pass++;
        }
        System.out.printf("Produk berhasil diurutkan berdasarkan Stok secara %s!\n", metodeSorting);
    }

    // Fitur proses transaksi
    // Sistem pembelian berdasarkan ID Produk yang ingin dibeli, dapat mengatur
    // jumlah pembelian produk dan akan menampilkan struk pembelian setelah menyelesaikan pembayaran
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

        System.out.println();
        System.out.println("═══ TRANSAKSI PRODUK ═══");
        System.out.println("ID Produk yang dipilih:");
        tampilkanHeader();
        p.displayInfo();
        tampilkanFooter();

        System.out.print("\nMasukkan jumlah yang ingin dibeli: ");
        int jmlhBeli = scanner.nextInt();
        scanner.nextLine();
        
        // Validasi jumlah beli
        if (jmlhBeli <= 0) {
            System.out.println("✗ Jumlah pembelian minimal 1!");
            return;
        }
        if (jmlhBeli > p.stok) {
            System.out.println("✗ Stok produk tidak mencukupi! Sisa stok saat ini: " + p.stok);
            return;
        }

        // Hitung total harga
        double totalHargaBeli = p.harga * jmlhBeli;
        System.out.printf("Total harga yang harus dibayar: Rp %.0f\n", totalHargaBeli);
        
        System.out.print("Lanjutkan pembayaran? (y/n): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equals("y")) {
            // Kurangin stok
            p.stok = p.stok - jmlhBeli;
            String hargaFormatted = "Rp " + Math.round(p.harga);
            String totalHargaFormatted = "Rp " + Math.round(totalHargaBeli);

            // Tampilin struk
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

    // Bantu formatting struk biar rapi kalau teksnya terlalu panjang
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

    // Header tabel buat tampilin data produk
    static void tampilkanHeader() {
        System.out.println("┌──────┬──────────────────────┬─────────────────┬─────────────────┬────────────────┬───────┐");
        System.out.println("│  ID  │     Nama Produk      │      Merek      │     Kategori    │     Harga      │ Stok  │");
        System.out.println("├──────┼──────────────────────┼─────────────────┼─────────────────┼────────────────┼───────┤");
    }
    
    // Footer tabel
    static void tampilkanFooter() {
        System.out.println("└──────┴──────────────────────┴─────────────────┴─────────────────┴────────────────┴───────┘");
    }
}