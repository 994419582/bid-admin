package cn.teleinfo.bidadmin.blockchain.util;

import cn.hutool.core.img.ImgUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {

    public static String multipartFileToString(MultipartFile file) {
        String image = "";

        int n = 0;
        File f = new File(file.getOriginalFilename());
        try (InputStream in = file.getInputStream(); OutputStream os = new FileOutputStream(f)) {
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, n);
            }
            if (file.getContentType().contains("png")) {
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_PNG);
            } else if (file.getContentType().contains("gif")) {
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_GIF);
            } else if (file.getContentType().contains("jpg")) {
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_JPG);
            } else if (file.getContentType().contains("jpeg")) {
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_JPEG);
            } else if (file.getContentType().contains("bmp")) {
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_BMP);
            }
            f.delete();
        } catch (IOException e) {
            e.printStackTrace();
            f.delete();
        }

        return image;
    }
}
