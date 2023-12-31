package com.aya.cloud.core.service.qrcode;

import com.aya.cloud.core.service.qrcode.processor.QRCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class QRCodeClient implements ApplicationContextAware {
    private List<QRCodeProcessor> qrCodeProcessors = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, QRCodeProcessor> qrCodeProcessorMap = applicationContext.getBeansOfType(QRCodeProcessor.class);
        qrCodeProcessors = qrCodeProcessorMap.values().stream()
                .sorted(Comparator.comparing(QRCodeProcessor::sort))
                .collect(Collectors.toList());
    }

    public QRCodeResult execute(String fileName) {
        for (QRCodeProcessor qrCodeProcessor : qrCodeProcessors){
            log.info("执行 {} 生成二维码", qrCodeProcessor.getClass().getName());
            QRCodeResult result = qrCodeProcessor.process(fileName);
            if (result.isHandleSuccess()){
                return result;
            }
        }
        return QRCodeResult.fail("二维码生成失败");
    }

}
