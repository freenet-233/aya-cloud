//package com.aya.service.qrcode.processor;//package com.wang.common.service.qrcode;
//
//import com.aya.service.qrcode.QRCodeResult;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//public class WeChatProcessor implements QRCodeProcessor{
//    @Override
//    public int sort() {
//        return 2;
//    }
//
//    @Override
//    public QRCodeResult process(String fileName) {
//        return null;
//    }
//
//
//    private static String deCode(Mat img) {
//        // 微信二维码对象，要返回二维码坐标前2个参数必传；后2个在二维码小或不清晰时必传。
//        WeChatQRCode we = new WeChatQRCode();
//        List<Mat> points = new ArrayList<Mat>();
//        // 微信二维码引擎解码，返回的valList中存放的是解码后的数据，points中Mat存放的是二维码4个角的坐标
//        StringVector stringVector = we.detectAndDecode(img);
//
//        if (stringVector.empty()) {
//            return "0";
//        }
//
//        System.out.println(stringVector.get(0).getString(StandardCharsets.UTF_8));
//        return stringVector.get(0).getString(StandardCharsets.UTF_8);
//
//    }
//
//
//    public static String openPic(String url) {
//
//        String savePath = "d:\\img";
//        String filename = UUID.randomUUID().toString() + ".jpg";
//        String re="0";
//        try {
//            dxz(url, savePath, filename);
//            Mat img = imread(savePath + "\\" + filename);
//            re=deCode(img);
//            //如果识别失败那么尝试放大缩小图片尝试识别，提高准确率
//            if(re.equals("0")) {
//                ys(savePath, filename);
//                Mat img1 = imread(savePath + "\\1_" + filename);
//                re=deCode(img1);
//            }
//            if(re.equals("0")) {
//                fd(savePath, filename);
//                Mat img2 = imread(savePath + "\\2_" + filename);
//                re=deCode(img2);
//            }
//            return re;
//
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "0";
//        }
//    }
//
//    private static void ys(String savePath, String filename) throws Exception {
//        try {
//            // 读取原始图片
//            BufferedImage image = ImageIO.read(new FileInputStream(savePath + "\\" + filename));
//            System.out.println("Width: " + image.getWidth());
//            System.out.println("Height: " + image.getHeight());
//            // 调整图片大小
//            BufferedImage newImage = ImageUtils.resizeImage(image, image.getWidth()*2, image.getHeight()*2);
//            // 图像缓冲区图片保存为图片文件(文件不存在会自动创建文件保存，文件存在会覆盖原文件保存)
//            ImageIO.write(newImage, "jpg", new File(savePath + "\\1_" + filename));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void fd(String savePath, String filename) throws Exception {
//        try {
//            // 读取原始图片
//            BufferedImage image = ImageIO.read(new FileInputStream(savePath + "\\" + filename));
//            System.out.println("Width: " + image.getWidth());
//            System.out.println("Height: " + image.getHeight());
//            // 调整图片大小
//            BufferedImage newImage = ImageUtils.resizeImage(image, 200, 200);
//            // 图像缓冲区图片保存为图片文件(文件不存在会自动创建文件保存，文件存在会覆盖原文件保存)
//            ImageIO.write(newImage, "jpg", new File(savePath + "\\2_" + filename));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void dxz(String urlString, String savePath, String filename) throws Exception {
//        // 构造URL
//        URL url = new URL(urlString);
//        // 打开连接
//        URLConnection con = url.openConnection();
//        // 设置请求超时为20s
//        con.setConnectTimeout(20 * 1000);
//        // 文件路径不存在 则创建
//        File sf = new File(savePath);
//        if (!sf.exists()) {
//            sf.mkdirs();
//        }
//        // jdk 1.7 新特性自动关闭
//        try (InputStream in = con.getInputStream();
//             OutputStream out = new FileOutputStream(sf.getPath() + "\\" + filename)) {
//            // 创建缓冲区
//            byte[] buff = new byte[1024];
//            int n;
//            // 开始读取
//            while ((n = in.read(buff)) >= 0) {
//                out.write(buff, 0, n);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
