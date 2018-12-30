# JavaFinalProject
- [系统操作说明](#系统操作说明)  
- [系统概述](#系统概述)  
  - [Main类](#Main类)  
  - [Controller类](#Controller类)
  - [Replay类](#Replay类)
  - [Creature抽象类](#Creature抽象类)
  - [Formation抽象类](#Formation抽象类)
  - [BattleGround类](#BattleGround类)
- [代码实现细节](#代码实现细节)  
  - [多线程协同](#多线程协同)
  - [图形化内容](#图形化内容)
  - [移动和攻击细节](#移动和攻击细节)
- [单元测试](#单元测试)  
  - [Replay读取测试](#Replay读取测试)
  - [Formation切换与BattleGround部署阵型测试](#Formation切换与BattleGround部署阵型测试)
## 系统操作说明
+ D键：随机设置双方阵型（双方各八种阵型）（需要战斗结束后）
  + 葫芦娃由于人数限制，阵型不一定符合原型。老爷爷有时也参与阵型，未参与时将位于战场左下角。
  + 妖怪无人数限制，将铺满原阵型。蛇精固定位于战场右上角。
  + 战场为15x15的方形，葫芦娃阵营固定位于左侧，妖怪阵营固定位于右侧。
+ L键：读取战斗回放（固定为.log文件）（需要战斗结束后）
+ S键：保存战斗回放（固定为.log文件）（需要战斗结束后，读取或设置阵型前）
+ SPACE键：开始战斗（需要读取或设置阵型后）
+ 右上方有按钮，点击会弹出按键提示对话框。
## 系统概述
+ 界面为一个16x15的GridPane网格布局，右上角添加了一个帮助Button
+ 共五个大类，分别为Main类、Controller类、Replay类、BattleGround类、Creature类和Formation类。
### Main类
  + 继承`Application`类
  + 实现`void start(Stage primaryStage)`函数
    + 读取ui.fxml获得界面布局内容
    + 设置窗口大小为800x600，且不可更改窗口大小
  + 于main函数中`launch(args)`
### Controller类
  + 扩展initializable接口，初始化变量和布局。
  + 关联GridPane的onKeyRealeased函数，对键盘按键进行响应。
  + 关联Button的On Action函数，对不同情况下的帮助对话框进行更新。
  + 每次重设阵型或读取战斗回放时重新初始化所有变量。
  + 启动一个匿名内部类线程，定时更新界面。
### Replay类
  + 包含一个内部类Data用以保存一个回合的信息。
  + 一个`ArrayList<Data> data`用以保存一次战斗的所有信息，以及两个int数字保存双方阵型。
  + 给定File进行读取或保存的函数`void saveReplay(File file)`和`void loadReplay(File file)`
  + 数个更新或返回战斗信息的函数。
### Creature抽象类
  + 继承`Thread`类用于实现多线程
  + 一个`int id`用于在阵型和战场上标识自己的身份。
    + 0为老爷爷（`GrandPa`子类）
    + 1-7为七个葫芦娃（`CalabashBrother`子类）
    + 8为蛇精（`Snake`子类）
    + 9为蝎子精（`Scorpion`子类）
    + 10往后为普通小兵（`Creep`子类）
  +一个`int camp`用于区分阵营
    + 0为葫芦娃阵营
    + 1为妖怪阵营
  + 大量战斗和UI显示相关的属性（如攻击力，生命值，图片，死亡图片等）
  + 实现run函数
  + 在run中被调用的用于判定移动和攻击的函数
  + 返回该Creature的相关信息的函数
### Formation抽象类
  + 包含一个内部类Name用于保存阵法信息。
  + 一个int数组用于存储具体阵型内容。
  + 一个抽象函数`abstract void changeFormation(Name name)`用于更改阵型。
  + 数个返回阵型信息的函数。
  + 两个子类MonsterFormation和CalabashFormation，实现了`changeFormation`函数用于两个阵营同一阵型的不同实现。
### BattleGround类
  + 一个int数组用于存储战场内容。
  + 一个`void deploy(Formation formation, int camp)`函数用于将实例化的Formation放置在战场上。
  + 检查是否有一方获胜的`boolean winning()`函数。
  + 获得当前战场上总人数的`int combatNumber()`函数。
## 代码实现细节
+ 主要说明多线程协同方式、图形化内容、战斗中移动和攻击细节。
### 多线程协同
  + 按下SPACE时，程序调用两类线程的`start()`函数。
    + Creature类用于查看战场，执行移动和攻击。
    + `Controller::startUpdate()`函数中启动一个匿名内部类，每有生物移动就使用`Platform.runlater`进行界面更新（下称其为界面更新线程）。
  + 两类线程的run函数都`synchronized (ob)`，并while循环执行，通过wait和notifyAll函数释放和获得锁。
    + ob为Creature类下定义的`public static final Object ob = "aa"`
    + Creature线程循环条件为（该Creature仍然存活 && 未有任何一方获胜）
    + 界面更新线程循环条件为（未有任何一方获胜）
  + 在Creature类中设置一个信号量`public static int move = 0`
    + Creature线程获得synchronized锁后，检查move是否为0，  
      是则执行移动和攻击，且`move++`，并调用`ob.notifyAll()`，目的是唤醒界面更新线程。  
      否则调用`ob.wait()`，等待界面更新线程更新界面。
    + 界面更新线程获得synchronized锁后，检查move是否为1，  
      是则进行界面更新，且`move--`，并调用`ob.notifyAll()`，目的是唤醒Creature线程继续战斗。  
      否则调用`ob.wait()`，等待Creature线程进行移动。
  + 至此，此两类线程将按照：Creature->界面更新->Creature->界面更新->...的顺序执行，直至决出胜负。
### 图形化内容
  + GridPane由15x16个格子组成。背景图片PS过使得左侧15x15个草地格子与15x15个网格相符，  
    右上角Button实际用来接收键盘响应。
  + 每个Creature都是一个Label，调用`setGraphics`函数让图片在Label中显示。
  + 每个Creature上方的血条为ProgressBar，长度使用`setProgress`函数计算当前生命值与最大生命值的比值得出。
  + Creature移动时，先从GridPane上移除其Label和ProgressBar，再将其Label和ProgressBar加入新的网格。
  + Creature死亡后会将其Label加载新的死亡图片。（实际为原图旋转180°并且变为黑白所得）  
    并从BattleGround类的数组中移除，从而使得尸体不占用格子（因为两个Label可以叠加在同一个网格内）
  + 攻击时飞出去的箭矢也是Label，实现过程如下：
    1. 先将Label添加至攻击者的网格中。
    2. 计算攻击者和被攻击者之间的方位角，将Label适当旋转。
    3. 利用TranslateTransition将Label移动至目的地。
    4. 动画播放完后将Label从GridPane中移除。
### 移动和攻击细节
+ 默认行动顺序为：先移动再攻击。
+ 通常双方阵营会一边向对方整体移动（不破坏阵型），一边攻击。  
  直至进入交战距离，之后开始随机移动并攻击。
#### 移动
  + 若移动目的地已被占用，则本次不移动。
  + 移动方向（engaging变量在攻击时修改）
    + 若尚未交战，则持续向敌方阵营移动。
    + 若已经交战，则索敌并决定移动权重。
  + 移动权重
    + 优先向最近的敌人移动。
    + 较小概率向相反方向移动。
#### 攻击
  1. 在攻击距离内寻找一个敌人
  2. 以一定概率决定是否攻击，攻击后结束，不攻击则继续寻找。
## 单元测试
### Replay读取测试
  + 通过Replay读取一个指定文件。
  + 检查其中内容是否符合预期。
### Formation切换与BattleGround部署阵型测试
  + 切换共计64种阵型（8x8），并部署在战场上。
  + 统计战场上总人数是否符合预期。
  + 依次检查是否每个Creature是否已被部署。
