package com.hhit.edu.library;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hhit.edu.partwork.R;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by long on 2016/4/15.
 * 分段标签栏
 * 扩展TabHost
 */
public class XFragmentTabHost extends FragmentTabHost {
/*
* public abstract class Context{上下文环境，场景
*   public static final in MODE_PRIVATE=0x0000;
*                          MODE_WORDLD_WRITEABLE=0x0002;
*                          MODE_APPEND=0x8000;
*                          MODE_MULTI_PROCESS=0x0004l}
* */
    private Context mContext;
    private List<View> mTabViews;//标签组件数组
    private List<TabItem> mTabItems;//标签元素数组
    // 字体激活颜色
    private int mTextActiveColor;
    private int mTextInactiveColor;
    // 字体激活大小
    private float mTextActiveSize;
    private float mTextInactiveSize;
    // 视图激活对顶部的偏移
    private int mViewActivePaddingTop;
    private int mViewInactivePaddingTop;
    // 波纹模式的前景颜色和后景颜色
    private int mFrontColor;
    private int mBehindColor;
    // TabHost模式，一个内部类
    private TabMode mTabMode;


    //构造方法1
    public XFragmentTabHost(Context context) {
        super(context);
        _init(context);
    }
    //构造方法2
    public XFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init(context);
    }

    /*
    * 初始化上下文场景*/
    private void _init(Context context) {
        mTabViews = new ArrayList<>();//创建对象数组
        mTabItems = new ArrayList<>();
        mContext = context;
        /*R.color.colorActive*/
        //获取字体的颜色ID对象和前景后景对象ID
        mTextActiveColor = ContextCompat.getColor(mContext, R.color.colorActive);
        mTextInactiveColor = ContextCompat.getColor(mContext, R.color.colorInactive);
        mFrontColor = ContextCompat.getColor(mContext, R.color.colorFront);
        mBehindColor = ContextCompat.getColor(mContext, R.color.colorBehind);
        //设置字体大小
        mTextActiveSize = getResources().getDimension(R.dimen.tab_text_size_active);
        mTextInactiveSize = getResources().getDimension(R.dimen.tab_text_size_inactive);
        //视图激活偏移量
        mViewActivePaddingTop = (int) getResources().getDimension(R.dimen.tab_padding_top_active);
        mViewInactivePaddingTop = (int) getResources().getDimension(R.dimen.tab_padding_top_inactive);
        //调用固定的方法
        mTabMode = TabMode.MoveToTop;
    }

    /**
     * 设置当前标签
     * 覆写父类接口，并在这里做些动画特效
     * @param index 当前选中的Tab项
     */
    @Override
    public void setCurrentTab(int index) {
        System.out.println("设置当前页面--index"+index);
        // 获取之前选中的index

        int lastIndex = getCurrentTab();//默认值是-1
        System.out.println("lastIndex="+lastIndex);
        super.setCurrentTab(index);
        // 选中不同的Tab项才做切换处理，一开始是不一样的所以默认切换一次视图
        if (lastIndex != index) {//点击的tab和上一个不同，也就是切换点击了就执行下面的东西
            _switchTab(lastIndex, index);//将上一次的,和当前的参数，-1,0
        }
    }

    /*消除分割线*/
    @Override
    protected void onAttachedToWindow() {
        System.out.println("消除分割线");
        super.onAttachedToWindow();
        // 部分机型TabHost带有分割线，统一将分割线设为透明
        getTabWidget().setDividerDrawable(android.R.color.transparent);
    }

    /**
     * 添加TabItem
     * @param item  TabItem
     * @param fragClass fragment类名
     * @param bundle 传给fragment的参数
     */
    public void addTabItem(TabItem item, Class<?> fragClass, Bundle bundle) {
        System.out.println("22222222222222222222");
        //下面的参数一次是对应标签的字符、图片资源ID、TableFragment类名、捆绑数据mTableItem[i]
        mTabItems.add(item);//数组添加个tabitems对象？什么用
        View view = _getIndicator(item);
        mTabViews.add(view);//数组添加一个view对象？什么用
        //addTab添加选项卡，newTabSpec创建选项卡，当前对象添加选项卡，setIndicator(view)设置按钮显示
        this.addTab(newTabSpec(item.getTitle()).setIndicator(view), fragClass, bundle);
        //fragClass=="TabFragment"就是这个类
    }

    /**
     * 形成一个view选项卡返回
     * @param item TabItem
     * @return
     */
    private View _getIndicator(TabItem item) {//参数是标题字符和四个标签的xml
        //获得单独的图片和文本的组合的组件
        View view = LayoutInflater.from(mContext).inflate(R.layout.tabhost_indicator, null);
        //获取tabhost里边的图片和文本对象
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        TextView title = (TextView) view.findViewById(R.id.tab_title);
        //设置图片组员
        imageView.setImageResource(item.getImageRes());//图片布局.xml
        //设置字体
        title.setText(item.getTitle());//这里获得的就是传进来的各个标题的内容
        title.setTextColor(mTextInactiveColor);//设置字体颜色为蓝色
        return view;
    }

    /**
     * 切换Tab
     * @param lastIndex 上一个选中索引
     * @param nextIndex 下一个选中索引
     */
    private void _switchTab(int lastIndex, int nextIndex) {
        //上一次的点击，和本次的点击参数0,2
        System.out.println("lastIndex=="+lastIndex);
        System.out.println("nextIndex=="+nextIndex);
        System.out.println("mTabViews里边的数组个数4="+mTabViews.size());
        for (int i = 0; i < mTabViews.size(); i++) {//0,1,2,3
            if (i == lastIndex) {//将上一个标签设置为未选中状态（图形样式）
                _switchView(i, false);
            } else if (i == nextIndex) {//将当前标签设置为选中状态（图形样式）
                _switchView(i, true);
            }
        }
    }

    /**
     * 切换视图
     * @param index 索引
     * @param isActivated 是否激活
     */
    private void _switchView(int index, boolean isActivated) {//0,true
        switch (mTabMode) {
            case MoveToTop://这里指使用了这个case，
                _doMoveToTop(index, isActivated);//0,true
                break;
            case ClipDrawable:
                _doClipDrawable(index, isActivated);
                break;
            case Ripple:
                _doRipple(index, isActivated);
                break;
        }
    }

    /**
     * 背景展开处理
     * @param index 索引
     * @param isActivated 是否激活
     */
    private void _doClipDrawable(int index, boolean isActivated) {
        View view = mTabViews.get(index);
        View tabView = view.findViewById(R.id.tab_layout);
        if (isActivated) {
            TabAnimHelper.clipDrawable(tabView, mTabItems.get(index).getDrawable(), true);
        } else {
            TabAnimHelper.clipDrawable(tabView, mTabItems.get(index).getDrawable(), false);
        }
    }

    /**
     * 波纹处理，这个用不到
     * @param index 索引
     * @param isActivated 是否激活
     */
    private void _doRipple(int index, boolean isActivated) {
        View view = mTabViews.get(index);
        View tabView = view.findViewById(R.id.tab_layout);
        TextView title = (TextView) view.findViewById(R.id.tab_title);
        if (index == 0) {
            TabAnimHelper.rippleDrawable(tabView, mFrontColor, mBehindColor, RippleDrawable.MODE_LEFT, isActivated);
        } else if (index == (mTabViews.size() - 1)){
            TabAnimHelper.rippleDrawable(tabView, mFrontColor, mBehindColor, RippleDrawable.MODE_RIGHT, isActivated);
        } else {
            TabAnimHelper.rippleDrawable(tabView, mFrontColor, mBehindColor, RippleDrawable.MODE_MIDDLE, isActivated);
        }
        if (isActivated) {
            title.setTextColor(mTextActiveColor);
        } else {
            title.setTextColor(mTextInactiveColor);
        }
    }

    /**
     * 上移动作处理
     * @param index 索引
     * @param isActivated 是否激活
     */
    private void _doMoveToTop(int index, boolean isActivated) {
        View view = mTabViews.get(index);//选中第一个组件
        TextView title = (TextView) view.findViewById(R.id.tab_title);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        if (isActivated) {//如果是激活状态（选中状态），设置组件内部样式内容
            TabAnimHelper.changeTextColor(title, mTextInactiveColor, mTextActiveColor);
            TabAnimHelper.changeTextSize(title, mTextInactiveSize, mTextActiveSize);
            TabAnimHelper.changeTopPadding(view, mViewInactivePaddingTop, mViewActivePaddingTop);
            TabAnimHelper.changeImageSize(icon, 1.0f, 1.1f);
        } else {//如果不是激活状态（不是选中状态）
            TabAnimHelper.changeTextColor(title, mTextActiveColor, mTextInactiveColor);
            TabAnimHelper.changeTextSize(title, mTextActiveSize, mTextInactiveSize);
            TabAnimHelper.changeTopPadding(view, mViewActivePaddingTop, mViewInactivePaddingTop);
            TabAnimHelper.changeImageSize(icon, 1.1f, 1.0f);
        }
    }


    /**
     * 属性设置
     * @return
     */
    public int getTextActiveColor() {
        return mTextActiveColor;
    }

    public void setTextActiveColor(int textActiveColor) {
        mTextActiveColor = textActiveColor;
    }

    public int getTextInactiveColor() {
        return mTextInactiveColor;
    }

    public void setTextInactiveColor(int textInactiveColor) {
        mTextInactiveColor = textInactiveColor;
    }

    public int getFrontColor() {
        return mFrontColor;
    }

    public void setFrontColor(int frontColor) {
        mFrontColor = frontColor;
    }

    public int getBehindColor() {
        return mBehindColor;
    }

    public void setBehindColor(int behindColor) {
        mBehindColor = behindColor;
    }

    public TabMode getTabMode() {
        return mTabMode;
    }

    public void setTabMode(TabMode tabMode) {
        mTabMode = tabMode;//这里的mTabMode==MoveToTop
    }

    /**
     * Tab的模式
     * MoveToTop：向上偏移
     * Ripple：波纹
     * ClipDrawable：逐步展示背景
     */
    public enum TabMode {

        MoveToTop(1),
        Ripple(2),
        ClipDrawable(3);

        private int tabMode;

        TabMode(int tabMode) {
            System.out.println("看看这里的tabMode是一个什么数字-----"+tabMode);
            this.tabMode = tabMode;//这个就是简单的设置一个数
        }

        public int getTabMode() {
            return tabMode;
        }
    }
}
