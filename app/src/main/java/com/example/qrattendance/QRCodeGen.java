package obstinate.demon.qrcodeattendancesystem;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGen {
    private int height;
    private int width;
    Bitmap bitmap=null;
    public QRCodeGen(int width, int height)
    {
        this.height = height;
        this.width = width;
    }
    public Bitmap generateQRCode(String text)
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x<width; x++){
                for (int y=0; y<height; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
