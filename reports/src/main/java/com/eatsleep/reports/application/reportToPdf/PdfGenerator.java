package com.eatsleep.reports.application.reportToPdf;


import com.eatsleep.reports.application.allearningscostsusecase.EarningResponse;
import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import com.eatsleep.reports.common.UseCase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@UseCase
public class PdfGenerator {
    
    public byte[] generateEmployeeReport(List<RetrieveEmployeeResponse> employees) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Agregar título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Employee Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n")); // Espacio entre título y tabla

        // Crear tabla con encabezados
        PdfPTable table = new PdfPTable(7); // 7 columnas
        table.setWidthPercentage(100);

        // Encabezados
        addTableHeader(table, "ID");
        addTableHeader(table, "Email");
        addTableHeader(table, "Name");
        addTableHeader(table, "Phone");
        addTableHeader(table, "Type");
        addTableHeader(table, "Weekly Payment");
        addTableHeader(table, "Location ID");

        // Añadir los datos de los empleados
        for (RetrieveEmployeeResponse employee : employees) {
            table.addCell(employee.getId());
            table.addCell(employee.getEmail());
            table.addCell(employee.getName());
            table.addCell(employee.getPhone());
            table.addCell(employee.getType());
            table.addCell(String.valueOf(employee.getWeeklyPayment()));
            table.addCell(employee.getIdLocation());
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        PdfPCell header = new PdfPCell(new Phrase(headerTitle, headFont));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
    
    public byte[] generateTopsBillDescriptionReport(List<TopsBillDescriptionResponse> responseList, String titleStr, String productStr) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Crear el título del reporte
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph(titleStr, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Línea en blanco

        // Crear tabla con encabezados (6 columnas)
        PdfPTable table = new PdfPTable(6); // Cambiado a 6 columnas
        table.setWidthPercentage(100); // Asegura que la tabla ocupe todo el ancho
        table.setWidths(new float[]{2, 2, 2, 2, 2, 2}); // Ajusta los anchos de las columnas según sea necesario


        // Definir fuentes
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

        // Encabezados de la tabla
        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("ID", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("ID Bill", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Type", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase(productStr, headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Unit Price", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Quantity", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        // Agregar datos a la tabla
        for (TopsBillDescriptionResponse response : responseList) {
            PdfPCell cell;

            cell = new PdfPCell(new Phrase(response.getId(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(response.getIdBill(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(response.getType(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(response.getIdProduct(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(response.getUnitPrice()), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(response.getQuantity()), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();

        return baos.toByteArray();
    }
    
    
    public byte[] generateEarningsCostsReport(List<EarningResponse> responseList, String titleStr) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Crear el título del reporte
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph(titleStr, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Línea en blanco

        // Crear tabla con 5 columnas (ID, Fecha, Ubicación, Total de Ingresos, Total de Gastos)
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100); // Asegura que la tabla ocupe todo el ancho
        table.setWidths(new float[]{2, 2, 3, 2, 2}); // Ajusta los anchos de las columnas

        // Definir fuentes
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Encabezados de la tabla
        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("ID", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Fecha", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Ubicación", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Ingresos", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Gastos", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        // Agregar datos a la tabla
        for (EarningResponse response : responseList) {
            PdfPCell cell;

            // ID
            cell = new PdfPCell(new Phrase(response.getId(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Fecha (startDate)
            cell = new PdfPCell(new Phrase(response.getStartDate().toString(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Ubicación (idLocation)
            cell = new PdfPCell(new Phrase(response.getIdLocation(), bodyFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Ingresos o Gastos
            if (response.getType().toLowerCase().contains("earning")) {
                // Colocar el monto en la columna de Ingresos
                cell = new PdfPCell(new Phrase(String.valueOf(response.getTotal()), bodyFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell); // Ingresos

                // Colocar vacío en Gastos
                cell = new PdfPCell(new Phrase("", bodyFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell); // Gastos
            } else {
                // Colocar el monto en la columna de Gastos
                cell = new PdfPCell(new Phrase("", bodyFont)); // Dejar vacío en Ingresos
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell); // Ingresos

                cell = new PdfPCell(new Phrase(String.valueOf(response.getTotal()), bodyFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell); // Gastos
            }
        }

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();

        return baos.toByteArray();
    }
    
    
    
}
