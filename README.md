# AndroidStudyApp
这个App是根据google官网的培训材料一步一步地学习Andrio开发(https://developer.android.com/training/index.html)

目录:

* [启动另一个Activity](#startAnotherActivity)
* [运行时检查系统版本](./doc/SystemVersionCheck.md#checkSystemVerionInruntime)
* [使用Fragment创建动态UI](#createDynamicUI)
* [保存数据](#savingData)  
  1. [键值对集合](#keyValueSet)  
  2. [文件系统](#fileSystem)  
  3. [数据库系统](#databaseSystem)  
* [问题解决](#problemResolve)  
  1. [英文字母全大写](#textAllCaps)  
  2. [ShareActionProvider Is Null](#shareActionProviderIsNull)
  

<h2 id="startAnotherActivity">启动另一个Activity</h2>
**LaunchAnotherActivity.java**
### 构建一个Intent
****
Intent 是指在相互独立的组件（如两个 Activity）之间提供运行时绑定功能的对象。Intent 表示一个应用“执行某项操作的 Intent”。 您可以将 Intent 用于各种任务.

Intent 构造函数采用两个参数:  
Context 是第一个参数（之所以使用 this ，是因为 Activity 类是 Context 的子类）
应用组件的 Class，系统应将 Intent（在本例中，为应启动的 Activity）传递至该类。
`Intent intent = new Intent(this, DisplayMessageActivity.class);`
### 传递值到另一个Activity
****
putExtra() 方法将需要的值添加到 Intent。Intent 能够以名为 extra 的键值对形式携带数据类型。下一个 Activity 将使用该键来检索文本值。为 Intent extra 定义键时最好使用应用的软件包名称作为前缀。这可以确保在您的应用与其他应用交互过程中这些键始终保持唯一。
`intent.putExtra(EXTRA_EDIT_MESSAGE, message);`
### 启动另一个Activity
****
startActivity() 方法将启动 Intent 指定的 DisplayMessageActivity 实例。
`startActivity(intent);`

<h2 id="createDynamicUI">使用Fragment创建动态UI</h2>
**FragmentDemoActivity.java**

由于市场上有很多不同尺寸的移动终端和横屏竖屏的使用习惯，为了提高用户使用体验，需要根据不同尺寸展现不同的UI。  
每个Fragment可以有自己的布局，也可以和其他的Fragment放在同一个Activity的布局中去适应不同的屏幕尺寸，也可以动态的调整来提高用户体验。
### 主Activity继承自FragmentActivity
`public class FragmentDemoActivity extends FragmentActivity`

### 创建一个Fragment
****
创建一个自己的Fragment并继承自Fragment,并重写onCreateView方法去绑定自己的Fragment布局。

```
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // If activity recreated (such as from screen rotate), restore
    // the previous article selection set by onSaveInstanceState().
    // This is primarily necessary when in the two-pane layout.
    if (savedInstanceState != null) {
        mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
    }
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_article, container, false);
}
```
### 主Activity定义不同的布局文件
****
为不同尺寸屏幕提供不同的布局

```
new_articles.xml(layout)
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/fragment_container">
</FrameLayout>
```
或者

```
new_articles.xml(layout_large)
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal" android:layout_width="fill_parent"
    android:layout_height="fill_parent">

    <fragment android:name="com.jupiter.androidstudyapp.gettingstarted.fragmentdemo.HeadlinesFragment"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="match_parent"
        android:id="@+id/headlines_fragment">
    </fragment>

    <fragment android:name="com.jupiter.androidstudyapp.gettingstarted.fragmentdemo.ArticleFragment"
        android:layout_width="0dp"
        android:layout_weight="2"
        android:layout_height="match_parent"
        android:id="@+id/article_fragment">
    </fragment>

</LinearLayout>
```
### 运行时判断加载的布局文件
****
通过findViewById()方法查找布局文件的id来判断加载的是哪种布局文件。在本例中，如果加载的是 `new_articles.xml (layout-large)`布局文件，则说明用户使用的是大屏幕的移动设备，根据布局文件新闻标题和具体内容的Fragment在布局文件中已经定义好了，直接显示就行。否则如果加载的是`new_articles.xml(layout)`布局文件,则需要动态加载新闻标题Fragment。

```
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.news_articles);
   if(findViewById(R.id.fragment_container) != null){
	   if(savedInstanceState != null) {
	   		return;
	  	}
	   HeadlinesFragment firstFragment = new HeadlinesFragment();
	   firstFragment.setArguments(getIntent().getExtras());
	   getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,firstFragment).commit();
	}
}
```
### 动态更新布局中的Fragment
****
要动态添加或者移除一个Fragment, 在你的Activity中通过getSupportFragmentManager()获取一个FragmentManager, 然后使用FragmentManager创建一个FragmentTransaction去添加(add)、移除(remove)、替换(replace)或执行(commit)一个事务。
`getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,firstFragment).commit();`
为了允许用户通过导航栏返回到前面一个界面，必须在执行(commit)一个事务之前调用addToBackStack()，这个方法可以提供唯一名字在表示一个事务，这个名字不是必须的，除非你使用FragmentManager.BackStackEntry API进行高级操作。

### Fragment之间消息的传递
****
可以借助于Fragment关联的Activity进行消息的中转，Fragment之间不能进行直接的消息传递。

1. Fragment中定义一个接口
	
	```
	OnHeadlineSelectedListener mCallback;
	// Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }
	```
2. Fragment通过onAttach()方法获取关联的Activity

	```
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	```

3. Fragment根据这个Activity对象实例调用接口方法和关联的Activiy交流。

	```
	@Override
    public void onListItemClick(ListView l, View v, int position, long id){
        mCallback.onArticleSelected(position);
        getListView().setItemChecked(position,true);
    }
	```
4. 关联的Activity实现这个接口

	```
	public class FragmentDemoActivity extends FragmentActivity implements HeadlinesFragment.OnHeadlinesSelectedListener {
	......
	@Override
	public void onArticleSelected(int position) {
	    ArticleFragment articleFragment = (ArticleFragment)getSupportFragmentManager().findFragmentById(R.id.article_fragment);
	    if(articleFragment != null){
	        // If article frag is available, we're in two-pane layout...
	        // Call a method in the ArticleFragment to update its content
	        articleFragment.updateArticleView(position);
	    } else{
	        // Otherwise, we're in the one-pane layout and must swap frags...
	        articleFragment = new ArticleFragment();
	        Bundle args = new Bundle();
	        args.putInt(ArticleFragment.ARG_POSITION, position);
	        articleFragment.setArguments(args);
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	        transaction.replace(R.id.fragment_container, articleFragment);
	        transaction.addToBackStack(null);
	        transaction.commit();
	    }
	}
	}
	```

5. Activity发送消息到关联的Fragment
Activity可以通过findFragmentById()方法根据Fragment对应布局文件的Id获取到指定的Fragment, 然后调用相应的Fragment公共方法进行交流。
`ArticleFragment articleFragment = (ArticleFragment)getSupportFragmentManager().findFragmentById(R.id.article_fragment);`

<h2 id="savingData">保存数据</h2>
**SavingDataDemoActivity.java**  
Andrio有3种方式保存数据:

<h3 id="keyValueSet">键值对集合</h3>
****
**SavingKeyValueSetsActivity.java**  
如果有相对较小的键值对数据需要保存，并且不需要跟其他的App程序共享，那么可以使用SharedPreferences APIs, 一个SharedPreferences对象指向一个包含键值对数据的问题，并且提供简单的读写方法。 

#### 获取一个SharedPreferences句柄

可以通过getSharedPreferences() 和 getPreferences()来创建或获取一个已经存在的引用文件。  
**getSharedPreferences()**可以通过指定一个name获取多个共享引用文件。  
**getPreferences()**可以获取一个共享引用文件。

```
SharedPreferences sharedPref = getSharedPreferences(
getString(R.string.preference_file_key), Context.MODE_PRIVATE);
```
或者

```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
```

#### 写数据

首先通过getPreferences()获取一个SharedPreferences句柄，通过SharedPreferences.edit()获取一个SharedPreferences.Editor实例，然后通过SharedPreferences.Editor的putInt和putString方法传入要存储的数据，最后通过commit()方法保存的引用文件中。

```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPref.edit();
editor.putInt(getString(R.string.saved_high_score), newHighScore);
editor.commit();
```

#### 读数据

首先通过getPreferences()获取一个SharedPreferences句柄，然后通过SharedPreferences的getInt和getString方法获取需要取到的值，也可以传递一个默认值如果指定的共享文件中不存在所指定的值。 

```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
```

<h3 id="fileSystem">文件系统</h3>
****
**SavingFileActivity.java**  
文件系统适合存储顺序访问的大量数据，比如图片，网络传输的数据块等。所有Android设备包含2个文件存储区域：内部和外部存储。下面是内部和外部存储性质的总结:  
**内部存储**:  
1. 始终有效。  
2. 存储的文件只有该App可以访问。  
3. 当该App被卸载时，存储的所有文件将被一同删除。

**外部存储**:  
1. 不是始终有效。用户有可能从系统中移除USB存储设备。  
2. 在用户授权的情况下，其他的App时可以访问存储的数据。  
3. 当该App被卸载时，只有通过getExternalFilesDir()获取到的目录下的文件将被一同删除；通过getExternalStoragePublicDirectory()获取到的目录下的文件将不受影响。

#### 内部存储设备存储文件
可以通过getFilesDir()获取内部存储设备存储目录和getCacheDir()获取内部存储设备缓存目录。

```
File file = new File(context.getFilesDir(), filename);
```
或者

```
File file = File.createTempFile(fileName, null, context.getCacheDir());
```
或者通过openFileOutput()获取到一个FileOutputStream去存储文件。

```
String filename = "myfile";
String string = "Hello world!";
FileOutputStream outputStream;

try {
  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
  outputStream.write(string.getBytes());
  outputStream.close();
} catch (Exception e) {
  e.printStackTrace();
}
```
#### 外部存储设备存储文件
**获取外部存储权限**  
若要对外部存储设备进行读写，必须在App安装时获取外部存储设备的读写权限。  
在manifest file文件中加入:  
**写权限**  

```
<manifest ...>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    ...
</manifest>
```

**读权限**

```
<manifest ...>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    ...
</manifest>
```
**NOTE**:
如果App已经获取到写权限，那么同时拥有了读权限。

**获取外部存储设备状态**
在将文件存储到外部存储设备之前，需要通过getExternalStorageState()获取外部存储设备状态。
读和写状态

```
public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
        return true;
    }
    return false;
}
```
只有读状态

```
public boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        return true;
    }
    return false;
}
```
通过getExternalStoragePublicDirectory()并传入合适的文件类型获取公用的外部存储设备目录，比如DIRECTORY_MUSIC or DIRECTORY_PICTURES。

```
public File getAlbumStorageDir(String albumName) {
    // Get the directory for the user's public pictures directory.
    File file = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
```
通过getExternalFilesDir()并传入合适的文件类型获取私有的外部存储设备目录。注意：该目录下的文件会一同App卸载后一同删除。

```
public File getAlbumStorageDir(Context context, String albumName) {
    // Get the directory for the app's private pictures directory.
    File file = new File(context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
```

**获取存储空间大小**  
通过getFreeSpace() 或者 getTotalSpace()获取到存储空间可用和总存储空间大小。

**删除文件**
可以通过文件句柄或上下文结合文件名删除文件。

```
myFile.delete();
```
或者

```
myContext.deleteFile(fileName);
```

<h3 id="databaseSystem">数据库系统</h3>
****
**SavingDatabaseActivity.java**  
数据库系统适合存储有结构化的数据。

#### 定义数据结构
定义需要存储的数据组织结构的数据库表名和表中字段项。该数据结构类继承于BaseColumns，就可以引用父类的_ID字段。

```
public final class FeedReaderContract {

    private void FeedReaderContract(){}

    public static class  FeedReaderEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
```
#### 准备创建和删除表SQL语句

```
private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedReaderContract.FeedReaderEntry.TABLE_NAME + "(" +
        FeedReaderContract.FeedReaderEntry._ID + " INTEGER PRIMARY KEY," +
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " TEXT,"  +
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE + " TEXT)";
private static final String SQL_DELETE_ENTRIES =
        "DELETE TABLE IF EXISTS" + FeedReaderContract.FeedReaderEntry.TABLE_NAME;
```
#### 初始化数据表
使用一个子类继承于SQLiteOpenHelper，并重写onCreate(), onUpgrade() and onOpen()方法初始化数据表，通过getWritableDatabase()和getReadableDatabase()获取可读写的数据库引用。

```
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
```
#### 写数据
构造ContentValues数据，并通过SQLiteDatabase的insert()写入数据。

```
// Gets the data repository in write mode
SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME, recordName);
values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE, recordAge);

long newRowId = db.insert(FeedReaderContract.FeedReaderEntry.TABLE_NAME, null, values);
```
**NOTE:**  
insert()方法的第二个参数告诉Android框架如何处理ContentValues中没有指定表中的字段值时如何处理。如果第二个参数指定了表的某个字段名并且ContentValues中没有指定相应的值，那么Android框架会插入一个记录，并把第二个参数指定字段名的值设置为null; 如果第二个参数值为null,并且ContentValues中没有没有指定所有的字段值数据，那么Android框架不会插入一条新记录。

#### 查询数据
通过查询条件和指定查询的输入参数，使用SQLiteDatabase的query()方法来获取需要的数据。  
**指定查询的字段名**

```
String[] projection = {
        FeedReaderContract.FeedReaderEntry._ID,
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME,
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE
```

**定义查询语句和输入参数**

```
String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " = ?";
String[] selectionArgs = {"*"};
```

**定义数据排序方法**

`String sortOrder = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " ASC";`

**执行查询**

```
Cursor cursor = db.query(
    FeedEntry.TABLE_NAME,                     // The table to query
    projection,                               // The columns to return
    selection,                                // The columns for the WHERE clause
    selectionArgs,                            // The values for the WHERE clause
    null,                                     // don't group the rows
    null,                                     // don't filter by row groups
    sortOrder                                 // The sort order
    );
```
**遍历输出结果**

```
StringBuilder stringBuilder = new StringBuilder();
int i = 0;
while(cursor.moveToNext()){
    i++;
    stringBuilder.append(i + ":\n");
    stringBuilder.append("Name:" + cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME)));
    stringBuilder.append(", Age:" + cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE)));
    stringBuilder.append("\n");
}
cursor.close();
```
#### 删除数据
通过查询条件和指定查询的输入参数，使用SQLiteDatabase的delete()方法来删除指定的数据。

```
SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " LIKE ?";
String[] selectionArgs = {localRecordName};
int count = db.delete(FeedReaderContract.FeedReaderEntry.TABLE_NAME, selection, selectionArgs);
```

#### 更新数据
通过查询条件和指定查询的输入参数，使用SQLiteDatabase的update()方法来更新指定的数据。

```
SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME, localRecordName);
values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE, localRecordAge);

String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " LIKE ?";
String[] selectionArgs = {localRecordName};

int count = db.update(FeedReaderContract.FeedReaderEntry.TABLE_NAME, values, selection, selectionArgs);
```
#### 关闭数据库

```
@Override
protected void onDestroy() {
    mDbHelper.close();
    super.onDestroy();
}
```

<h2 id="problemResolve">问题解决</h2>
<h3 id="textAllCaps">英文字母全大写</h3>
****
<h4>问题:</h4>   
在Andrio Studio中Button上面的英文字母不管输入的是否是大小写，显示的时候默认都变成了大写  
<h4>解决方案:</h4>   
在Button的属性里加上andriod:textAllCaps="fase"

<h3 id="shareActionProviderIsNull"> ShareActionProvider Is Null</h3>
****

#### 问题 
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

#### 解决方案
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