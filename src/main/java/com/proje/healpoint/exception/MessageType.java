package com.proje.healpoint.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST             ("1001","Kayıt bulunamadı"),
    RECORD_ALREADY_EXIST        ("1002","Kayıt zaten mevcut"),
    NOT_COMPLETED_APPOINTMENT   ("1003","Tamamlanmamış randevuya değerlendirme yapılamaz"),
    INVALID_INPUT               ("1004","Eksik bilgi"),
    INVALID_TOKEN               ("1005","Geçersiz token"),
    AUTHENTICATION_FAILED       ("1006","Kimlik doğrulama başarısız"),
    INVALID_USER_TYPE           ("1007","Geçersiz kullanıcı tipi:");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
