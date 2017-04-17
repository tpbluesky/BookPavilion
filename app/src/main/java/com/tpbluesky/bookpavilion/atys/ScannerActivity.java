package com.tpbluesky.bookpavilion.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.common.Intents;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.views.ToastMaker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.FileNotFoundException;

/**
 * 用于扫描二维码的Activity
 */
@ContentView(R.layout.activity_scanner)
public class ScannerActivity extends Activity implements OnScannerCompletionListener {

    public static final int REQUEST_CODE = 888;
    public static final int CHOOSE_PICTURE = 188;

    public static final String BAR_CODE_TYPE = "type";

    @ViewInject(R.id.scanner_view)
    private ScannerView scanner_view;
    @ViewInject(R.id.iv_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_light)
    private ImageView ivLight;
    @ViewInject(R.id.iv_photo)
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        scanner_view.setOnScannerCompletionListener(this);
//        mScannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
        scanner_view.setLaserGridLineResId(R.mipmap.zfb_grid_scan_line);//网格图
        scanner_view.setLaserFrameBoundColor(0xFF26CEFF);//设置颜色
    }

    @Event({R.id.iv_back, R.id.iv_light, R.id.iv_photo})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.iv_light:
                if (ivLight.isSelected()) {
                    scanner_view.toggleLight(false);
                    ivLight.setSelected(false);
                } else {
                    scanner_view.toggleLight(true);
                    ivLight.setSelected(true);
                }
                break;
            case R.id.iv_photo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), CHOOSE_PICTURE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner_view.onPause();
    }

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (rawResult == null) {
            ToastMaker.makeLongToast("未发现二维码");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra(Intents.Scan.RESULT, rawResult.getText());
        intent.putExtra("type", parsedResult.getType());
        setResult(RESULT_OK, intent);
        finish();
    }


    private ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                ContentResolver resolver = getContentResolver();
                final Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                dialog = new ProgressDialog(this);
                dialog.setMessage("正在扫描...");
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        QRDecode.decodeQR(bitmap, ScannerActivity.this);
                    }
                }, 400);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        QRDecode.decodeQR();
    }

    private Handler handler = new Handler();
}
