package org.example.demosecurity1.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Service
//public class QRCodeService {
//
//    public byte[] generateQRCodeImage(String barcodeText) throws Exception {
//        int width = 300;
//        int height = 300;
//        String imageFormat = "PNG";
//
//        Map<EncodeHintType, String> hintMap = new HashMap<>();
//        hintMap.put(EncodeHintType.MARGIN, "1");
//
//        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
//        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height, hintMap);
//
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        image.createGraphics().drawImage(image, 0, 0, null);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, imageFormat, outputStream);
//        return outputStream.toByteArray();
//    }
//}

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
        import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

@Service
public class QRCodeService {

    // Generates the QR code image as a byte array
    public byte[] generateQRCodeImage(String barcodeText) throws Exception {
        int width = 300;  // QR code width
        int height = 300; // QR code height
        String imageFormat = "PNG"; // Image format

        // Encoding hint options
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.MARGIN, 1);  // Optional: to set the margin (default is 4)

        // Generate QR code bit matrix
        BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.QR_CODE, width, height, hintMap);

        // Create a BufferedImage from the BitMatrix
        BufferedImage image = toBufferedImage(bitMatrix);

        // Convert the image to a byte array and return it
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, imageFormat, baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new Exception("Error converting QR code to byte array", e);
        }
    }

    // Converts the BitMatrix to a BufferedImage
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        // Paint the image with white background and black QR code
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        return image;
    }
}


