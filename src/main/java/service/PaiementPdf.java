package service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import model.Paiement;

import javax.print.attribute.standard.PageRanges;
import java.time.format.DateTimeFormatter;

public class PaiementPdf {
    public static void genererRecuePaiement(Paiement paiement){
      String recuePaiement="C:/Users/soufiane/Desktop/pdf/recupaiement "+paiement.getId()+".pdf";
      try {
          PdfWriter writer=new PdfWriter(recuePaiement);
          PdfDocument pdf=new PdfDocument(writer);
          Document document=new Document(pdf);
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

          document.add(new Paragraph("----------------- Recu de Paiement -----------------------"));
          document.add(new Paragraph("Numéro du paiement : "+paiement.getId()));
          document.add(new Paragraph("Numéro de la facture: "+paiement.getFacture().getNumero()));
          document.add(new Paragraph("Date du paiement: "+paiement.getDatePaiement().format(formatter)));
          document.add(new Paragraph("Méthode de paiement : Espèces / Carte / Autre"));
          document.add(new Paragraph("le montant " + paiement.getMontant()));
          double reste=paiement.getFacture().getMontant() - paiement.getMontant();
          document.add(new Paragraph("Reste à payer: "+ reste));

          document.add(new Paragraph("----------------------------------------------------------"));
          document.close();

          System.out.println("Recu PDF généré avec succès : " + recuePaiement);

      }catch (Exception e){
          System.out.println("Erreur lors de la génération du PDF : " + e.getMessage());
      }
    }
}
