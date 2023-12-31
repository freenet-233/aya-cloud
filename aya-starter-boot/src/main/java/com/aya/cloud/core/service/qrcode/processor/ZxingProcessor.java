package com.aya.cloud.core.service.qrcode.processor;

import com.aya.cloud.core.service.qrcode.QRCodeResult;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ZxingProcessor implements QRCodeProcessor {

    @Override
    public int sort() {
        return 1;
    }

    @Override
    public QRCodeResult process(String fileName) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(fileName));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            System.out.println("图片中内容：  ");
            System.out.println("content： " + result.getText());
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            //这里判断如果识别不了带LOGO的图片，重新添加上一个属性
            try {
                image = ImageIO.read(new File(fileName));
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
                //设置编码格式
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                //设置优化精度
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                //设置复杂模式开启（我使用这种方式就可以识别微信的二维码了）
                hints.put(DecodeHintType.PURE_BARCODE,Boolean.TYPE);
                Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
                System.out.println("图片中内容：  ");
                System.out.println("content： " + result.getText());
                content = result.getText();
            } catch (IOException | NotFoundException e1) {
                e1.printStackTrace();
            }
        }
        QRCodeResult result = new QRCodeResult();
        List<String> qrCodeContexts = new ArrayList<>();
        qrCodeContexts.add(content);
        QRCodeResult.success(qrCodeContexts);
        return result;

    }

    public static void main(String[] args) {
        ZxingProcessor processor = new ZxingProcessor();
        QRCodeResult result = processor.process("/home/wang/02media/4f4ecad39002d7af49bf35cb0053d77d.jpg");
        System.out.println(result.toString());
    }
}
