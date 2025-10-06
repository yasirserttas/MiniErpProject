package com.yasir.erp.minierp.modules.user.application.converter;


import com.yasir.erp.minierp.modules.address.application.converter.AddressUserConverter;
import com.yasir.erp.minierp.modules.customer.application.converter.CustomerUserConverter;
import com.yasir.erp.minierp.modules.invoice.application.converter.InvoiceUserConverter;
import com.yasir.erp.minierp.modules.order.application.converter.OrderUserConverter;
import com.yasir.erp.minierp.modules.product.application.converter.ProductUserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final OrderUserConverter orderUserConverter;
    private final ProductUserConverter productUserConverter;
    private final CustomerUserConverter customerUserConverter;
    private final InvoiceUserConverter invoiceUserConverter;
    private final AddressUserConverter addressUserConverter;

    public UserConverter(OrderUserConverter orderUserConverter,
                         ProductUserConverter productUserConverter,
                         CustomerUserConverter customerUserConverter,
                         InvoiceUserConverter invoiceUserConverter,
                         AddressUserConverter addressUserConverter) {
        this.orderUserConverter = orderUserConverter;
        this.productUserConverter = productUserConverter;
        this.customerUserConverter = customerUserConverter;
        this.invoiceUserConverter = invoiceUserConverter;
        this.addressUserConverter = addressUserConverter;
    }

    public UserDto convertToUser(User user ){

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getUsername(),
                user.getCompanyName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getTaxNumber(),
                user.getCreatedAt(),
                addressUserConverter.convertToSetAddressUser(user.getAddresses()),
                orderUserConverter.convertToSetOrderUserDto(user.getOrders()),
                productUserConverter.convertToSetProductUserDto(user.getProducts()),
                customerUserConverter.convertToSetCustomerUserDto(user.getCustomers()),
                invoiceUserConverter.convertToSetInvoiceUserDto(user.getInvoices()),
                user.getRoles(),
                user.getActive()
        );
    }


}
