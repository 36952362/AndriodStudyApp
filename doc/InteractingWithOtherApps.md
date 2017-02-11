# 与其他App交互
**InteractingWithOtherAppsActivity.java**  
Android一个很重要的功能就是允许用户从一个App跳转到另外一个App,使得某个App专注于本身的功能，如果需要其他的功能并且另外一个App已经实现，Android框架则可以允许一个App使用另外一个App的功能，并能获取另外一个App执行指定功能的结果。  
使用Intent.startActivity()可以启动另外一个Activity,进而让用户从一个Activity(界面)跳转到另外一个(本身App或者其他App)的Activity(界面).  
Intent可以**显式**指定一个Activity(通常是在本身App的各个Activity之间),也可以**隐式**指定一个可处理指定功能的Activities(通常在不同App之间）。  

* [发送数据到其他App](#sendUsersToAnotherApp)
* [从其他App获取返回值](#getResultFromAnotherApp)
* [允许其他App调用](#allowOtherAppInvoke)

<h2 id="sendUsersToAnotherApp">发送数据到其他App</h2>
**SendingUserToAnotherAppActivity.java**
### 创建隐式Intent
****
隐式Intent不指定Activity的具体类名字，而是通过指定动作类型来执行。  
**拨打电话**  

```
Uri number = Uri.parse("tel:5551234");
Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
```
**查看地图** 
 
```
// Map point based on address
Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
// Or map point based on latitude/longitude
// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
```
**查看网页**

```
Uri webpage = Uri.parse("http://www.android.com");
Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
```

**发送带有附件的邮件**

```
Intent emailIntent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
// You can also attach multiple items by passing an ArrayList of Uris
```
**创建一个日程安排**

```
Intent calendarIntent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
Calendar beginTime = Calendar.getInstance().set(2012, 0, 19, 7, 30);
Calendar endTime = Calendar.getInstance().set(2012, 0, 19, 10, 30);
calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
calendarIntent.putExtra(Events.TITLE, "Ninja class");
calendarIntent.putExtra(Events.EVENT_LOCATION, "Secret dojo");
```
###验证有效性
在发送隐式Intent之前，要确保有可用的其他App可以接收该App的意图并能正确处理。如果没有合适的App处理Intent,有可能导致App崩溃。通过PackageManager.queryIntentActivities()可以获取到有效Apps列表。

```
PackageManager packageManager = getPackageManager();
List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
boolean isIntentSafe = activities.size() > 0;
```
### 启动Activity

`startActivity(intent);`
### 显示一个App选择项
Android框架中若有多个合适的App能够处理隐式Intent, 则会显示一个可用App选择列表，同时有一个选择开关允许用户设置一个默认的App为下次处理的默认App,不再显示可用App选择列表。但是有时用户要求在已经设置了一个默认App后还要继续显示可用App选择列表的情况。可以通过Intent.createChooser()来解决此类问题。

```
Intent intent = new Intent(Intent.ACTION_SEND);
...

// Always use string resources for UI text.
// This says something like "Share this photo with"
String title = getResources().getString(R.string.chooser_title);
// Create intent to show chooser
Intent chooser = Intent.createChooser(intent, title);

// Verify the intent will resolve to at least one activity
if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(chooser);
}
```

<h2 id="getResultFromAnotherApp">从其他App获取返回值</h2>
**GettingResultFromOtherAppActivity.java**

### 启动Activity
要想从其他App获取返回值，需要使用startActivityForResult()启动Activity

`startActivityForResult(intent);`

### 获取返回值
要想从其他App获取返回值，还有重载onActivityResult()来获取返回值。

```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Check which request we're responding to
    if (requestCode == PICK_CONTACT_REQUEST) {
        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
            // The user picked a contact.
            // The Intent's data Uri identifies which contact was selected.
			  Uri contactUri = data.getData();
            // Do something with the contact here (bigger example below)
        }
    }
}
```

<h2 id="allowOtherAppInvoke">允许其他App调用</h2>
**AllowOtherAppStartActivity.java**
### 添加Inter过滤器
允许其他App调用该App,需要在该App的程序清单中说明该App具有何种能力，并在该App安装的时候告诉Android系统，这样在其他App在隐式调用Intent的时候，Android系统能够找到该App。一个Intent的过滤器包含3部分的内容:  
**Action**:指定Intent可以执行何种动作，比如ACTION_SEND或者ACTION_VIEW。  
**Data**: 描述Intent可以处理的数据类型，比如text/plain或者image/jpeg。  
**Category**:近一步描述Intent的特征，通常情况下很少使用。默认值为CATEGORY_DEFAULT。

```
<activity android:name="ShareActivity">
    <intent-filter>
        <action android:name="android.intent.action.SEND"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <data android:mimeType="text/plain"/>
        <data android:mimeType="image/*"/>
    </intent-filter>
</activity>
```
**NOTE**: 可以为某个Intent指定多个intent-filter。
### 处理Intent
在onCreate()或者onStart()中通过getIntent()可以获取到其他App发送的Intent。

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);

    // Get the intent that started this activity
    Intent intent = getIntent();
    Uri data = intent.getData();

    // Figure out what to do based on the intent type
    if (intent.getType().indexOf("image/") != -1) {
        // Handle intents with image data ...
    } else if (intent.getType().equals("text/plain")) {
        // Handle intents with text ...
    }
}
```
### 返回结果
通过setResult()返回结果到调用的App。通过finish()关闭或者销毁该Activity。

```
// Create intent to deliver some kind of result data
Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
setResult(Activity.RESULT_OK, result);
finish();
```
