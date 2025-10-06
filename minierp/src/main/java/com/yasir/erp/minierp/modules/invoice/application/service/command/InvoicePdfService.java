package com.yasir.erp.minierp.modules.invoice.application.service.command;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.query.FindOrderByIdUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemOrderDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoicePdfService {

    private static final Logger logger = LoggerFactory.getLogger(InvoicePdfService.class);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final FindOrderByIdUseCase findOrderByIdUseCase;

    public InvoicePdfService(OrderActiveQueryPort orderActiveQueryPort,
                             FindOrderByIdUseCase findOrderByIdUseCase) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
    }

    public byte[] generateInvoicePdf(InvoiceDto invoice) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.A4);
            doc.setMargins(36, 36, 36, 36);

            PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont    = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);


            doc.add(new Paragraph("FATURA")
                    .setFont(boldFont).setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f));


            Table info = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(16f);

            addInfo(info, "Fatura No:", boldFont);
            addInfo(info, dash(invoice.getInvoiceNumber()), regularFont);

            addInfo(info, "Fatura Tarihi:", boldFont);
            addInfo(info, invoice.getInvoiceDate()==null ? "-" : invoice.getInvoiceDate().format(DATE_FMT), regularFont);

            addInfo(info, "Oluşturulma:", boldFont);
            addInfo(info, invoice.getCreatedAt()==null ? "-" : invoice.getCreatedAt().format(DATE_FMT), regularFont);

            addInfo(info, "Güncellenme:", boldFont);
            addInfo(info, invoice.getUpdatedAt()==null ? "-" : invoice.getUpdatedAt().format(DATE_FMT), regularFont);

            addInfo(info, "Düzenleyen:", boldFont);
            addInfo(info, dash(invoice.getIssuedBy()), regularFont);

            addInfo(info, "Alıcı:", boldFont);
            addInfo(info, dash(invoice.getReceivedBy()), regularFont);

            doc.add(info);


            OrderDto orderDto = null;
            String orderId = (invoice.getOrder()!=null) ? invoice.getOrder().getId() : null;
            if (orderId != null && !orderId.isBlank()) {
                Order ordOpt = orderActiveQueryPort.
                        findByIdAndActive(orderId, true).
                        orElseThrow(()-> new OrderNotFoundException(orderId));
                orderDto = findOrderByIdUseCase.findDtoByIdAndActive(ordOpt.getId(),true);
            }


            doc.add(new Paragraph("Ürün / Hizmetler")
                    .setFont(boldFont).setFontSize(14).setMarginBottom(10f));

            Table items = new Table(UnitValue.createPercentArray(new float[]{1, 6, 2, 3, 3, 3, 3}))
                    .setWidth(UnitValue.createPercentValue(100));

            addHead(items, "No", boldFont);
            addHead(items, "Ürün", boldFont);
            addHead(items, "Adet", boldFont);
            addHead(items, "Vergi Oranı", boldFont);
            addHead(items, "Vergi Tutarı", boldFont);
            addHead(items, "Birim Fiyat", boldFont);
            addHead(items, "Tutar", boldFont);

            int idx = 1;
            if (orderDto != null && orderDto.getOrderItems() != null) {
                for (OrderItemOrderDto it : safe(orderDto.getOrderItems())) {
                    if (it == null) continue;

                    String productText = String.valueOf(it.getProduct()); // toString() güvenli
                    String qty   = String.valueOf(it.getQuantity());
                    String rate  = it.getTaxRateApplied()==null ? "-" : it.getTaxRateApplied().toPlainString()+"%";
                    String tax   = money(it.getTaxAmount());
                    String unit  = money(it.getUnitPrice());
                    String total = money(it.getTotalPriceWithTax());

                    items.addCell(new Cell().add(new Paragraph(String.valueOf(idx++)).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(productText).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(qty).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(rate).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(tax).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(unit).setFont(regularFont)));
                    items.addCell(new Cell().add(new Paragraph(total).setFont(regularFont)));
                }
            } else {
                items.addCell(new Cell(1,7).add(new Paragraph("Kalem bulunamadı").setFont(regularFont)));
            }
            doc.add(items);


            String grand = money(
                    invoice.getFinalAmount() != null ? invoice.getFinalAmount() : calcFromOrder(orderDto)
            );

            doc.add(new Paragraph("Genel Toplam: " + grand)
                    .setFont(boldFont).setFontSize(12)
                    .setTextAlignment(TextAlignment.RIGHT).setMarginTop(18f));

            // Watermark
            boolean cancelled = Optional.ofNullable(orderDto)
                    .map(o -> o.getOrderStatus() == OrderStatus.CANCELLED)
                    .orElse(false);
            boolean inactive = !invoice.getActive();
            if (cancelled || inactive) {
                addMark(pdfDoc, cancelled ? "İPTAL EDİLDİ" : "PASİF", boldFont);
            }

            doc.close();
            return baos.toByteArray();

        } catch (IOException e) {
            logger.error("PDF oluşturulurken hata.", e);
            throw new RuntimeException("PDF oluşturulamadı.", e);
        }
    }


    private static void addInfo(Table t, String s, PdfFont f){
        t.addCell(new Cell().add(new Paragraph(s).setFont(f)).setBorder(null));
    }
    private static void addHead(Table t, String s, PdfFont f){
        t.addHeaderCell(new Cell().add(new Paragraph(s).setFont(f).setTextAlignment(TextAlignment.CENTER)));
    }
    private static String dash(String s){ return (s==null || s.isBlank()) ? "-" : s; }
    private static String money(BigDecimal v){ return v==null ? "-" : v.toPlainString(); }
    private static Set<OrderItemOrderDto> safe(Set<OrderItemOrderDto> s){
        return s==null ? java.util.Collections.emptySet() : s;
    }

    private static BigDecimal calcFromOrder(OrderDto o){
        if (o==null || o.getOrderItems()==null) return BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItemOrderDto it : safe(o.getOrderItems())){
            if (it==null) continue;
            BigDecimal line = Optional.ofNullable(it.getTotalPriceWithTax()).orElseGet(() -> {
                BigDecimal qty  = BigDecimal.valueOf(it.getQuantity());
                BigDecimal unit = Optional.ofNullable(it.getUnitPrice()).orElse(BigDecimal.ZERO);
                BigDecimal tax  = Optional.ofNullable(it.getTaxAmount()).orElse(BigDecimal.ZERO);
                return unit.multiply(qty).add(tax);
            });
            sum = sum.add(line);
        }
        return sum;
    }

    private static void addMark(PdfDocument pdfDoc, String text, PdfFont font){
        for (int i=1;i<=pdfDoc.getNumberOfPages();i++){
            PdfPage p = pdfDoc.getPage(i);
            Rectangle ps = p.getPageSize();
            PdfCanvas c = new PdfCanvas(p);
            c.setExtGState(new PdfExtGState().setFillOpacity(0.2f));
            new Canvas(c, ps)
                    .setFontColor(ColorConstants.RED)
                    .setFontSize(60)
                    .setFont(font)
                    .showTextAligned(new Paragraph(text),
                            ps.getWidth()/2, ps.getHeight()/2, i,
                            TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
        }
    }
}
