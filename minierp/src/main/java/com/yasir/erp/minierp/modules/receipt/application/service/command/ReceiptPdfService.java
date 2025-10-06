package com.yasir.erp.minierp.modules.receipt.application.service.command;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class ReceiptPdfService {

    public byte[] generateReceiptPdf(ReceiptDto receipt) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, PageSize.A4);


            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);


            doc.add(new Paragraph("ÖDEME MAKBUZU")
                    .setFont(bold)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));


            Table info = new Table(new float[]{4, 8})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10f);
            info.addCell(new Cell().add(new Paragraph("Makbuz No:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(receipt.getReceiptNumber())));
            info.addCell(new Cell().add(new Paragraph("Tarih:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(receipt.getReceiptDate().toString())));
            info.addCell(new Cell().add(new Paragraph("Keseni:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(receipt.getIssuedBy())));
            info.addCell(new Cell().add(new Paragraph("Alan:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(receipt.getReceivedBy())));
            doc.add(info);


            PaymentDto pay = receipt.getPayment();
            Table payTable = new Table(new float[]{4, 8})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10f);
            payTable.addCell(new Cell().add(new Paragraph("Ödeme ID:").setFont(bold)));
            payTable.addCell(new Cell().add(new Paragraph(pay.getId())));
            payTable.addCell(new Cell().add(new Paragraph("Tutar:").setFont(bold)));
            payTable.addCell(new Cell().add(new Paragraph(pay.getAmount().toString())));
            doc.add(payTable);


            doc.add(new Paragraph("Teşekkür ederiz!")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20f));

            if (!receipt.getActive()) {
                PdfExtGState gs = new PdfExtGState().setFillOpacity(0.3f);
                PdfFont wmFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                int n = pdf.getNumberOfPages();
                for (int i = 1; i <= n; i++) {
                    PdfCanvas canvas = new PdfCanvas(pdf.getPage(i));
                    canvas.saveState();
                    canvas.setExtGState(gs);
                    canvas.beginText()
                            .setFontAndSize(wmFont, 60)
                            .moveText(150, 400)
                            .showText("İPTAL EDİLDİ")
                            .endText();
                    canvas.restoreState();
                }
            }


            doc.close();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Receipt PDF oluşturulamadı", e);
        }


    }
}
