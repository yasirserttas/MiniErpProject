package com.yasir.erp.minierp.modules.dispatchNote.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class DispatchNoteNotFoundException extends BusinessException {

    public DispatchNoteNotFoundException(String dispatchNoteId) {
        super("DISPATCH_NOTE_NOT_FOUND",
                String.format("DispatchNote not found. ID[%s]",dispatchNoteId));
    }
}
