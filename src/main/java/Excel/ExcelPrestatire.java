package Excel;

import model.Facture;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class ExcelPrestatire {
    public void afficherFacturePrestatire(int id){
        List<Facture> factures=dao.FactureDAO.getFacturesByPrestataire(id);
        int totalpaye=0;
        int totalfacture=0;
        int totalNonPaye=0;


    Workbook  workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("Factures");
    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Date");
    header.createCell(2).setCellValue("Client");
    header.createCell(3).setCellValue("Montant");
    header.createCell(4).setCellValue("Statut");
    //calcul facturé


     //Total payé
        factures.stream().filter(f->f.getStatut().equals(true)).count();

      //Total non payé
        factures.stream().filter(f->f.getStatut().equals(false)).count();



    }

}
