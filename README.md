# EditTextIcon

An extension to the android EditText which shows an ImageView on its left side. This view is commonly used in typical android forms.

# How to Use?

For the complete sample you can check out ```SampleActivity.java``` in the project.

 - Copy ```EditTextIcon.java``` file to your android project.
 - Specify the view in your view's xml.
```xml
<com.edittexticon.android.edittexticon.EditTextIcon
        android:layout_width="match_parent"
        android:layout_height="40dp"
        app:iconSrc="@drawable/ic_close"
        app:hint="name"
        app:spacing="12dp"
        app:underlineColor="#000000"
        app:hideUnderline="true" />
 ```
 
 ### Supported Attributes
 
 - ```iconSrc``` - to set the drawable for the ImageView to be shown on the left side of EditText
 - ```underlineColor``` - to change the EditText underline color.
 - ```hideUnderline``` - to toggle the visibility of EditText underline
 
