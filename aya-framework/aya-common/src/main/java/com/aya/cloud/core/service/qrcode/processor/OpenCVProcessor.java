package com.aya.cloud.core.service.qrcode.processor;

import com.aya.cloud.core.service.qrcode.QRCodeResult;
import com.aya.cloud.core.service.qrcode.QRCodeTool;
import lombok.extern.slf4j.Slf4j;

import nu.pattern.OpenCV;
import org.apache.commons.lang3.StringUtils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OpenCVProcessor implements QRCodeProcessor {

    static {
//        QRCodeTool.loadLibraries();
        OpenCV.loadLocally();
    }

    @Override
    public int sort() {
        return 0;
    }

    @Override
    public QRCodeResult process(String fileName) {

        try {
//            QRCodeTool.loadLibraries();
            String qrCodeText = decodeQRCode(fileName);
            if (StringUtils.isEmpty(qrCodeText)) {
                throw new RuntimeException();
            }
            log.info("opencv解析[{}]二维码成功:{}", fileName, qrCodeText);
            List<String> qrCodeTextList = List.of(qrCodeText.trim());
            return QRCodeResult.success(qrCodeTextList);

        } catch (RuntimeException e) {
            log.error("opencv解析 {} 二维码失败", fileName, e);
            return QRCodeResult.fail("opencv解析二维码失败");
        }
    }

    public String decodeQRCode(String path) {
        Mat img = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
        QRCodeDetector detector = new QRCodeDetector();
        return detector.detectAndDecode(img);
    }

    public static void main(String[] args) {
//        QRCodeTool.loadLibraries();

        OpenCVProcessor processor = new OpenCVProcessor();
        processor.process("/home/wang/02media/IMG_0653(20231206-093724).JPG");
    }


}
