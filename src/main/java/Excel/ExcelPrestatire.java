package model;

public class ExcelPrestataire {
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("Factures");
    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Date");
    header.createCell(2).setCellValue("Client");
    header.createCell(3).setCellValue("Montant");
    header.createCell(4).setCellValue("Statut");


}
