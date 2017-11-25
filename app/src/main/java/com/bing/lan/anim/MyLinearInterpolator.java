package com.bing.lan.anim;/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BaseInterpolator;

/**
 * An interpolator where the rate of change is constant
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
public class MyLinearInterpolator extends BaseInterpolator {

    public MyLinearInterpolator() {
    }

    public MyLinearInterpolator(Context context, AttributeSet attrs) {
    }

    public float getInterpolation(float input) {

        Log.e("getInterpolation()", "----input----" + input);
        Log.e("getInterpolation()", "----input * 0.5f----" + input * 0.5f);

        return input  ;
        //return input * 0.5f;
    }
}
