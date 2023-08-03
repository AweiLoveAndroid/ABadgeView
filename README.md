ABadgeView
============

Android Badge for any View.
Also you can add badge in Menu, for example: `Toolbar`, `BottomNavigationView`, `TabLayout`,`DrawerLayout`,etc...

### Gradle Init

* 1.in your project root `build.gradle` add maven repositories, like this:

```groovy
maven { url 'https://jitpack.io' }
``` 

* 2.in your module `build.gradle` add dependencies, like this:

```groovy
dependencies {
    implementation 'com.lzw.badgeview:lib:0.1.0'
    annotationProcessor 'com.lzw.badgeview:compiler:0.1.0'
}
```

If the libs that you  implementation contains `com.google.android.material`, you should exclude it.like this:

```groovy
implementation ('androidx.navigation:navigation-ui:2.3.5') {
    exclude group: 'com.google.android.material', module: 'material'
}
```


### Init ABadgeView

Use the bytecode of those classes that require add badges functionality as a parameter in the ABadge annotation. (The following code block provides an example, and the corresponding lines can be deleted if not needed.)

```Java
@ABadge({
    View.class, // equeals: com.lzw.badgeview.ABadgeView, if you don't use it, just delete this line.
    ImageView.class, // equeals: com.lzw.badgeview.ABadgeImageVieww, if you don't use it, just delete this line.
    TextView.class, // equeals: com.lzw.badgeview.ABadgeFloatingTextView, if you don't use it, just delete this line.
    RadioButton.class, // equeals: com.lzw.badgeview.ABadgeRadioButton, if you don't use it, just delete this line.
    LinearLayout.class, // equeals: com.lzw.badgeview.ABadgeLinearLayout, if you don't use it, just delete this line.
    FrameLayout.class, // equeals: com.lzw.badgeview.ABadgeFrameLayout, if you don't use it, just delete this line.
    RelativeLayout.class, // equeals: com.lzw.badgeview.ABadgeRelativeLayout, if you don't use it, just delete this line.
    FloatingActionButton.class, // equeals: com.lzw.badgeview.ABadgeFloatingActionButton, if you don't use it, just delete this line.
    ...
})
public class ABadgeInit {
}
```
3. In Android Studio , click [Build] => [Rebuild Project]

4. Now you can use 「com.lzw.badgeview.ABadgeXXX」in your project.

### Api Method

* void showCirclePointBadge();

* void showTextBadge(String badgeText);

* void hiddenBadge();

* void showDrawableBadge(Bitmap bitmap);

* void setDragDismissDelegage(BGADragDismissDelegate delegate);

* boolean isShowBadge();

* boolean isDraggable();

* boolean isDragging();

### Badge Attrs

Attrs Name | Description | Default value
:----------- | :----------- | :-----------
badge_bgColor         | Badge background color.        | Color.RED
badge_textColor         | Badge text color.         | Color.WHITE
badge_textSize         | Badge text text size.        | 10sp
badge_verticalMargin         | The vertical margin between the badge background and anchorView.        | 4dp
badge_horizontalMargin         | The horizontal margin between the badge background and anchorView.        | 4dp
badge_padding         | The padding between tadge text edge and  the badge background edge.       | 4dp
badge_gravity         | Badge alignment in anchor view.        | BGABadgeImageView和BGABadgeRadioButton是右上方，其他控件是右边垂直居中
badge_draggable         | Do you want to enable drag and drop to delete Badge?        | false
badge_isResumeTravel         | Do you want to restore the trajectory when dragging the Badge beyond the trajectory range and placing it back into the trajectory range again?        | false
badge_borderWidth         | Badge border width        | 0dp
badge_borderColor         | Badge border color         | Color.WHITE
badge_dragExtra         | Trigger the extended touch distance to start dragging Badge event.       | 4dp



### Use badge in `BottomNavigationView`

Three ways:

> 1.Use BadgeDrawable

```java
// 使用官方的BadgeDrawable：
BadgeDrawable badgeDrawable = bottomNavigationView.addBadgeCustom(R.id.navigation_home);
badgeDrawable.setBadgeGravity(BadgeDrawable.TOP_END);
badgeDrawable.setNumber(100);
```

> 2.Use Xml layout

```java
// 使用xml布局加载
bottomNavigationView.addBadgeFromXmlLayout(BottomNavActivity.this, 1, R.layout.layout_custom_badge_xml);
```

> 3.Use ABadgeView

```java
// 使用自定义ABadgeView
bottomNavigationView.setIsNeedShowBadge(true);
bottomNavigationView.bindTargetItemIndex(2)
//  .setBadgeText("10")//设置显示数字
    .setBadgeNumber(10)//设置显示数字
    .setBadgeGravity(Gravity.END | Gravity.TOP)//设置显示位置
    .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
    .setBadgeGravityOffset(5, 0, true)
    .setBadgePadding(8, true)
    .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_red, null))//设置自定义背景，红色填充，绿色边框
    .setBadgeIsDraggable(true)
    .setOnBadgeDragStateChangedListener(new OnBadgeDragStateChangedListener() {
        @Override
        public void onBadgeDragStateChanged(int dragState, String stateTag, IABadge badge, View targetView) {
            Log.d(TAG, "onBadgeDragStateChanged: dragState=" + dragState + ", stateTag=" + stateTag);
            if (dragState == ABadgeDragState.STATE_SUCCEED) {
                bottomNavigationView.setIsNeedShowBadge(false);
            }
        }
    });
```

