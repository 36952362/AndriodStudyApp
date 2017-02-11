<span id="problemResolve"/>
# 问题解决
* [英文字母全大写](#textAllCaps)
* [ShareActionProvider Is Null](#shareActionProviderIsNull)

<h2 id="textAllCaps">英文字母全大写</h2>
### 问题:   
在Andrio Studio中Button上面的英文字母不管输入的是否是大小写，显示的时候默认都变成了大写  
<h4>解决方案:</h4>   
在Button的属性里加上andriod:textAllCaps="fase"

<h2 id="shareActionProviderIsNull">ShareActionProvider Is Null</h2>
### 问题 
根据goolge官方的培训材料[Adding an Easy Share Action](https://developer.android.com/training/sharing/shareaction.html)写的测试代码:  
**菜单配置文件:** 

	```
	<?xml version="1.0" encoding="utf-8"?>
	<menu xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:id="@+id/menu_item_share_text"
	        android:showAsAction="ifRoom"
	        android:title="Share Text"
	        android:icon="@mipmap/ic_launcher"
	        android:actionProviderClass="android.widget.ShareActionProvider"/>
	
	    <item android:id="@+id/menu_item_share_picture"
	        android:showAsAction="ifRoom"
	        android:title="Share Picture"
	        android:actionProviderClass="android.widget.ShareActionProvider"/>
	
	</menu>
	```
**获取ShareActionProvide代码:**

	```
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate menu resource file.
	    getMenuInflater().inflate(R.menu.share_menu, menu);
	
	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	
	    // Return true to display menu
	    return true;
	}
	```
在运行时mShareActionProvider = (ShareActionProvider) item.getActionProvider();获取的mShareActionProvider总是为NULL，导致没有获取到期望的效果。  

### 解决方案
**菜单配置文件**  
菜单配置文件中的actionProviderClass换成android.support.v7.widget.ShareActionProvider

	```
	<?xml version="1.0" encoding="utf-8"?>
	<menu xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:id="@+id/menu_item_share_text"
	        android:showAsAction="ifRoom"
	        android:title="Share Text"
	        android:icon="@mipmap/ic_launcher"
	        android:actionProviderClass="android.support.v7.widget.ShareActionProvider"/>
	
	    <item android:id="@+id/menu_item_share_picture"
	        android:showAsAction="ifRoom"
	        android:title="Share Picture"
	        android:actionProviderClass="android.support.v7.widget.ShareActionProvider"/>
	
	</menu>
	```
**获取ShareActionProvide代码:**  
创建一个ShareActionProvider实例并通过MenuItemCompat.setActionProvider设置到系统中

	```
	mShareActionProvider = new ShareActionProvider();
	mShareActionProvider.setShareIntent(createShareIntent())
	MenuItemCompat.setActionProvider(item, mShareActionProvider);
	```