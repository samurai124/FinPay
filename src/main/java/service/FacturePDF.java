package service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import model.Facture;

public class FacturePDF {
    public static void facturepdf(Facture facture){
        try {

            PdfWriter writer = new PdfWriter("facture.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            String logoPath = "im1.png";
            Image logo = new Image(ImageDataFactory.create(logoPath));

            logo.scaleToFit(100, 100);// 150x150 pixels max
            logo.setMarginRight(50);

            document.add(logo);


            document.add(new Paragraph("FinPin").setBold());
            document.add(new Paragraph(("- La facture N° : ") + facture.getNumero()));
            document.add(new Paragraph("---------------------les informations du client --------------------").setBold());
            document.add(new Paragraph("- La facture du client : " + facture.getClient().getNom()));
            document.add(new Paragraph("- Id client : " + facture.getClient().getId()));
            document.add(new Paragraph("--------------------- les informations du prestataire --------------------").setBold());
            document.add(new Paragraph("- Le prestataire : " + facture.getPrestataire().getNomEntreprise()));
            document.add(new Paragraph("- L'email du prestataire : " + facture.getPrestataire().getEmail()));
            document.add(new Paragraph("--------------------- les informations de la facture  --------------------").setBold());
            document.add(new Paragraph("- La date de la facture : " + facture.getDate()));
            document.add(new Paragraph(("- Le montant total de la facture est: " + facture.getMontant()) + " MAD"));
//            document.add(new Paragraph(("- La commition appliquée : " +  ));
            document.add(new Paragraph("- Le statut de la facture : " + facture.getStatut()));


            document.close();
            System.out.println("PDF avec logo créé!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
