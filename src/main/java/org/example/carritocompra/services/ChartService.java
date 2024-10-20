package org.example.carritocompra.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.carritocompra.models.Producto;
import org.example.carritocompra.repositories.ProductRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ChartService {

    private ProductRepo productRepo;

    public void addProduct(Long id) {
        productRepo.findById(id).ifPresent(product -> {
            product.setOnChart(true);
            productRepo.save(product);
        });
    }

    public void removeProduct(Long id) {
        productRepo.findById(id).ifPresent(product -> {
            product.setOnChart(false);
            productRepo.save(product);
        });
    }

    @Transactional
    public void generateOrder(HttpServletResponse response) {
        List<Producto> productos = productRepo.findAllByOnChart(true);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph header = new Paragraph("Orden de Compra", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            Paragraph date = new Paragraph("Fecha: " + currentDate, FontFactory.getFont(FontFactory.HELVETICA, 12));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Nombre del Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Precio", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            table.addCell(cell1);
            table.addCell(cell2);

            double total = 0.0;
            for (Producto producto : productos) {
                table.addCell(new PdfPCell(new Paragraph(producto.getNombre())));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(producto.getPrecio()))));
                total += producto.getPrecio();
            }

            PdfPCell totalCell1 = new PdfPCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            totalCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell totalCell2 = new PdfPCell(new Paragraph(String.valueOf(total), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            totalCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell1);
            table.addCell(totalCell2);

            document.add(table);

            document.close();

            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf");
            response.setContentLength(baos.size());

            productRepo.setAllProductsNotInChart();

            try (OutputStream os = response.getOutputStream()) {
                baos.writeTo(os);
                os.flush();
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
