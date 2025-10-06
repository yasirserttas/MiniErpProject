package com.yasir.erp.minierp.modules.promissoryNote.application.exception;

public class PromissoryNoteNumberAlreadyExistsException extends RuntimeException {

    public PromissoryNoteNumberAlreadyExistsException(String noteNumber) {
        super("Promissory Note with number '" + noteNumber + "' already exists.");
    }
}
