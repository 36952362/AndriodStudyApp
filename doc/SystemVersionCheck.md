<h2 id="checkSystemVerionInruntime">运行时检查系统版本</h2>
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