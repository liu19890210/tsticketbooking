package guominchuxing.tsbooking.scanner.decode;

import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

public class BitmapLuminanceSource extends LuminanceSource {

    private byte bitmapPixels[];

    protected BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();


        // 首先，要取得该图片的像素数组内容
        int[] data = new int[width * height];
        this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        try {
            bitmap.getPixels(data, 0, width, 0, 0, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
        for (int i = 0, len = data.length; i < len; i++) {
            this.bitmapPixels[i] = (byte) data[i];
        }
    }

    @Override
    public byte[] getMatrix() {
        // 返回我们生成好的像素数据
        return bitmapPixels;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        // 这里要得到指定行的像素数据
        System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
        return row;
    }
}