package com.yasir.erp.minierp.modules.promissoryNote.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class PromissoryNoteNotFoundException extends BusinessException {
    public PromissoryNoteNotFoundException(String promissoryNoteId) {
        super("PROMISSORY_NOTE_NOT_FOUND" ,
                String.format("PromissoryNote not found. ID[%s]",promissoryNoteId));
    }
}
