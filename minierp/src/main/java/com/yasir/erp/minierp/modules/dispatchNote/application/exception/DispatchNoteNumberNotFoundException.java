package com.yasir.erp.minierp.modules.dispatchNote.application.exception;

public class DispatchNoteNumberNotFoundException extends RuntimeException {


    public DispatchNoteNumberNotFoundException(String dispatchNoteNumber) {
        super("DispatchNote with number '" + dispatchNoteNumber + "' was not found.");
    }
}
