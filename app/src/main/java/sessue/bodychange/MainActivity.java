package sessue.bodychange;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import sessue.bodychange.OpenCV.Reader;

public class MainActivity extends AppCompatActivity {
    // メンバ変数
    private static final String TAG = "MainActivity"; // Debug TAG
    private Reader oppyReader;

    // Initialize OpenCV
    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV not loaded");
        } else {
            Log.d(TAG, "OpenCV loaded");
        }
    }

    //// ここからデフォルトの設定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // OpenCV
        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0,
                MainActivity.this, mOpenCVCallBack)) {
            Log.e("TEST", "Cannot connect to OpenCV Manager");
        }

        oppyReader = new Reader(this);
        oppyReader.DisplayImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ////ここまでデフォルトの設定
    // ここからOpenCVを使った処理
    // ライブラリ初期化完了後に呼ばれるコールバック (onManagerConnected)
    // public abstract class BaseLoaderCallback implements LoaderCallbackInterface
    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    //your code
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        // 非同期でライブラリの読み込み/初期化を行う
        // static boolean initAsync(String Version, Context AppContext,LoaderCallbackInterface Callback)
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_4, this, mOpenCVCallBack);
    }



}
