package com.yasir.erp.minierp.modules.customer.application.service.command;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.HardDeleteCustomerUseCase;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.command.CustomerCommandPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HardDeleteCustomerService implements HardDeleteCustomerUseCase {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;

    public HardDeleteCustomerService(CustomerCommandPort customerCommandPort,
                                     CustomerActiveQueryPort customerActiveQueryPort) {
        this.customerCommandPort = customerCommandPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
    }

    @Override
    public void hardDeleteCustomer(UUID customerId) {
        Customer existing = customerActiveQueryPort.findByIdAndActive(customerId, false)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerCommandPort.delete(existing);
    }
}
