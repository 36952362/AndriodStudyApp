# 系统权限控制
为了保护系统的完整性和用户的隐私。Android只运作每个App运行在有限的沙盒中。若App想要访问沙盒之外的资源，必须显示请求权限。对于不同类型的权限请求类型，系统可能会自动授权，也可能提示用户去授权。  
* [权限声明](#declaringPermission)  
* [运行时获取权限](#requestPermissionAtRunTime)

<h2 id="declaringPermission">权限声明</h2>
在App的程序清单中使用<uses-permission>声明该App需要的所有权限。  

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.example.snazzyapp">

    <uses-permission android:name="android.permission.SEND_SMS"/>
    
    <application ...>
        ...
    </application>

</manifest>
```

<h2 id="requestPermissionAtRunTime">运行时获取权限</h2>
从Android 6.0 (API level 23)开始，用户可以在App运行时授权，而不是在App安装时。这个可以最大程度地允许App能够安装并正常运行，只有在需要授权的时候在请求系统或提示用户进行授权。
### 检查授权
通过ContextCompat.checkSelfPermission()可以检查App是否已获得指定的权限。 返回值PackageManager.PERMISSION_GRANTED表明App已经获取到授权，PackageManager.PERMISSION_DENIED表示没有得到授权，需要请求系统或提示用户进行授权。  

```
// Assume thisActivity is the current activity
int permissionCheck = ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.WRITE_CALENDAR);
```

### 解释请求授权的原因
Android系统提供了一个工具方法ActivityCompat.shouldShowRequestPermissionRationale()来判断是否需要给用户一个提示，只有当曾经已经请求过并且已经被用户拒绝过，这个方法才返回true。当用户曾经拒绝过并且选中了"不再提示"选项，那么这个方法始终返回false。
### 请求授权
通过ActivityCompat.requestPermissions()请求系统或用户授权。

```
// Here, thisActivity is the current activity
if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_CONTACTS) !=
          PackageManager.PERMISSION_GRANTED) {
    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Manifest.permission.READ_CONTACTS)){
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
    } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }
}
```
### 权限请求结果
通过重载onRequestPermissionsResult()方法获取权限请求的结果。

```
@Override
public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }
}
```