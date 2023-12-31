package com.aya.cloud.core.service.qrcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class QRCodeResult {
    private List<String> qrCodeContexts;
    private String regularQrCodeText;
    private boolean handleSuccess;
    private String errorMessage;

    public static QRCodeResult success(List<String> qrCodeContexts){
        QRCodeResult result = new QRCodeResult();
        result.setQrCodeContexts(qrCodeContexts);
        result.setHandleSuccess(true);
        return result;
    }

    public static QRCodeResult fail(String errorMessage){
        QRCodeResult result = new QRCodeResult();
        result.setErrorMessage(errorMessage);
        result.setHandleSuccess(false);
        return result;
    }


}
