package org.rplbo.app.Manager;

import org.rplbo.app.DBConnectionManager;
import org.rplbo.app.Data.RekamMedis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ========================================================================
 *              PETUNJUK PENGERJAAN (BERLAKU UNTUK SETIAP METHOD)
 * ========================================================================
 *              TODO : LENGKAPILAH SETIAP METHOD YANG KOSONG
 * TODO 1: Siapkan Query SQL.
 * TODO 2: Buka koneksi database dan siapkan PreparedStatement. Gunakan prepareStatement(query)
 * TODO 3: Lakukan parameter binding (isi nilai tanda '?'). Gunakan setString() / setInt()
 * TODO 4: Eksekusi query ke dalam database. Gunakan executeQuery()
 * TODO 5: Evaluasi dan olah hasil eksekusi. Gunakan next()
 * TODO 6: Tangkap exception (SQLException) jika terjadi kegagalan database. (try catch (SQLException e))
 *
 * HINT ATURAN EMAS:
 * - Gunakan stmt.executeUpdate() jika merubah isi tabel (INSERT, UPDATE, DELETE).
 * - Gunakan stmt.executeQuery() jika hanya membaca tabel (SELECT).
 * - Untuk INSERT/UPDATE/DELETE : Cek apakah baris yang terpengaruh (rowsAffected) > 0, lalu return true/false.
 * - Untuk SELECT : Gunakan perulangan while(rs.next()) untuk mengekstrak data dan membungkusnya ke dalam List/Objek.
 *
 * Tampilkan pesan error di konsol (System.err.println) agar mudah di-debug,
 * lalu kembalikan nilai default (misal: return false, atau return List kosong).
 * ========================================================================
 */


public class RekamMedisManager {
    private Connection connection;

    public RekamMedisManager(Connection connection) {
        this.connection = connection;
    }

    // TODO LENGKAPILAH SETIAP METHOD YANG KOSONG DIBAWAH INI
    // --- 1. CREATE (Tambah Rekam Medis) ---
    public boolean tambahRekamMedis(String namaDokter,String namaPasien, String diagnosis, String tanggal) {
        try {
//            statement =connection.createStatement();
            String query = "insert into rekam_medis (nama_pasien, nama_dokter, diagnosis, tanggal) values (?, ?, ?, ?)";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, namaPasien);
            myStmt.setString(2, namaDokter);
            myStmt.setString(3, diagnosis);
            myStmt.setString(4, tanggal);
            int check = myStmt.executeUpdate();
            if (check == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // --- 2. READ ALL ---
    public List<RekamMedis> getAllRekamMedis() {
        List<RekamMedis> rekamMedisList = new ArrayList<>();
        try {
            String query = "select * from rekam_medis";
            PreparedStatement myStmt = connection.prepareStatement(query);
            ResultSet myRs = myStmt.executeQuery();
            while (myRs.next()) {
                int id = myRs.getInt("id");
                String namaPasien = myRs.getString("nama_pasien");
                String namaDokter = myRs.getString("nama_dokter");
                String diagnosa = myRs.getString("diagnosis");
                String tanggal = myRs.getString("tanggal");
                RekamMedis rm = new RekamMedis(id, namaPasien, diagnosa, tanggal, namaDokter);
                rekamMedisList.add(rm);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return rekamMedisList;
    }

    // --- 3. UPDATE ---
    public boolean editRekamMedis(int idRekamMedis, String diagnosisBaru) {
        try {
            String query = "update rekam_medis set diagnosis = ? where id = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, diagnosisBaru);
            myStmt.setInt(2, idRekamMedis);
            int check = myStmt.executeUpdate();
            if (check == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    // --- 4. DELETE ---
    public boolean hapusRekamMedis(int idRekamMedis) {
        try {
            String query = "delete from rekam_medis where id = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1, idRekamMedis);
            int check = myStmt.executeUpdate();
            if (check == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    // --- 5. READ ---
    public List<RekamMedis> cariRekamMedisPasien(String nama) {
        List<RekamMedis> resultList = new ArrayList<>();
        try {
            String query = "select * from rekam_medis where nama_pasien = ?";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, nama);
            ResultSet myRs =  myStmt.executeQuery();
            while (myRs.next()) {
                int id = myRs.getInt("id");
                String namaPasien = myRs.getString("nama_pasien");
                String namaDokter = myRs.getString("nama_dokter");
                String diagnosa = myRs.getString("diagnosis");
                String tanggal = myRs.getString("tanggal");
                RekamMedis rm = new RekamMedis(id, namaPasien, diagnosa, tanggal, namaDokter);
                resultList.add(rm);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }
}