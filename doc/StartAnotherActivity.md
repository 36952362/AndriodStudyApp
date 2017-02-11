#启动另一个Activity
**LaunchAnotherActivity.java**
## 构建一个Intent
Intent 是指在相互独立的组件（如两个 Activity）之间提供运行时绑定功能的对象。Intent 表示一个应用“执行某项操作的 Intent”。 您可以将 Intent 用于各种任务.

Intent 构造函数采用两个参数:  
Context 是第一个参数（之所以使用 this ，是因为 Activity 类是 Context 的子类）
应用组件的 Class，系统应将 Intent（在本例中，为应启动的 Activity）传递至该类。
`Intent intent = new Intent(this, DisplayMessageActivity.class);`
## 传递值到另一个Activity
putExtra() 方法将需要的值添加到 Intent。Intent 能够以名为 extra 的键值对形式携带数据类型。下一个 Activity 将使用该键来检索文本值。为 Intent extra 定义键时最好使用应用的软件包名称作为前缀。这可以确保在您的应用与其他应用交互过程中这些键始终保持唯一。
`intent.putExtra(EXTRA_EDIT_MESSAGE, message);`
## 启动另一个Activity
startActivity() 方法将启动 Intent 指定的 DisplayMessageActivity 实例。
`startActivity(intent);`