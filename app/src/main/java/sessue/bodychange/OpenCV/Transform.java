package sessue.bodychange.OpenCV;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import sessue.bodychange.R;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vesp on 16/05/30.
 */
public class Transform {
    //Activity activity;

    //private Transform(){}
    /*public Transform(Activity inActivity){
        activity = inActivity;
    }*/
    public Transform(){}

    public Bitmap TransformImage(Resources appResource){
        // 画像読み込み（OpenCVでなく、アンドロイドの関数）
        // アプリのリソースを管理するインスタンス　getResourcesはActivityのクラス
        //Resources appResource = activity.getResources();
        Bitmap oppyBmpSrc = BitmapFactory.decodeResource(appResource, R.drawable.anime2);
        //Bitmap oppyBmpSrc = BitmapFactory.decodeResource(appResource, R.drawable.real2);

        //  画像をMatへ変換
        Mat oppyMat = new Mat(oppyBmpSrc.getHeight(), oppyBmpSrc.getWidth(), CvType.CV_8UC1);;
        Utils.bitmapToMat(oppyBmpSrc, oppyMat);

        //  グレースケール変換
        Imgproc.cvtColor(oppyMat, oppyMat, Imgproc.COLOR_RGB2GRAY);

        //  Cannyでエッジ検出
        Imgproc.Canny(oppyMat, oppyMat, 80, 100);

        //  Bitmapに変換する前にRGB形式に変換
        Imgproc.cvtColor(oppyMat, oppyMat, Imgproc.COLOR_GRAY2RGBA, 4);

        //  Bitmap dst に空のBitmapを作成
        Bitmap oppyBmpDst = Bitmap.createBitmap(oppyMat.width(), oppyMat.height(), Bitmap.Config.ARGB_8888);

        //  MatからBitmapに変換
        Utils.matToBitmap(oppyMat, oppyBmpDst);

        return oppyBmpDst;

        // 画像を保存する
        /*
        try{
            saveBitmap(oppyBmpDst);
        }
        catch(IOException e){
            Toast.makeText(this, "画像が保存できませぬ", Toast.LENGTH_LONG).show();
        }
        */
    }

    // 画像を保存する
    private void saveBitmap(Bitmap saveImage) throws IOException {

        final String SAVE_DIR = "/MyPhoto/";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        try{
            if(!file.exists()){
                file.mkdir();
            }
        }catch(SecurityException e){
            e.printStackTrace();
            throw e;
        }

        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = fileNameDate.format(mDate) + ".jpg";
        String AttachName = file.getAbsolutePath() + "/" + fileName;

        try {
            FileOutputStream out = new FileOutputStream(AttachName);
            saveImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw e;
        }

        /*
        // save index
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        */
    }

    // DeepCopyするためのメソッド
    protected Object clone() throws CloneNotSupportedException {
        // このオブジェクトのコピーを記述します。
        // この例だと、実はあまり意味がありません（return super.clone();でによる浅いコピーを返だけでも同じ）。
        Transform cp = new Transform();
        //cp.s = this.s;
        return cp;
    }
}
