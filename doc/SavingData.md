<span id="savingData"/>
# 保存数据
**SavingDataDemoActivity.java**  
Andrio有3种方式保存数据:[键值对集合](#keyValueSet),[文件系统](#fileSystem), [数据库系统](#databaseSystem)

<h2 id="keyValueSet">键值对集合</h2>

**SavingKeyValueSetsActivity.java**  
如果有相对较小的键值对数据需要保存，并且不需要跟其他的App程序共享，那么可以使用SharedPreferences APIs, 一个SharedPreferences对象指向一个包含键值对数据的问题，并且提供简单的读写方法。 

### 获取一个SharedPreferences句柄

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

### 写数据

首先通过getPreferences()获取一个SharedPreferences句柄，通过SharedPreferences.edit()获取一个SharedPreferences.Editor实例，然后通过SharedPreferences.Editor的putInt和putString方法传入要存储的数据，最后通过commit()方法保存的引用文件中。

```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPref.edit();
editor.putInt(getString(R.string.saved_high_score), newHighScore);
editor.commit();
```

### 读数据

首先通过getPreferences()获取一个SharedPreferences句柄，然后通过SharedPreferences的getInt和getString方法获取需要取到的值，也可以传递一个默认值如果指定的共享文件中不存在所指定的值。 

```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
```

<h2 id="fileSystem">文件系统</h2>
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

### 内部存储设备存储文件
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
### 外部存储设备存储文件
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

<h2 id="databaseSystem">数据库系统</h2>
**SavingDatabaseActivity.java**  
数据库系统适合存储有结构化的数据。

### 定义数据结构
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
### 准备创建和删除表SQL语句

```
private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedReaderContract.FeedReaderEntry.TABLE_NAME + "(" +
        FeedReaderContract.FeedReaderEntry._ID + " INTEGER PRIMARY KEY," +
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " TEXT,"  +
        FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE + " TEXT)";
private static final String SQL_DELETE_ENTRIES =
        "DELETE TABLE IF EXISTS" + FeedReaderContract.FeedReaderEntry.TABLE_NAME;
```
### 初始化数据表
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
### 写数据
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

### 查询数据
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
### 删除数据
通过查询条件和指定查询的输入参数，使用SQLiteDatabase的delete()方法来删除指定的数据。

```
SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " LIKE ?";
String[] selectionArgs = {localRecordName};
int count = db.delete(FeedReaderContract.FeedReaderEntry.TABLE_NAME, selection, selectionArgs);
```

### 更新数据
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
### 关闭数据库

```
@Override
protected void onDestroy() {
    mDbHelper.close();
    super.onDestroy();
}
```