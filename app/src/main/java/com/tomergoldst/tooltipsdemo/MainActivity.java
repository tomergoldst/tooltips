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

package com.tomergoldst.tooltipsdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;


public class MainActivity extends AppCompatActivity implements
        ToolTipsManager.TipListener,
        View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TIP_TEXT = "Tool Tip";
    public static final String TIP_TEXT_SMALL = "Small Tool Tip";
    public static final String TIP_TEXT_LARGE = "Large Tool Tip";

    ToolTipsManager mToolTipsManager;
    RelativeLayout mRootLayout;
    RelativeLayout mParentLayout;
    TextInputEditText mEditText;
    TextView mTextView;

    Button mAboveBtn;
    Button mBelowBtn;
    Button mLeftToBtn;
    Button mRightToBtn;

    RadioButton mAlignRight;
    RadioButton mAlignLeft;
    RadioButton mAlignCenter;

    @ToolTip.Align int mAlign = ToolTip.ALIGN_CENTER;
    Typeface mCustomFont = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        mParentLayout = (RelativeLayout) findViewById(R.id.parent_layout);
        mTextView = (TextView) findViewById(R.id.text_view);

        mToolTipsManager = new ToolTipsManager(this);

        mAboveBtn = (Button) findViewById(R.id.button_above);
        mBelowBtn = (Button) findViewById(R.id.button_below);
        mLeftToBtn = (Button) findViewById(R.id.button_left_to);
        mRightToBtn = (Button) findViewById(R.id.button_right_to);

        mAboveBtn.setOnClickListener(this);
        mBelowBtn.setOnClickListener(this);
        mLeftToBtn.setOnClickListener(this);
        mRightToBtn.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        mAlignCenter = (RadioButton) findViewById(R.id.button_align_center);
        mAlignLeft = (RadioButton) findViewById(R.id.button_align_left);
        mAlignRight = (RadioButton) findViewById(R.id.button_align_right);

        mAlignCenter.setOnClickListener(this);
        mAlignLeft.setOnClickListener(this);
        mAlignRight.setOnClickListener(this);

        mAlignCenter.setChecked(true);

        mEditText = (TextInputEditText) findViewById(R.id.text_input_edit_text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.use_custom_font_menu_item:
                mCustomFont = Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf");
                Toast toast = Toast.makeText(this, "Custom font set. Re-try demo.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ToolTip.Builder builder = new ToolTip.Builder(this, mTextView, mRootLayout, TIP_TEXT, ToolTip.POSITION_ABOVE);
        builder.setAlign(mAlign);
        builder.setTextAppearance(R.style.TooltipTextAppearance);
        mToolTipsManager.show(builder.build());
    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {
        Log.d(TAG, "tip near anchor view " + anchorViewId + " dismissed");

        if (anchorViewId == R.id.text_view) {
            // Do something when a tip near view with id "R.id.text_view" has been dismissed
        }
    }

    @Override
    public void onClick(View view) {
        String text = TextUtils.isEmpty(mEditText.getText()) ? TIP_TEXT : mEditText.getText().toString();
        ToolTip.Builder builder;

        switch (view.getId()){
            case R.id.button_above:
                mToolTipsManager.findAndDismiss(mTextView);
                builder = new ToolTip.Builder(this, mTextView, mRootLayout, text, ToolTip.POSITION_ABOVE);
                builder.setAlign(mAlign);
                builder.setTypeface(mCustomFont);
                mToolTipsManager.show(builder.build());
                break;
            case R.id.button_below:
                mToolTipsManager.findAndDismiss(mTextView);
                builder = new ToolTip.Builder(this, mTextView, mRootLayout, text, ToolTip.POSITION_BELOW);
                builder.setAlign(mAlign);
                builder.setTextAppearance(R.style.TooltipTextAppearance);
                builder.setTypeface(mCustomFont);
                builder.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                mToolTipsManager.show(builder.build());
                break;
            case R.id.button_left_to:
                mToolTipsManager.findAndDismiss(mTextView);
                builder = new ToolTip.Builder(this, mTextView, mRootLayout, TIP_TEXT.equals(text) ? TIP_TEXT_SMALL : text, ToolTip.POSITION_LEFT_TO);
                builder.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                builder.setGravity(ToolTip.GRAVITY_CENTER);
                builder.setTypeface(mCustomFont);
                builder.setTextAppearance(R.style.TooltipTextAppearance_Small_Black);
                mToolTipsManager.show(builder.build());
                break;
            case R.id.button_right_to:
                mToolTipsManager.findAndDismiss(mTextView);
                builder = new ToolTip.Builder(this, mTextView, mRootLayout, TIP_TEXT.equals(text) ? TIP_TEXT_LARGE : text, ToolTip.POSITION_RIGHT_TO);
                builder.setBackgroundColor(getResources().getColor(R.color.colorDarkRed));
                builder.setTextAppearance(R.style.TooltipTextAppearance_Large);
                builder.setTypeface(mCustomFont);
                mToolTipsManager.show(builder.build());
                break;
            case R.id.button_align_center:
                mAlign = ToolTip.ALIGN_CENTER;
                break;
            case R.id.button_align_left:
                mAlign = ToolTip.ALIGN_LEFT;
                break;
            case R.id.button_align_right:
                mAlign = ToolTip.ALIGN_RIGHT;
                break;
            case R.id.text_view:
                mToolTipsManager.dismissAll();
                break;
        }
    }
}
