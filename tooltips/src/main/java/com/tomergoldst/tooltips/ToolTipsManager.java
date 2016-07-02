/*
Copyright 2016 Tomer Goldstein

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.tomergoldst.tooltips;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Outline;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ToolTipsManager {

    private static final String TAG = ToolTipsManager.class.getSimpleName();

    private static final int DEFAULT_ANIM_DURATION = 400;

    // Parameter for managing tip creation or reuse
    private Map<Integer, View> mTipsMap = new HashMap<>();

    private int mAnimationDuration;
    private TipListener mListener;

    public interface TipListener {
        void onTipDismissed(View view, boolean byUser);
    }

    public ToolTipsManager(){
        mAnimationDuration = DEFAULT_ANIM_DURATION;
    }

    public ToolTipsManager(TipListener listener){
        mAnimationDuration = DEFAULT_ANIM_DURATION;;
        mListener = listener;
    }

    public View show(ToolTip toolTip) {
        View tipView = create(toolTip);
        if (tipView == null) {
            return null;
        }

        // animate tip visibility
        AnimationUtils.popup(tipView, mAnimationDuration).start();

        return tipView;
    }

    private View create(ToolTip toolTip) {

        if (toolTip.getAnchorView() == null) {
            Log.e(TAG, "Unable to create a tip, anchor view is null");
            return null;
        }

        if (toolTip.getRootView() == null) {
            Log.e(TAG, "Unable to create a tip, root layout is null");
            return null;
        }

        // only one tip is allowed near an anchor view at the same time, thus
        // reuse tip if already exist
        if (mTipsMap.containsKey(toolTip.getAnchorView().hashCode())) {
            return mTipsMap.get(toolTip.getAnchorView().hashCode());
        }

        // init tip view parameters
        TextView tipView = createTipView(toolTip);

        // on RTL languages replace sides
        if (UiUtils.isRtl()) {
            switchToolTipSidePosition(toolTip);
        }

        // set tool tip background / shape
        ToolTipBackgroundConstructor.setBackground(tipView, toolTip);

        // add tip to root layout
        toolTip.getRootView().addView(tipView);

        // find where to position the tool tip
        Point p = ToolTipCoordinatesFinder.getCoordinates(tipView, toolTip);

        // move tip view to correct position
        moveTipToCorrectPosition(tipView, p);

        // set dismiss on click
        tipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss(view, true);
            }
        });

        // store anchorView hashcode with tipView
        int hashCode = toolTip.getAnchorView().hashCode();
        tipView.setTag(hashCode);

        // enter tip to map by 'anchorView' hash code
        mTipsMap.put(hashCode, tipView);

        return tipView;

    }

    private void moveTipToCorrectPosition(TextView tipView, Point p) {
        Coordinates tipViewCoordinates = new Coordinates(tipView);
        int translationX = p.x - tipViewCoordinates.left;
        int translationY = p.y - tipViewCoordinates.top;
        tipView.setTranslationX(!UiUtils.isRtl() ? translationX : -translationX);
        tipView.setTranslationY(translationY);
    }

    @NonNull
    private TextView createTipView(ToolTip toolTip) {
        TextView tipView = new TextView(toolTip.getContext());
        tipView.setTextColor(toolTip.getTextColor());
        tipView.setText(toolTip.getMessage());
        tipView.setVisibility(View.INVISIBLE);
        tipView.setGravity(toolTip.getTextGravity());
        setTipViewElevation(tipView, toolTip);
        return tipView;
    }

    private void setTipViewElevation(TextView tipView, ToolTip toolTip) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (toolTip.getElevation() > 0) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                    @SuppressLint("NewApi")
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setEmpty();
                    }
                };
                tipView.setOutlineProvider(viewOutlineProvider);
                tipView.setElevation(toolTip.getElevation());
            }
        }
    }

    private void switchToolTipSidePosition(ToolTip toolTip) {
        if (toolTip.positionedLeftTo()) {
            toolTip.setPosition(ToolTip.POSITION_RIGHT_TO);
        } else if (toolTip.positionedRightTo()) {
            toolTip.setPosition(ToolTip.POSITION_LEFT_TO);
        }
    }

    public void setAnimationDuration(int duration){
        mAnimationDuration = duration;
    }

    public boolean dismiss(View tipView, boolean byUser) {
        if (tipView != null && isVisible(tipView)) {
            int key = (int) tipView.getTag();
            mTipsMap.remove(key);
            animateDismiss(tipView, byUser);
            return true;
        }
        return false;
    }

    public boolean dismiss(Integer key) {
        return mTipsMap.containsKey(key) && dismiss(mTipsMap.get(key), false);
    }

    public View find(Integer key) {
        if (mTipsMap.containsKey(key)) {
            return mTipsMap.get(key);
        }
        return null;
    }

    public boolean findAndDismiss(final View anchorView) {
        View view = find(anchorView.hashCode());
        return view != null && dismiss(view, false);
    }

    public void clear() {
        if (!mTipsMap.isEmpty()) {
            for (Map.Entry<Integer, View> entry : mTipsMap.entrySet()) {
                dismiss(entry.getValue(), false);
            }
        }
        mTipsMap.clear();
    }

    private void animateDismiss(final View view, final boolean byUser) {
        AnimationUtils.popout(view, mAnimationDuration, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mListener != null){
                    mListener.onTipDismissed(view, byUser);
                }
            }
        }).start();
    }

    public boolean isVisible(View tipView) {
        return tipView.getVisibility() == View.VISIBLE;
    }

}
