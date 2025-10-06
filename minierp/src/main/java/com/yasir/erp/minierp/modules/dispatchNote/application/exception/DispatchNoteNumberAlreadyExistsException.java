package com.yasir.erp.minierp.modules.dispatchNote.application.exception;

public class DispatchNoteNumberAlreadyExistsException extends RuntimeException{
    public DispatchNoteNumberAlreadyExistsException(String dispatchNoteNumber) {
        super("Dispatch Note Number already exists: " + dispatchNoteNumber);
    }
}
