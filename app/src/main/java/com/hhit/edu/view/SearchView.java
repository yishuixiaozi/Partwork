package com.hhit.edu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hhit.edu.partwork.R;

/**
 * Created by Carson_Ho on 17/8/10.
 */

public class SearchView extends LinearLayout {

    /**
     * 初始化成员变量
     */
    private Context context;

    // 搜索框组件
    private EditText et_search; // 搜索按键
    private TextView tv_clear;  // 删除搜索记录按键
    private LinearLayout search_block; // 搜索框布局
    private ImageView searchBack; // 返回按钮按键

    // ListView列表 & 适配器
    private SearchListView listView;
    private BaseAdapter adapter;

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    // 回调接口
    private  ICallBack mCallBack;// 搜索按键回调接口
    private  bCallBack bCallBack; // 返回按键回调接口

    // 自定义属性设置，这就是那些app：；；；；；
    // 1. 搜索字体属性设置：大小、颜色 & 默认提示
    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    // 2. 搜索框设置：高度 & 颜色
    private int searchBlockHeight;
    private int searchBlockColor;

    /**
     * 构造函数
     * 作用：对搜索框进行初始化
     */
    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs); // ->>关注a
        init();// ->>关注b
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    /**
     * 关注a
     * 作用：初始化自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {

        // 控件资源名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);
        // 搜索框字体大小（dp）
        textSizeSearch = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);
        // 搜索框字体颜色（使用十六进制代码，如#333、#8e8e8e）
        int defaultColor = context.getResources().getColor(R.color.colorText); // 默认颜色 = 灰色
        textColorSearch = typedArray.getColor(R.styleable.Search_View_textColorSearch, defaultColor);
        // 搜索框提示内容（String）
        textHintSearch = typedArray.getString(R.styleable.Search_View_textHintSearch);
        // 搜索框高度
        searchBlockHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);
        // 搜索框颜色
        int defaultColor2 = context.getResources().getColor(R.color.colorDefault); // 默认颜色 = 白色
        searchBlockColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, defaultColor2);
        // 释放资源
        typedArray.recycle();
    }


    /**
     * 关注b
     * 作用：初始化搜索框
     */
    private void init(){

        // 1. 初始化UI组件->>关注c
        initView();
        System.out.println("SearchView 布局初始化完成");

        // 2. 实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(context);//创建数据库对象
        System.out.println("数据库实例化完成");
        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");//这是在执行某个方法啊
        /**
         * "清空搜索历史"按钮
         */
        tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // 清空数据库->>关注2
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");
            }
        });

        /**
         * 点击搜索后保存搜索记录
         * 调用时刻：点击键盘上的搜索键时
         */
        et_search.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    // 1. 点击搜索按键后，根据输入的搜索字段进行查询
                    // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                    if (!(mCallBack == null)){
                        mCallBack.SearchAciton(et_search.getText().toString());//获取输入的搜索内容调用方法
                        System.out.println("searchView 里边mCallBack");
                        //
                    }
                    Toast.makeText(context, "需要搜索的是" + et_search.getText(), Toast.LENGTH_SHORT).show();

                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                    boolean hasData = hasData(et_search.getText().toString().trim());//将搜索内容作为参数
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {//如果查询的内容以前没有查询过
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                }
                return false;
            }
        });


        /**
         * 搜索框输入搜索内容调出搜索记录内容
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 每次输入或者取消都进行相关的查询
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = et_search.getText().toString();//获取文本框的输入内容
                queryData(tempName); // ->>关注1

            }
        });


        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("SearchView    列表元素监听");
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 点击返回按键后的事件
         */
        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SearchView    返回键列表元素监听");
                // 注：由于返回需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                if (!(bCallBack == null)){
                    bCallBack.BackAciton();
                }

                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
                Toast.makeText(context, "返回到上一页", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 关注c：绑定搜索框xml视图
     */
    private void initView(){

        System.out.println("SearchView log------里边的初始化内容");
        // 1. 绑定R.layout.search_layout作为搜索框的xml文件
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);//将搜索布局内容填充到activy_searchView
        System.out.println("SearchView log------布局填充完毕");
        // 2. 绑定搜索框EditText
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setTextSize(textSizeSearch);
        et_search.setTextColor(textColorSearch);
        et_search.setHint(textHintSearch);

        // 3. 搜索框背景颜色
        search_block = (LinearLayout)findViewById(R.id.search_block);
        LayoutParams params = (LayoutParams) search_block.getLayoutParams();
        params.height = searchBlockHeight;
        search_block.setBackgroundColor(searchBlockColor);
        search_block.setLayoutParams(params);

        // 4. 历史搜索记录 = ListView显示
        listView = (SearchListView) findViewById(R.id.listView);

        // 5. 删除历史搜索记录 按钮
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setVisibility(INVISIBLE);
        // 6. 返回按键
        searchBack = (ImageView) findViewById(R.id.search_back);
        System.out.println("SearchView  log-----布局初始化结束");
    }

    /**
     * 关注1  查询数据内容
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {
        System.out.println("SearchView   查询数据内容填充到ListView");
        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println("搜索记录的内容条数"+cursor.getCount());
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0){
            System.out.println("SearchView  没搜索但是  有记录条数显示内容并且显示可以清空的内容");
            tv_clear.setVisibility(VISIBLE);//显示可以清除记录的按钮
        }
        else {
            tv_clear.setVisibility(INVISIBLE);//不显示按钮的内容
        }

    }

    /**
     * 关注2：清空数据库
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tv_clear.setVisibility(INVISIBLE);
    }

    /**
     * 关注3,只是判断查询的结果是否有值
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        System.out.println("查询是否有数据");
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 关注4,增加数据库
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();//获取数据库操作对象
        db.execSQL("insert into records(name) values('" + tempName + "')");//执行操作语句
        db.close();//关闭数据库
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;

    }
    /**
     * 点击返回后的操作，用于接口回调
     */
    public void setOnClickBack(bCallBack bCallBack){
        this.bCallBack = bCallBack;

    }
}
