shape.xml
**
 <?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#30ccf3" />

    <corners android:radius="15dip" />

    <padding
        android:right="10dp"
        android:left="10dp"
        android:top="10dp"
        android:bottom="10dp" />

</shape>
====================================================================
 shape_circle.xml
**
  <?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#30ccf3" />

    <corners android:radius="360dip" />

    <padding
        android:right="10dp"
        android:left="10dp"
        android:top="10dp"
        android:bottom="10dp" />
</shape>
====================================================================
shape_text.xml
**
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >
    <item>
        <shape>
            <stroke
                android:width="1dp"
                android:color="#B6B4B4" />
            <corners android:radius="15dip" />
            <solid android:color="#ffffff" >
            </solid>
            <padding android:right="12dp" />
            <size
                android:width="300dp"
                android:height="10dp" />
        </shape>
    </item>
    <item>
        <bitmap
            android:gravity="end"
            android:src="@drawable/icon_android_cms_dropdown_arrow_down"
            android:antialias="true" />
    </item>

</layer-list>
