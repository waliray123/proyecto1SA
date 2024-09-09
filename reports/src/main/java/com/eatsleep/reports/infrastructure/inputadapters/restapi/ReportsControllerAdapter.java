package com.eatsleep.reports.infrastructure.inputadapters.restapi;

import com.eatsleep.reports.application.allearningscostsusecase.EarningResponse;
import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import com.eatsleep.reports.application.reportToPdf.PdfGenerator;
import com.eatsleep.reports.common.WebAdapter;
import com.eatsleep.reports.infrastructure.inputports.FindTopRestaurantBillsInputPort;
import com.eatsleep.reports.infrastructure.inputports.FindTopRoomInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetAllEmployeesByIdLocationInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByClientByDatesInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByClientIdLocationByDatesInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByIdLocationByDatesInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetEarningsCostsInputPort;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/reports")
@WebAdapter
public class ReportsControllerAdapter {
    
    private GetAllEmployeesByIdLocationInputPort getAllEmployeesByIdLocationInputPort;
    private FindTopRestaurantBillsInputPort findTopRestaurantBillsInputPort;
    private FindTopRoomInputPort findTopRoomInputPort;
    private GetBillsDescriptionByClientByDatesInputPort getBillsDescriptionByClientByDatesInputPort;
    private GetBillsDescriptionByClientIdLocationByDatesInputPort getBillsDescriptionByClientIdLocationByDatesInputPort;
    private GetBillsDescriptionByIdLocationByDatesInputPort getBillsDescriptionByIdLocationByDatesInputPort;
    private GetEarningsCostsInputPort getEarningsCostsInputPort;
    private PdfGenerator pdfGenerator;
    
    @Autowired
    public ReportsControllerAdapter(GetAllEmployeesByIdLocationInputPort getAllEmployeesByIdLocationInputPort
            ,FindTopRestaurantBillsInputPort findTopRestaurantBillsInputPort
            ,FindTopRoomInputPort findTopRoomInputPort
            ,GetBillsDescriptionByClientByDatesInputPort getBillsDescriptionByClientByDatesInputPort
            ,GetBillsDescriptionByClientIdLocationByDatesInputPort getBillsDescriptionByClientIdLocationByDatesInputPort
            ,GetBillsDescriptionByIdLocationByDatesInputPort getBillsDescriptionByIdLocationByDatesInputPort
            ,GetEarningsCostsInputPort getEarningsCostsInputPort
            ,PdfGenerator pdfGenerator
            
    ){
        this.getAllEmployeesByIdLocationInputPort = getAllEmployeesByIdLocationInputPort;
        this.findTopRestaurantBillsInputPort = findTopRestaurantBillsInputPort;
        this.findTopRoomInputPort = findTopRoomInputPort;
        this.getBillsDescriptionByClientByDatesInputPort = getBillsDescriptionByClientByDatesInputPort;
        this.getBillsDescriptionByClientIdLocationByDatesInputPort = getBillsDescriptionByClientIdLocationByDatesInputPort;
        this.getBillsDescriptionByIdLocationByDatesInputPort = getBillsDescriptionByIdLocationByDatesInputPort;
        this.getEarningsCostsInputPort = getEarningsCostsInputPort;
        this.pdfGenerator = pdfGenerator;
    }
    
    @GetMapping("/allemployees/{idLocation}")
    public ResponseEntity<byte[]> getAllEmployeesByIdLocation(@PathVariable String idLocation) throws DocumentException, IOException {
        // Obtener la lista de empleados desde el puerto de entrada
        List<RetrieveEmployeeResponse> responseList = this.getAllEmployeesByIdLocationInputPort.getAllEmployeesByIdLocation(idLocation);

        // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateEmployeeReport(responseList);

        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/toprestaurant")
    public ResponseEntity<byte[]> getTopRestaurant() throws DocumentException, IOException{
        List<TopsBillDescriptionResponse> responseList = this.findTopRestaurantBillsInputPort.findTopRestaurantBills();
         // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Listado de ventas del restaurante mas popular", "Id Platillo");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/toproom")
    public ResponseEntity<byte[]> getTopRoom() throws DocumentException, IOException{
        List<TopsBillDescriptionResponse> responseList = this.findTopRoomInputPort.findTopRoom();
         // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Habitación más popular, la que más alojamientos ha tenido incluyendo el listado de alojamientos", "Id Cuarto");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/toproom/{idLocation}")
    public ResponseEntity<byte[]> getTopRoomIdLocation(@PathVariable String idLocation) throws DocumentException, IOException {
        List<TopsBillDescriptionResponse> responseList = this.findTopRoomInputPort.findTopRoomBillsByIdLocation(idLocation);
         // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Habitación más popular, la que más alojamientos ha tenido por un hotel con id" + idLocation, "Id Cuarto");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/rangeclient/{idClient}/{idLocation}")
    public ResponseEntity<byte[]> getBillsDescriptionByClientIdLocationByDates(
            @PathVariable String idClient,
            @PathVariable String idLocation,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws DocumentException, IOException{
        List<TopsBillDescriptionResponse> responseList = this.getBillsDescriptionByClientIdLocationByDatesInputPort.getBillsDescriptionByClientIdLocationByDates(idClient, idLocation, startDate, endDate);
        // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Listado de gastos de un cliente por un rango de fechas en un establecimiento con id: " + idLocation, "Id Producto");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/rangeclient/{idClient}")
    public ResponseEntity<byte[]> getBillsDescriptionByClientByDates(
            @PathVariable String idClient,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws DocumentException, IOException{
        List<TopsBillDescriptionResponse> responseList = this.getBillsDescriptionByClientByDatesInputPort.getBillsDescriptionByClientByDates(idClient, startDate, endDate);

        // Generar el PDF
        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Listado de gastos de un cliente por un rango de fechas ", "Id Producto");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/rangelocation/{idLocation}")
    public ResponseEntity<byte[]> getBillsDescriptionByIdLocation(
            @PathVariable String idLocation,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws DocumentException, IOException{
        List<TopsBillDescriptionResponse> responseList = this.getBillsDescriptionByIdLocationByDatesInputPort.getBillsDescriptionByIdLocationByDates(idLocation, startDate, endDate);

        byte[] pdfBytes = pdfGenerator.generateTopsBillDescriptionReport(responseList, "Listado de ingresos del establecimiento con id " + idLocation, "Id Producto");
        
        // Configurar los encabezados para la descarga de PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Retornar el PDF como respuesta
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    
    @GetMapping("/earningscost")
    public ResponseEntity<byte[]> getBillsDescriptionByIdLocation(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws DocumentException, IOException {

        List<EarningResponse> responseList = this.getEarningsCostsInputPort.getEarningsCostsByDates(startDate, endDate);

        // Llamada para generar el PDF
        byte[] pdfBytes = this.pdfGenerator.generateEarningsCostsReport(responseList, "Reporte de Ingresos y Gastos");

        // Enviar el PDF como respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "EarningsCostsReport.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
