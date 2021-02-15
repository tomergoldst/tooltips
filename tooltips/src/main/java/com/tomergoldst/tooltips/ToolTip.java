
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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

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

    @NonNull private final Context mContext;
    @NonNull private final View mAnchorView;
    @NonNull private final ViewGroup mRootViewGroup;
    @NonNull private final CharSequence mMessage;
    private @Position int mPosition;
    private final @Align int mAlign;
    private final int mOffsetX;
    private final int mOffsetY;
    private final boolean mArrow;
    private final int mBackgroundColor;
    private final float mElevation;
    private final @Gravity int mTextGravity;
    private final @StyleRes int mTextAppearanceStyle;
    @Nullable private final Typeface mTypeface;

    public ToolTip(Builder builder){
        mContext = builder.mContext;
        mAnchorView = builder.mAnchorView;
        mRootViewGroup = builder.mRootViewGroup;
        mMessage = builder.mMessage;
        mPosition = builder.mPosition;
        mAlign = builder.mAlign;
        mOffsetX = builder.mOffsetX;
        mOffsetY = builder.mOffsetY;
        mArrow = builder.mArrow;
        mBackgroundColor = builder.mBackgroundColor;
        mElevation = builder.mElevation;
        mTextGravity = builder.mTextGravity;
        mTextAppearanceStyle = builder.mTextAppearanceStyle;
        mTypeface = builder.mTypeface;
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    @NonNull
    public View getAnchorView() {
        return mAnchorView;
    }

    @NonNull
    public ViewGroup getRootView() {
        return mRootViewGroup;
    }

    @NonNull
    public CharSequence getMessage() {
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

    @StyleRes
    public int getTextAppearanceStyle() {
        return mTextAppearanceStyle;
    }

    @Nullable
    public Typeface getTypeface() {
        return mTypeface;
    }

    @NonNull
    public int getTextGravity(){
        int gravity;
        switch (mTextGravity){
            case GRAVITY_LEFT:
                gravity = android.view.Gravity.START;
                break;
            case GRAVITY_RIGHT:
                gravity = android.view.Gravity.END;
                break;
            case GRAVITY_CENTER:
            default:
                gravity = android.view.Gravity.CENTER;
        }
        return gravity;
    }

    public static class Builder {
        private final @NonNull Context mContext;
        private final @NonNull View mAnchorView;
        private final @NonNull ViewGroup mRootViewGroup;
        private final @NonNull CharSequence mMessage;
        private @Position int mPosition;
        private @Align int mAlign;
        private int mOffsetX;
        private int mOffsetY;
        private boolean mArrow;
        private int mBackgroundColor;
        private float mElevation;
        private @Gravity int mTextGravity;
        private @StyleRes int mTextAppearanceStyle;
        private @Nullable Typeface mTypeface;


        /**
         * Creates the tooltip builder with message and required parameters to show tooltip.
         *
         * @param context context
         * @param anchorView the view which near it we want to put the tip
         * @param root a class extends ViewGroup which the created tip view will be added to
         * @param message message to show. Note: This allows normal text and spannable text with spanned styles.
         * @param position  put the tip above / below / left to / right to anchor view.
         */
        public Builder(@NonNull Context context,
                       @NonNull View anchorView,
                       @NonNull ViewGroup root,
                       @NonNull CharSequence message,
                       @Position int position) {
            mContext = context;
            mAnchorView = anchorView;
            mRootViewGroup = root;
            mMessage = message;
            mPosition = position;
            mAlign = ALIGN_CENTER;
            mOffsetX = 0;
            mOffsetY = 0;
            mArrow = true;
            mBackgroundColor = context.getResources().getColor(R.color.colorBackground);
            mTextGravity = GRAVITY_LEFT;
            mTextAppearanceStyle = R.style.TooltipDefaultStyle;
        }

        @NonNull
        public Builder setPosition(@Position int position){
            mPosition = position;
            return this;
        }

        @NonNull
        public Builder setAlign(@Align int align){
            mAlign = align;
            return this;
        }

        /**
         * @param offset offset to move the tip on x axis after tip was positioned
         * @return offset
         */
        @NonNull
        public Builder setOffsetX(int offset){
            mOffsetX = offset;
            return this;
        }

        /**
         * @param offset offset to move the tip on y axis after tip was positioned
         * @return offset
         */
        @NonNull
        public Builder setOffsetY(int offset){
            mOffsetY = offset;
            return this;
        }

        @NonNull
        public Builder withArrow(boolean value){
            mArrow = value;
            return this;
        }

        @NonNull
        public Builder setBackgroundColor(int color){
            mBackgroundColor = color;
            return this;
        }

        @NonNull
        public Builder setElevation(float elevation){
            mElevation = elevation;
            return this;
        }

        @NonNull
        public Builder setGravity(@Gravity int gravity){
            mTextGravity = gravity;
            return this;
        }

        @NonNull
        public Builder setTextAppearance(@StyleRes int textAppearance) {
            mTextAppearanceStyle = textAppearance;
            return this;
        }

        @NonNull
        public Builder setTypeface(@NonNull Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        @NonNull
        public ToolTip build(){
            return new ToolTip(this);
        }

    }
}
