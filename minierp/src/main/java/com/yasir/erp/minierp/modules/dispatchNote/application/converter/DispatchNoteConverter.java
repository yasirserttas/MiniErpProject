package com.yasir.erp.minierp.modules.dispatchNote.application.converter;

import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.customer.application.converter.CustomerDispatchNoteConverter;
import com.yasir.erp.minierp.modules.order.application.converter.OrderDispatchNoteConverter;
import com.yasir.erp.minierp.modules.user.application.converter.UserDispatchNoteConverter;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DispatchNoteConverter {

    private final AddressConverter addressConverter;
    private final UserDispatchNoteConverter userDispatchNoteConverter;
    private final CustomerDispatchNoteConverter customerDispatchNoteConverter;
    private final OrderDispatchNoteConverter orderDispatchNoteConverter;

    public DispatchNoteConverter(AddressConverter addressConverter,
                                 UserDispatchNoteConverter userDispatchNoteConverter,
                                 CustomerDispatchNoteConverter customerDispatchNoteConverter,
                                 OrderDispatchNoteConverter orderDispatchNoteConverter) {
        this.addressConverter = addressConverter;
        this.userDispatchNoteConverter = userDispatchNoteConverter;
        this.customerDispatchNoteConverter = customerDispatchNoteConverter;
        this.orderDispatchNoteConverter = orderDispatchNoteConverter;
    }

    public DispatchNoteDto convertToDispatchNoteDto(DispatchNote e) {
        return new DispatchNoteDto(
                e.getId(),
                userDispatchNoteConverter.convertToUserDispatchNoteDto(e.getUser()),
                customerDispatchNoteConverter.convertToCustomerDispatchNoteDto(e.getCustomer()),
                e.getOrder() != null ? orderDispatchNoteConverter.convertToOrderDispatchNoteDto(e.getOrder()) : null,
                addressConverter.convertToAddress(e.getDeliveryAddress()),
                e.getTransporterName(),
                e.getTransporterPlate(),
                e.getDispatchDate(),
                e.getEstimatedArrival(),
                e.getCreatedAt(),

                e.getUpdateAt(),
                e.getDispatchNoteStatus(),
                e.getDispatchNoteNumber(),
                e.getDeliveredBy(),
                e.getReceivedBy(),
                e.getActive()
        );
    }

    public Set<DispatchNoteDto> convertToDispatchNoteSetDto(Set<DispatchNote> set) {
        return set.stream().map(this::convertToDispatchNoteDto).collect(Collectors.toSet());
    }
}
