package com.eatsleep.bill.bill.application.generatepdfusecase;

import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.BillResponse;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.TopsBillDescriptionResponse;
import com.eatsleep.bill.common.UseCase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@UseCase
public class PdfGenerator {
    
    public byte[] generateBillPdf(BillResponse billResponse) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Factura de Restaurante", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add space
        document.add(Chunk.NEWLINE);

        // Add bill details
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Create a table with 2 columns
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{1, 2});

        table.addCell(new Phrase("ID Factura:", headerFont));
        table.addCell(new Phrase(billResponse.getId(), normalFont));
        table.addCell(new Phrase("ID Restaurante:", headerFont));
        table.addCell(new Phrase(billResponse.getIdLocation(), normalFont));
        table.addCell(new Phrase("Tipo de Factura:", headerFont));
        table.addCell(new Phrase(billResponse.getType(), normalFont));
        table.addCell(new Phrase("Usuario:", headerFont));
        table.addCell(new Phrase(billResponse.getUserId(), normalFont));
        table.addCell(new Phrase("Fecha:", headerFont));
        table.addCell(new Phrase(billResponse.getStartDate().toString(), normalFont));

        document.add(table);

        // Add space
        document.add(Chunk.NEWLINE);

        // Add items table
        Paragraph itemsTitle = new Paragraph("Detalles de los Productos", headerFont);
        itemsTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(itemsTitle);

        PdfPTable itemsTable = new PdfPTable(3);
        itemsTable.setWidths(new int[]{1, 2, 2});
        itemsTable.addCell(new Phrase("ID Producto", headerFont));
        itemsTable.addCell(new Phrase("Cantidad", headerFont));
        itemsTable.addCell(new Phrase("Precio", headerFont));

        for (TopsBillDescriptionResponse description : billResponse.getDescriptions()) {
            itemsTable.addCell(new Phrase(description.getIdProduct(), normalFont));
            itemsTable.addCell(new Phrase(String.valueOf(description.getQuantity()), normalFont));
            itemsTable.addCell(new Phrase(String.format("$%.2f", description.getUnitPrice()), normalFont));
        }

        document.add(itemsTable);

        // Add footer
        Paragraph footer = new Paragraph("Gracias por su compra", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return baos.toByteArray();
    }
    
    
    public byte[] generateBillPdfHotel(BillResponse billResponse) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Factura de Hotel", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add space
        document.add(Chunk.NEWLINE);

        // Add bill details
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Create a table with 2 columns
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{1, 2});

        table.addCell(new Phrase("ID Factura:", headerFont));
        table.addCell(new Phrase(billResponse.getId(), normalFont));
        table.addCell(new Phrase("ID Hotel:", headerFont));
        table.addCell(new Phrase(billResponse.getIdLocation(), normalFont));
        table.addCell(new Phrase("Tipo de Factura:", headerFont));
        table.addCell(new Phrase(billResponse.getType(), normalFont));
        table.addCell(new Phrase("Usuario:", headerFont));
        table.addCell(new Phrase(billResponse.getUserId(), normalFont));
        table.addCell(new Phrase("Fecha:", headerFont));
        table.addCell(new Phrase(billResponse.getStartDate().toString(), normalFont));

        document.add(table);

        // Add space
        document.add(Chunk.NEWLINE);

        // Add items table
        Paragraph itemsTitle = new Paragraph("Detalles de los Productos", headerFont);
        itemsTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(itemsTitle);

        PdfPTable itemsTable = new PdfPTable(3);
        itemsTable.setWidths(new int[]{1, 2, 2});
        itemsTable.addCell(new Phrase("ID Producto", headerFont));
        itemsTable.addCell(new Phrase("Cantidad Dias", headerFont));
        itemsTable.addCell(new Phrase("Precio", headerFont));

        for (TopsBillDescriptionResponse description : billResponse.getDescriptions()) {
            itemsTable.addCell(new Phrase(description.getIdProduct(), normalFont));
            itemsTable.addCell(new Phrase(String.valueOf(description.getQuantity()), normalFont));
            itemsTable.addCell(new Phrase(String.format("$%.2f", description.getUnitPrice()), normalFont));
        }

        document.add(itemsTable);

        // Add footer
        Paragraph footer = new Paragraph("Gracias por su compra", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return baos.toByteArray();
    }
    
    
}
