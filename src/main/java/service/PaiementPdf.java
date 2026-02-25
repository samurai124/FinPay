package service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import model.Paiement;

import javax.print.attribute.standard.PageRanges;
import java.io.File;
import java.time.format.DateTimeFormatter;
public class PaiementPdf {
    public static void genererRecuePaiement(Paiement paiement){

        String folderName = "pdfs";
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
      String recuePaiement = folderName + File.separator + "recu_" + paiement.getId() + ".pdf";
//        String recuePaiement = folderName + File.separator +genererNomRecu(paiement.getId());
        try {
          PdfWriter writer=new PdfWriter(recuePaiement);
          PdfDocument pdf=new PdfDocument(writer);
          Document document=new Document(pdf);
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

          document.add(new Paragraph("----------------- Recu de Paiement -----------------------").setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph(" Total Facture : "+paiement.getFacture().getMontant()).setTextAlignment(TextAlignment.CENTER).setItalic());document.add(new Paragraph(" Numéro du paiement : "+paiement.getId()).setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph(" Numéro de la facture: "+paiement.getFacture().getNumero()).setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph(" Date du paiement: "+paiement.getDatePaiement().format(formatter)).setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph(" Méthode de paiement: "+paiement.getModePaiement()).setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph(" le montant " + paiement.getMontant()+ " MAD").setTextAlignment(TextAlignment.CENTER).setItalic());
          double reste=paiement.getFacture().getMontant() - paiement.getMontant();
          document.add(new Paragraph(" Reste à payer : " + (reste > 0 ? reste : "0.00") + " MAD").setTextAlignment(TextAlignment.CENTER).setItalic());
          document.add(new Paragraph("-----------------------------------------------------------------").setTextAlignment(TextAlignment.CENTER).setItalic());
            document.add(new Paragraph("\nMerci pour votre paiement.").setTextAlignment(TextAlignment.CENTER).setItalic());
          document.close();

          System.out.println("Recu PDF généré avec succès : " + recuePaiement);

      }catch (Exception e){
          System.out.println("Erreur lors de la génération du PDF : " + e.getMessage());
      }
    }

    // Générer nom de reçu de paiement
    public static String genererNomRecu(int idPaiement) {
        return "recu_" + idPaiement + ".pdf";
    }

    // Générer nom de rapport avec année et mois
    public static String genererNomRapport(String mois, String annee) {
        return "rapport" + mois + annee + ".xls";
    }
}
