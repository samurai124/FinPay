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
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class ExcelAdmin {
    public static String exelData() {
        String requet = "SELECT facture.date, prestataire.nomEntreprise, COUNT(facture.id) AS 'totalefactures', SUM(facture.montant) AS 'totaleMontant' FROM prestataire INNER JOIN facture ON prestataire.id = facture.idPrestataire WHERE facture.status = true GROUP BY (prestataire.id,facture.date,prestataire.nomEntreprise);";
        String mois = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        int annee = LocalDate.now().getYear();
        String fileName = "rapportGenerale_" + mois + "_" + annee + ".xls";
        String path = "xlss/" + fileName;

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(requet);
             ResultSet rs = statement.executeQuery();
             HSSFWorkbook workbook = new HSSFWorkbook()) {

            HSSFSheet sheet = workbook.createSheet("Statistiques Globales");

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
                row.createCell(0).setCellValue(rs.getString("date"));
                row.createCell(1).setCellValue(rs.getString("nomEntreprise"));
                row.createCell(2).setCellValue(rs.getInt("totalefactures"));
                row.createCell(3).setCellValue(montantTotal);
                row.createCell(4).setCellValue(montantTotal * 0.02);
            }

            for (int i = 0; i < 5; i++) sheet.autoSizeColumn(i);

            File dir = new File("xlss");
            if (!dir.exists()) dir.mkdirs();

            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String exportFacturesImpayees() {
        String mois = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        String fileName = "factures_impayees_" + mois + ".xls";
        String path = "xlss/" + fileName;

        String requet = "SELECT facture.id, client.nom, facture.date, facture.montant FROM client " +
                "INNER JOIN facture ON client.id = facture.idClient WHERE facture.status = false";

        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(requet);
             ResultSet rs = statement.executeQuery();
             HSSFWorkbook workbook = new HSSFWorkbook()) {

            HSSFSheet sheet = workbook.createSheet("Impayés " + mois);
            HSSFRow header = sheet.createRow(0);
            String[] columns = {"ID", "Client", "Date", "Montant (MAD)", "Jours de Retard"};
            for (int i = 0; i < columns.length; i++) header.createCell(i).setCellValue(columns[i]);

            int rowNum = 1;
            LocalDate aujourdhui = LocalDate.now();

            while (rs.next()) {
                HSSFRow row = sheet.createRow(rowNum++);
                java.sql.Date dateSql = rs.getDate("date");
                row.createCell(0).setCellValue(rs.getInt("id"));
                row.createCell(1).setCellValue(rs.getString("nom"));
                row.createCell(2).setCellValue(dateSql.toString());
                row.createCell(3).setCellValue(rs.getDouble("montant"));

                long retard = (dateSql != null) ? ChronoUnit.DAYS.between(dateSql.toLocalDate(), aujourdhui) : 0;
                row.createCell(4).setCellValue(retard > 0 ? retard : 0);
            }

            for (int i = 0; i < 5; i++) sheet.autoSizeColumn(i);

            File dir = new File("xlss");
            if (!dir.exists()) dir.mkdirs();

            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}