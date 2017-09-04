package com.ljt.typeevelutordemo;

import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_src;
    private boolean b = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏

        Bitmap bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawCircle(10, 10, 10, paint);

        iv_src = new ImageView(this);
        iv_src.setImageBitmap(bitmap);
        setContentView(iv_src, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            b = !b;
            Point point = new Point();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);

            ValueAnimator animator = new ValueAnimator().setDuration(1000);
            //通过new创建的ValueAnimator要明确的setObjectValues和setEvaluator，并且一定要先setObjectValues，再setEvaluator
            if (b) animator.setObjectValues(new PointF(0, 0), new PointF(point.x - iv_src.getWidth(), point.y - iv_src.getHeight()));
            else animator.setObjectValues(new PointF(0, point.y - iv_src.getHeight()), new PointF(point.x - iv_src.getWidth(), 0));
            animator.setEvaluator(new PointFEveluator());//PointFEvaluator为自定义的估值器，其实就是用来封装你需要的数据用的
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF point = (PointF) animation.getAnimatedValue();
                    iv_src.setX(point.x);
                    iv_src.setY(point.y);
                }
            });
            animator.start();
        }


        return super.onTouchEvent(event);
    }
}
