package service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import model.Facture;
import java.io.File;
import java.time.format.DateTimeFormatter;

public class FacturePDF {
    public static void facturepdf(Facture facture) {
        try {
            String folderName = "pdfs";
            File directory = new File(folderName);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fullPath = folderName + File.separator + "facture_" + facture.getId() + ".pdf";

            PdfWriter writer = new PdfWriter(fullPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            try {
                String logoPath = "im1.png";
                Image logo = new Image(ImageDataFactory.create(logoPath));
                logo.scaleToFit(100, 100);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo non trouvé.");
            }

            document.add(new Paragraph("FinPay").setBold().setFontSize(20));
            document.add(new Paragraph("- La facture N° : " + facture.getNumero()));

            document.add(new Paragraph("\n----------------------------- Informations du Client -----------------------------").setBold());
            document.add(new Paragraph("- Nom : " + facture.getClient().getNom()));
            document.add(new Paragraph("- ID : " + facture.getClient().getId()));

            document.add(new Paragraph("\n----------------------------- Informations du Prestataire -----------------------------").setBold());
            document.add(new Paragraph("- Entreprise : " + facture.getPrestataire().getNomEntreprise()));
            document.add(new Paragraph("- Email : " + facture.getPrestataire().getEmail()));

            document.add(new Paragraph("\n----------------------------- Détails Financiers -----------------------------").setBold());
            document.add(new Paragraph("- Date : " + (facture.getDate() != null ? facture.getDate().format(formatter) : "N/A")));
            document.add(new Paragraph("- Montant HT : " + facture.getMontant() + " MAD"));

            double commission = facture.getMontant() * 0.02;
            double total = facture.getMontant() + commission;
            document.add(new Paragraph("- Commission (2%) : " + commission + " MAD"));
            document.add(new Paragraph("- Total à payer : " + total + " MAD").setBold());

            String statusText = facture.getStatut() ? "Payée" : "Non payée";
            document.add(new Paragraph("- Statut : " + statusText));

            document.close();
            System.out.println("Succès ! Facture disponible ici : " + directory.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}