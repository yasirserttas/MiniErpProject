package com.yasir.erp.minierp.modules.dispatchNote.application.exception;

public class DispatchNoteAlreadyFinalizedException extends RuntimeException {

    public DispatchNoteAlreadyFinalizedException(String dispatchNoteId) {
        super("The DispatchNote cannot be updated because it has already been finalized. DispatchNote ID: "
                + dispatchNoteId);
    }
}
