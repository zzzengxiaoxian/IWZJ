package common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.iwzj.ltkj.iwzj.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by dell on 2018/1/3.
 */

public class DrawableToBitmap {


    /**
     * Drawable -> Bitmap
     * @param drawable
     * @return
     */
   public static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

//
////    1.Drawable—>Bitmap
//    Resources res=getResources();
//    Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.sample_0);
//    Resources res=getResources();
//    private byte[] Bitmap2Bytes(Bitmap bm){
////        2.Bitmap---->Drawable
//        Drawable drawable =new BitmapDrawable(bmp);
//
////        3、Drawable → Bitmap
//        public static Bitmap drawableToBitmap(Drawable drawable) {
//
//            Bitmap bitmap = Bitmap.createBitmap(
//                    drawable.getIntrinsicWidth(),
//                    drawable.getIntrinsicHeight(),
//                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                            : Bitmap.Config.RGB_565);
//            Canvas canvas = new Canvas(bitmap);
//            //canvas.setBitmap(bitmap);
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            drawable.draw(canvas);
//            return bitmap;
//        }
////        4、从资源中获取Bitmap
//        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.pic);
////        5、Bitmap → byte[]
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();   }
////    6、 byte[] → Bitmap
//    private Bitmap Bytes2Bimap(byte[] b){
//        if(b.length!=0){
//            return BitmapFactory.decodeByteArray(b, 0, b.length);
//        }
//        else {
//            return null;
//        }
//    }


}
