
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

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tomer on 01/07/2016.
 *
 *
 */
public class ToolTip {

    @IntDef({POSITION_ABOVE, POSITION_BELOW, POSITION_LEFT_TO, POSITION_RIGHT_TO})
    public @interface Position {}
    public static final int POSITION_ABOVE = 0;
    public static final int POSITION_BELOW = 1;
    public static final int POSITION_LEFT_TO = 3;
    public static final int POSITION_RIGHT_TO = 4;

    @IntDef({ALIGN_CENTER, ALIGN_LEFT, ALIGN_RIGHT})
    public @interface Align {}
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;

    @IntDef({GRAVITY_CENTER, GRAVITY_LEFT, GRAVITY_RIGHT})
    public @interface Gravity {}
    public static final int GRAVITY_CENTER = 0;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_RIGHT = 2;

    private Context mContext;
    private View mAnchorView;
    private ViewGroup mRootViewGroup;
    private String mMessage;
    private @Position int mPosition;
    private @Align int mAlign;
    private int mOffsetX;
    private int mOffsetY;
    private boolean mArrow;
    private int mBackgroundColor;
    private int mTextColor;
    private float mElevation;
    private @Gravity int mTextGravity;
    private Spannable mSpannableMessage;
    private int mTextSize;
    private Typeface mTypeface;

    public ToolTip(Builder builder){
        mContext = builder.mContext;
        mAnchorView = builder.mAnchorView;
        mRootViewGroup = builder.mRootViewGroup;
        mMessage = builder.mMessage;
        mPosition = builder.mPosition;
        mAlign = builder.mAlign;
        mOffsetX = builder.mOffsetX;
        mOffsetX = builder.mOffsetX;
        mOffsetY = builder.mOffsetY;
        mArrow = builder.mArrow;
        mBackgroundColor = builder.mBackgroundColor;
        mTextColor = builder.mTextColor;
        mElevation = builder.mElevation;
        mTextGravity = builder.mTextGravity;
        mSpannableMessage = builder.mSpannableMessage;
        mTextSize = builder.mTextSize;
        mTypeface = builder.mTypeface;
    }

    public Context getContext() {
        return mContext;
    }

    public View getAnchorView() {
        return mAnchorView;
    }

    public ViewGroup getRootView() {
        return mRootViewGroup;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getAlign() {
        return mAlign;
    }

    public int getOffsetX() {
        return mOffsetX;
    }

    public int getOffsetY() {
        return mOffsetY;
    }

    public boolean hideArrow() {
        return !mArrow;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public boolean positionedLeftTo(){
        return POSITION_LEFT_TO == mPosition;
    }

    public boolean positionedRightTo(){
        return POSITION_RIGHT_TO == mPosition;
    }

    public boolean positionedAbove(){
        return POSITION_ABOVE == mPosition;
    }

    public boolean positionedBelow(){
        return POSITION_BELOW == mPosition;
    }

    public boolean alignedCenter(){
        return ALIGN_CENTER == mAlign;
    }

    public boolean alignedLeft(){
        return ALIGN_LEFT == mAlign;
    }

    public boolean alignedRight(){
        return ALIGN_RIGHT == mAlign;
    }

    public void setPosition(@Position int position){
        mPosition = position;
    }

    public float getElevation() {
        return mElevation;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public int getTextGravity(){
        int gravity;
        switch (mTextGravity){
            case GRAVITY_CENTER:
                gravity = android.view.Gravity.CENTER;
                break;
            case GRAVITY_LEFT:
                gravity = android.view.Gravity.START;
                break;
            case GRAVITY_RIGHT:
                gravity = android.view.Gravity.END;
                break;
            default:
                gravity = android.view.Gravity.CENTER;
        }
        return gravity;
    }

    public Spannable getSpannableMessage() {
        return mSpannableMessage;
    }

    public Typeface getTypeface() {
        if (mTypeface == null) {
            mTypeface = Typeface.DEFAULT;
        }

        return mTypeface;
    }

    public static class Builder {
        private Context mContext;
        private View mAnchorView;
        private ViewGroup mRootViewGroup;
        private String mMessage;
        private @Position int mPosition;
        private @Align int mAlign;
        private int mOffsetX;
        private int mOffsetY;
        private boolean mArrow;
        private int mBackgroundColor;
        private int mTextColor;
        private float mElevation;
        private @Gravity int mTextGravity;
        private Spannable mSpannableMessage;
        private int mTextSize;
        private Typeface mTypeface;

        /**
         *
         * @param context context
         * @param anchorView the view which near it we want to put the tip
         * @param root a class extends ViewGroup which the created tip view will be added to
         * @param message message to show
         * @param position  put the tip above / below / left to / right to
         */
        public Builder(Context context, View anchorView, ViewGroup root, String message, @Position int position){
            mContext = context;
            mAnchorView = anchorView;
            mRootViewGroup = root;
            mMessage = message;
            mSpannableMessage = null;
            mPosition = position;
            mAlign = ALIGN_CENTER;
            mOffsetX = 0;
            mOffsetY = 0;
            mArrow = true;
            mBackgroundColor = context.getResources().getColor(R.color.colorBackground);
            mTextColor = context.getResources().getColor(R.color.colorText);
            mTextGravity = GRAVITY_LEFT;
            mTextSize = 14;
        }

        /**
         * @param context    context
         * @param anchorView the view which near it we want to put the tip
         * @param root       a class extends ViewGroup which the created tip view will be added to
         * @param message    spannable message to show
         * @param position   put the tip above / below / left to / right to
         */
        public Builder(Context context, View anchorView, ViewGroup root, Spannable message, @Position int position) {
            mContext = context;
            mAnchorView = anchorView;
            mRootViewGroup = root;
            mMessage = null;
            mSpannableMessage = message;
            mPosition = position;
            mAlign = ALIGN_CENTER;
            mOffsetX = 0;
            mOffsetY = 0;
            mArrow = true;
            mBackgroundColor = context.getResources().getColor(R.color.colorBackground);
            mTextColor = context.getResources().getColor(R.color.colorText);
            mTextGravity = GRAVITY_LEFT;
            mTextSize = 14;
        }

        public Builder setPosition(@Position int position){
            mPosition = position;
            return this;
        }

        public Builder setAlign(@Align int align){
            mAlign = align;
            return this;
        }

        /**
         * @param offset offset to move the tip on x axis after tip was positioned
         * @return offset
         */
        public Builder setOffsetX(int offset){
            mOffsetX = offset;
            return this;
        }

        /**
         * @param offset offset to move the tip on y axis after tip was positioned
         * @return offset
         */
        public Builder setOffsetY(int offset){
            mOffsetY = offset;
            return this;
        }

        public Builder withArrow(boolean value){
            mArrow = value;
            return this;
        }

        public Builder setBackgroundColor(int color){
            mBackgroundColor = color;
            return this;
        }

        public Builder setTextColor(int color){
            mTextColor = color;
            return this;
        }

        public Builder setElevation(float elevation){
            mElevation = elevation;
            return this;
        }

        public Builder setGravity(@Gravity int gravity){
            mTextGravity = gravity;
            return this;
        }

        public Builder setTextSize(int sizeInSp) {
            mTextSize = sizeInSp;
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        public ToolTip build(){
            return new ToolTip(this);
        }
    }
}
