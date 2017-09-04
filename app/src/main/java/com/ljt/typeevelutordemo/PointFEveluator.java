package com.ljt.typeevelutordemo;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by 1 on 2017/9/4.
 */

public class PointFEveluator implements TypeEvaluator<PointF> {
    float x;
    float y;

    @Override
    public PointF evaluate(float v, PointF start, PointF end){
        PointF pointF = new PointF();
        pointF.x=start.x+v*(end.x-start.x);
        pointF.y=start.y+v*v*(end.y-start.y);

        return pointF;
    }
}
