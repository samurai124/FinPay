package Excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.DBconnection;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExcelAdmin {
    public static void exelData() {
        String requet = "SELECT facture.date, prestataire.nomEntreprise, COUNT(facture.id) AS 'totalefactures', SUM(facture.montant) AS 'totaleMontant' FROM prestataire INNER JOIN facture ON prestataire.id = facture.idPrestataire WHERE facture.status = true GROUP BY (prestataire.id);";

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(requet);
             ResultSet rs = statement.executeQuery()) {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Factures Impayées");

            HSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date Reference");
            header.createCell(1).setCellValue("Prestataire");
            header.createCell(2).setCellValue("Nombre Factures");
            header.createCell(3).setCellValue("Total Généré (MAD)");
            header.createCell(4).setCellValue("Commission (2%)");

            int rowNum = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(rowNum++);

                double montantTotal = rs.getDouble("totaleMontant");
                double commission = montantTotal * 0.02;

                row.createCell(0).setCellValue(rs.getString("date"));
                row.createCell(1).setCellValue(rs.getString("nomEntreprise"));
                row.createCell(2).setCellValue(rs.getInt("totalefactures"));
                row.createCell(3).setCellValue(montantTotal);
                row.createCell(4).setCellValue(commission);
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            File dir = new File("xlss");
            if (!dir.exists()) dir.mkdirs();

            String fileName = "xlss/factures_impayees.xls";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Fichier Excel généré avec succès dans le dossier pdfs");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void exportFacturesImpayees() {
        String requet = "SELECT facture.id, client.nom, facture.date, facture.montant FROM client INNER JOIN facture ON client.id = facture.idClient WHERE facture.status = false";

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(requet);
             ResultSet rs = statement.executeQuery()) {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Factures Impayées");

            HSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Client");
            header.createCell(2).setCellValue("Date");
            header.createCell(3).setCellValue("Montant (MAD)");
            header.createCell(4).setCellValue("Jours de Retard");

            int rowNum = 1;
            LocalDate aujourdhui = LocalDate.now();

            while (rs.next()) {
                HSSFRow row = sheet.createRow(rowNum++);

                int id = rs.getInt("id");
                String nomClient = rs.getString("nom");
                java.sql.Date dateSql = rs.getDate("date");
                double montant = rs.getDouble("montant");

                long joursDeRetard = 0;
                if (dateSql != null) {
                    LocalDate dateFacture = dateSql.toLocalDate();
                    joursDeRetard = ChronoUnit.DAYS.between(dateFacture, aujourdhui);
                }

                row.createCell(0).setCellValue(id);
                row.createCell(1).setCellValue(nomClient);
                row.createCell(2).setCellValue(dateSql.toString());
                row.createCell(3).setCellValue(montant);
                row.createCell(4).setCellValue(joursDeRetard > 0 ? joursDeRetard : 0);
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            File dir = new File("xlss");
            if (!dir.exists()) dir.mkdirs();

            String fileName = "xlss/facturesimpayeesmois.xls";
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Export réussi : " + fileName);
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
