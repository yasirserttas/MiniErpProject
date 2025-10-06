package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.query.FindOrderByIdUseCase;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.query.ListOrderItemsByOrderUseCase;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;


@Service
public class DispatchNotePdfService {

    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final ListOrderItemsByOrderUseCase listOrderItemsByOrderUseCase;

    public DispatchNotePdfService(FindOrderByIdUseCase findOrderByIdUseCase,
                                  ListOrderItemsByOrderUseCase listOrderItemsByOrderUseCase) {
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.listOrderItemsByOrderUseCase = listOrderItemsByOrderUseCase;
    }

    public byte[] generateDispatchNotePdf(DispatchNoteDto dispatchNoteDto) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, PageSize.A4);


            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);


            doc.add(new Paragraph("SEVK İRSALİYESİ")
                    .setFont(bold)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));


            Table info = new Table(new float[]{4, 8})
                    .setWidth(UnitValue.createPercentValue(100));
            info.addCell(new Cell().add(new Paragraph("İrsaliye No:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(dispatchNoteDto.getDispatchNoteNumber())));
            info.addCell(new Cell().add(new Paragraph("Tarih:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(dispatchNoteDto.getDispatchDate().toString())));
            info.addCell(new Cell().add(new Paragraph("Durum:").setFont(bold)));
            info.addCell(new Cell().add(new Paragraph(dispatchNoteDto.getDispatchNoteStatus().name())));
            doc.add(info);


            Table parties = new Table(new float[]{3, 3, 6})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10f);
            parties.addHeaderCell(new Cell().add(new Paragraph("Gönderen").setFont(bold)));
            parties.addHeaderCell(new Cell().add(new Paragraph("Alıcı").setFont(bold)));
            parties.addHeaderCell(new Cell().add(new Paragraph("Teslimat Adresi").setFont(bold)));


            parties.addCell(new Cell().add(new Paragraph(dispatchNoteDto.getUser().getName())));

            parties.addCell(new Cell().add(new Paragraph(dispatchNoteDto.getCustomer().getName())));

            AddressDto addr = dispatchNoteDto.getDeliveryAddress();
            String fullAddr = addr.getStreet() + ", " + addr.getDistrict() + " / " + addr.getCity();
            parties.addCell(new Cell().add(new Paragraph(fullAddr)));

            doc.add(parties);


            if (dispatchNoteDto.getTransporterName() != null || dispatchNoteDto.getTransporterPlate() != null) {
                Table trans = new Table(new float[]{3, 3})
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginTop(10f);
                trans.addHeaderCell(new Cell().add(new Paragraph("Taşıyıcı").setFont(bold)));
                trans.addHeaderCell(new Cell().add(new Paragraph("Plaka").setFont(bold)));
                trans.addCell(new Cell().add(new Paragraph(
                        dispatchNoteDto.getTransporterName() != null ? dispatchNoteDto.getTransporterName() : "-")));
                trans.addCell(new Cell().add(new Paragraph(
                        dispatchNoteDto.getTransporterPlate() != null ? dispatchNoteDto.getTransporterPlate() : "-")));
                doc.add(trans);
            }


            doc.add(new Paragraph("Sevk Edilen Kalemler")
                    .setFont(bold)
                    .setFontSize(14)
                    .setMarginTop(20f));


            Table items = new Table(new float[]{1, 6, 2})
                    .setWidth(UnitValue.createPercentValue(100));
            items.addHeaderCell(new Cell().add(new Paragraph("No").setFont(bold)));
            items.addHeaderCell(new Cell().add(new Paragraph("Ürün").setFont(bold)));
            items.addHeaderCell(new Cell().add(new Paragraph("Adet").setFont(bold)));

            OrderDto order = findOrderByIdUseCase.
                    findDtoByIdAndActive(dispatchNoteDto.getOrder().getId(),
                            dispatchNoteDto.getOrder().getActive());

            Set<OrderItemDto> orderItemDtoSet =
                    listOrderItemsByOrderUseCase.listOrderItemSetDtoByOrderId(order.getId());


            int i = 1;
            for (OrderItemDto it :orderItemDtoSet) {
                items.addCell(new Cell().add(new Paragraph(String.valueOf(i++))));
                items.addCell(new Cell().add(new Paragraph(it.getProduct().getName())));
                items.addCell(new Cell().add(new Paragraph(String.valueOf(it.getQuantity()))));
            }
            doc.add(items);


            Table footer = new Table(new float[]{4, 4, 4})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(20f);
            footer.addCell(new Cell().add(new Paragraph("Tahmini Varış:").setFont(bold)));
            footer.addCell(new Cell().add(new Paragraph("Teslim Eden:").setFont(bold)));
            footer.addCell(new Cell().add(new Paragraph("Teslim Alan:").setFont(bold)));

            footer.addCell(new Cell().add(new Paragraph(
                    dispatchNoteDto.getEstimatedArrival() != null
                            ? dispatchNoteDto.getEstimatedArrival().toString() : "-")));
            footer.addCell(new Cell().add(new Paragraph(
                    dispatchNoteDto.getDeliveredBy() != null ? dispatchNoteDto.getDeliveredBy() : "-")));
            footer.addCell(new Cell().add(new Paragraph(
                    dispatchNoteDto.getReceivedBy() != null ? dispatchNoteDto.getReceivedBy() : "-")));
            doc.add(footer);

            if (!dispatchNoteDto.getActive()) {
                PdfExtGState gs = new PdfExtGState().setFillOpacity(0.3f);
                PdfFont wmFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                int n = pdf.getNumberOfPages();
                for (int p = 1; p <= n; p++) {
                    PdfCanvas canvas = new PdfCanvas(pdf.getPage(p));
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

        } catch (IOException ex) {
            throw new RuntimeException("Dispatch Note PDF oluşturulamadı", ex);
        }

    }
}
