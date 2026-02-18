package Excel;

import model.Client;
import model.Facture;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.DBconnection;
import util.ValidationDonnees;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static util.DBconnection.getConnection;

public class ExcelPrestatire {
    public  static void afficherFacturePrestatire(){
        int id= ValidationDonnees.validateInts("id");

        List<Facture> factures=dao.FactureDAO.getFacturesByPrestataire(id);
        double totalpaye=0;
        double totalfacture=0;
        double totalNonPaye=0;
        int rowN=1;



    Workbook  workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("Factures");
    Row header = sheet.createRow(0);//header
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Date");
    header.createCell(2).setCellValue("Client");
    header.createCell(3).setCellValue("Montant");
    header.createCell(4).setCellValue("Status");
    //création de header:

        for(Facture f:factures){
            Row row = sheet.createRow(rowN++);
            row.createCell(0).setCellValue(f.getId());
            row.createCell(1).setCellValue(f.getDate());
            row.createCell(2).setCellValue(f.getClient().getNom());
            row.createCell(3).setCellValue(f.getMontant());
            row.createCell(4).setCellValue(f.getStatut()?"payée":"non payée");
        }





    //calcul facturé
   totalfacture = factures.stream().mapToDouble(f->f.getMontant()).sum();
        Row totalFactureRow=sheet.createRow(rowN++);
        totalFactureRow.createCell(2).setCellValue("calcul facturé");
        totalFactureRow.createCell(3).setCellValue(totalfacture);



   //Total payé
  totalpaye = factures.stream().filter(f->f.getStatut()).mapToDouble(f->f.getMontant()).sum();
  Row totalPayeRow=sheet.createRow(rowN++);
  totalPayeRow.createCell(2).setCellValue("total payé");
  totalPayeRow.createCell(3).setCellValue(totalpaye);

    //Total non payé
        totalNonPaye = factures.stream().filter(f->!f.getStatut()).mapToDouble(f->f.getMontant()).sum();
        Row totalNonPayeRow=sheet.createRow(rowN++);
        totalNonPayeRow.createCell(2).setCellValue("total non payé");
        totalNonPayeRow.createCell(3).setCellValue(totalNonPaye);



  try{
      FileOutputStream file= new FileOutputStream(" facturesprestatairemois.xls");
      workbook.write(file);
      file.close();
      workbook.close();
      System.out.println("Export Excel réussi");

  }catch(Exception e){
      e.printStackTrace();
  }
    }


}