### Use badge in `TabLayout`

Sample code:

```java
    //set the badge
    BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
    badgeDrawable.setVisible(true);
    badgeDrawable.setBadgeText("5");

    BadgeDrawable badgeDrawable2 = tabLayout.getTabAt(1).getOrCreateBadge();
    badgeDrawable2.setVisible(true);
    badgeDrawable2.setBadgeText("20");

    BadgeDrawable badgeDrawable3 = tabLayout.getTabAt(2).getOrCreateBadge();
    badgeDrawable3.setVisible(true);
    badgeDrawable3.setBadgeText("测试");

//  you can use setCustomView ,add your custom badge view in TabLayout items.
//        tabLayout.getTabAt(0).setCustomView(R.layout.layout_custom_tab_xml);
//        tabLayout.getTabAt(1).setCustomView(R.layout.layout_custom_tab_xml);
//        tabLayout.getTabAt(2).setCustomView(R.layout.layout_custom_tab_xml);
```

### Use badge in `Toolbar`

More information see: [ABadgeMenuItemHelper.java](lib/src/main/java/com/lzw/badgeview/menu/ABadgeMenuItemHelper.java)

Sample code:

```java
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        //you can add some logic
        MenuItem menuItem1 = menu.findItem(R.id.toolbar_menu_item1);
        MenuItem menuItem2 = menu.findItem(R.id.toolbar_menu_item2);
        MenuItem menuItem3 = menu.findItem(R.id.toolbar_menu_item3);

        Drawable menuItemDrawable1 = menuItem1.getIcon();
        Drawable menuItemDrawable2 = menuItem2.getIcon();
        Drawable menuItemDrawable3 = menuItem3.getIcon();

        // 隐藏当前MenuItem
//        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).hideMenuItem(menuItemHome);

        // 如果传入负数，或者0 则不会badge
        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem1,
                getResources().getDrawable(R.mipmap.tab_discover_normal),
                getResources().getDrawable(R.mipmap.tab_discover_checked),
                0,
                ABadgeStyleDefault.RED_ROUND,
                null);

        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem2,
                getResources().getDrawable(R.mipmap.tab_me_normal),
                getResources().getDrawable(R.mipmap.tab_me_checked),
                100,
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.toolbar_menu_item2) {
                            Toast.makeText(ToolbarBadgeActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem3,
                menuItemDrawable3,
                "测试",
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.toolbar_menu_item3) {
                            Toast.makeText(ToolbarBadgeActivity.this, "自定义监听 ==> 点击了按钮3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return true;


    }
```

### Use badge in `DrawerLayout`

Sample code:

```java
    MenuItem menuItem1 = navigationView.getMenu().getItem(0);
    MenuItem menuItem2 = navigationView.getMenu().getItem(1);
    MenuItem menuItem3 = navigationView.getMenu().getItem(2);

    Drawable menuItemDrawable1 = menuItem1.getIcon();
    Drawable menuItemDrawable2 = menuItem2.getIcon();
    Drawable menuItemDrawable3 = menuItem3.getIcon();

    // 隐藏当前MenuItem
//        new ABadgeMenuItemHelper(DrawerActivity.this).hideMenuItem(menuItemHome);

    // 如果传入负数，或者0 则不会badge
    new ABadgeMenuItemHelper(DrawerActivity.this).show(
        menuItem1,
        getResources().getDrawable(R.mipmap.tab_discover_normal),
        getResources().getDrawable(R.mipmap.tab_discover_checked),
        0,
        ABadgeStyleDefault.RED_ROUND,
        new ABadgeMenuItemClickListener() {
            @Override
            public void onOptionsItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.drawer_menu_item1) {
                    Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                }
            }
        });

    new ABadgeMenuItemHelper(DrawerActivity.this).show(
        menuItem2,
        getResources().getDrawable(R.mipmap.tab_me_normal),
        getResources().getDrawable(R.mipmap.tab_me_checked),
        100,
        ABadgeStyleDefault.RED_ROUND_RECT,
        new ABadgeMenuItemClickListener() {
            @Override
            public void onOptionsItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.drawer_menu_item2) {
                    Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                }
            }
        });

    new ABadgeMenuItemHelper(DrawerActivity.this).show(
        menuItem3,
        getResources().getDrawable(R.drawable.ic_home_black_24dp),
        getResources().getDrawable(R.drawable.ic_home_black_24dp),
        "测试",
        ABadgeStyleDefault.RED_ROUND_RECT,
        new ABadgeMenuItemClickListener() {
            @Override
            public void onOptionsItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.drawer_menu_item3) {
                    Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮3", Toast.LENGTH_SHORT).show();
                }
            }
        });
```

----

## Thanks

ABadgeView: reference: 'q.rorbin:badgeview:1.1.3'

**ABadge annotation and compiler** reference: [BGABadgeView-Android](https://github.com/bingoogolapple/BGABadgeView-Android)

**Toolbar show badge** reference:  [Android-ActionItemBadge](https://github.com/mikepenz/Android-ActionItemBadge)

----

## License

    Copyright 2023 AWeiLoveAndroid
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.