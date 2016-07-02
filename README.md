# Tooltips
Simple to use library for android, Enabling to add a tooltip near any view with ease

<img src="https://cloud.githubusercontent.com/assets/19874536/16538907/40f359ec-403a-11e6-8e91-388b12236b2e.png" width="150 height="150"/>
<img src="https://cloud.githubusercontent.com/assets/19874536/16538909/41088d44-403a-11e6-9ddd-353b01932047.png" width="150 height="150"/>
<img src="https://cloud.githubusercontent.com/assets/19874536/16538906/40eee45c-403a-11e6-8dec-87b7d7b19976.png" width="150 height="150"/>
<img src="https://cloud.githubusercontent.com/assets/19874536/16538908/4107dd7c-403a-11e6-8867-16a751da2f53.png" width="150 height="150"/>

##Instructions

Add a dependency to your app build.gradle
```
dependencies {
    compile 'com.tomergoldst.android:tooltips:1.0.0'    
}
```

Create a ToolTipsManager object
```
  ToolTipsManager mToolTipsManager;
  mToolTipsManager = new ToolTipsManager();
```
  
Use the ToolTip Builder to construct your tip
```
  ToolTip.Builder builder = new ToolTip.Builder(this, mTextView, mRootLayout, "Tip message", ToolTip.POSITION_ABOVE);
```
'mTextView' here is the view which near it the tip will be shown and 'mRootLayout' is the layout where the tip view will be added to.
The root layout must be of RelativeLayout, FrameLayout or similar. LinearLayout won't work but you can always wrap your LinearLayout
with another layout. Prefer to pass in a layout which is higher in the xml tree as this will give the
tip view more visible space.
 
OPTIONAL: Customize your tip with background color, text color, alignment, text gravity and more. 
```
  builder.setAlign(ToolTip.ALIGN_LEFT)
  builder.setBackgroundColor(getResources().getColor(R.color.colorOrange));
  builder.setTextColor(getResources().getColor(R.color.colorBlack));
  builder.setGravity(ToolTip.GRAVITY_RIGHT);
```

Use ToolTipManger to show the tip

IMPORTANT: This must be called after the layout has been drawn
You can override the 'onWindowFocusChanged()' of an Activity and show there, Start a delayed runnable from onStart() , React to user action or any other method that works for you
```
  mToolTipsManager.show(builder.build());
```

Each tip is dismissable by clicking on it, if you want to dismiss a tip from code there are a few options, The most simple way is to do the following
```
mToolTipsManager.findAndDismiss(mTextView);
```
Where 'mTextView' is the same view we asked to position a tip near it

If you want to react when tip has been dismissed, Implement ToolTipsManager.TipListener interface and use appropriate ToolTipsManager constructor
```
public class MainActivity extends AppCompatActivity implements ToolTipsManager.TipListener
.
.
@Override
protected void onCreate(Bundle savedInstanceState) {
    mToolTipsManager = new ToolTipsManager(this);
}
.
.
@Override
public void onTipDismissed(View view, boolean byUser) {
    Log.d(TAG, "Dismissed view " + view.hashCode() + " ByUser = " + byUser);
}
```

### License
```
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
```  


