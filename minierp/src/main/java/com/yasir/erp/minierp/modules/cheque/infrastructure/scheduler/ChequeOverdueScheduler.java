package com.yasir.erp.minierp.modules.cheque.infrastructure.scheduler;

import com.yasir.erp.minierp.modules.cheque.domain.port.inbound
        .command.MarkOverdueChequesIfNecessaryUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

    @Component
    public class ChequeOverdueScheduler {

        private final MarkOverdueChequesIfNecessaryUseCase markOverdueChequesIfNecessary;

        public ChequeOverdueScheduler(
                MarkOverdueChequesIfNecessaryUseCase markOverdueChequesIfNecessary) {
            this.markOverdueChequesIfNecessary = markOverdueChequesIfNecessary;
        }

        @Scheduled(cron = "0 0 2 * * *")
        public void run() {
            markOverdueChequesIfNecessary.markOverdueChequesIfNecessary();
        }
    }