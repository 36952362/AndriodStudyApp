# AndroidStudyApp
这个App是根据google官网的培训材料一步一步地学习Andrio开发(https://developer.android.com/training/index.html)
## 启动另一个Activity
**LaunchAnotherActivity**
### 构建一个Intent
Intent 是指在相互独立的组件（如两个 Activity）之间提供运行时绑定功能的对象。Intent 表示一个应用“执行某项操作的 Intent”。 您可以将 Intent 用于各种任务.

Intent 构造函数采用两个参数:  
Context 是第一个参数（之所以使用 this ，是因为 Activity 类是 Context 的子类）
应用组件的 Class，系统应将 Intent（在本例中，为应启动的 Activity）传递至该类。
`Intent intent = new Intent(this, DisplayMessageActivity.class);`
### 传递值到另一个Activity
putExtra() 方法将需要的值添加到 Intent。Intent 能够以名为 extra 的键值对形式携带数据类型。下一个 Activity 将使用该键来检索文本值。为 Intent extra 定义键时最好使用应用的软件包名称作为前缀。这可以确保在您的应用与其他应用交互过程中这些键始终保持唯一。
`intent.putExtra(EXTRA_EDIT_MESSAGE, message);`
### 启动另一个Activity
startActivity() 方法将启动 Intent 指定的 DisplayMessageActivity 实例。
`startActivity(intent);`

## 运行时检查系统版本
Android使用Build类中的常量为每个版本提供一个唯一的值

```
private void setUpActionBar() {
    // Make sure we're running on Honeycomb or higher to use ActionBar APIs
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
```
## 使用Fragment创建动态UI
**FragmentDemoActivity**

由于市场上有很多不同尺寸的移动终端和横屏竖屏的使用习惯，为了提高用户使用体验，需要根据不同尺寸展现不同的UI。  
每个Fragment可以有自己的布局，也可以和其他的Fragment放在同一个Activity的布局中去适应不同的屏幕尺寸，也可以动态的调整来提高用户体验。
### 主Activity继承自FragmentActivity
`public class FragmentDemoActivity extends FragmentActivity`

### 创建一个Fragment
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
要动态添加或者移除一个Fragment, 在你的Activity中通过getSupportFragmentManager()获取一个FragmentManager, 然后使用FragmentManager创建一个FragmentTransaction去添加(add)、移除(remove)、替换(replace)或执行(commit)一个事务。
`getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,firstFragment).commit();`
为了允许用户通过导航栏返回到前面一个界面，必须在执行(commit)一个事务之前调用addToBackStack()，这个方法可以提供唯一名字在表示一个事务，这个名字不是必须的，除非你使用FragmentManager.BackStackEntry API进行高级操作。

### Fragment之间消息的传递
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