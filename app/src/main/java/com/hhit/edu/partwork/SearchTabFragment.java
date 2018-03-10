package com.hhit.edu.partwork;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhit.edu.view.EditText_Clear;
import com.hhit.edu.view.ObservableScrollView;
import com.hhit.edu.view.SearchView;

import static com.hhit.edu.partwork.R.id.ll_topView;

/**
 * Created by 93681 on 2018/3/7.
 */

public class SearchTabFragment extends Fragment
        implements ObservableScrollView.OnObservableScrollViewScrollChanged{

    public static final String FRAG_KEY = "FragKey";//在MoveActivity里边
    private ObservableScrollView sv_contentView;
    private LinearLayout ll_topView;
    private TextView tv_topView;
    private LinearLayout ll_fixedView;
    private LinearLayout ll_contentView;
    private EditText_Clear et_Clear;
    private SearchView searchView;
    private int mHeight=394;
    private int height;
    private Button loginMissps;
    /*R.id.frag_tab_text就是为了获取空白页文本对象*/
    private void assignViews(View view) {
        //获取fragment_menu_tab里边的mFragTabText文本标签
        //mFragTabText = (TextView) view.findViewById(R.id.frag_tab_text);
        sv_contentView= (ObservableScrollView)view. findViewById(R.id.sv_contentView);
        ll_topView= (LinearLayout) view.findViewById(R.id.ll_topView);
        tv_topView= (TextView) view.findViewById(R.id.tv_topView);
        ll_fixedView= (LinearLayout)view. findViewById(R.id.ll_fixedView);
        //这个布局是用来后期去掉的
        ll_contentView= (LinearLayout) view.findViewById(R.id.ll_contentView);
        //这里是获得一个listview对象
        //滚动布局设置监听
        searchView= (SearchView) view.findViewById(R.id.search_view);
        //获取搜索框输入文本的对象
        //et_Clear= (EditText_Clear) searchView.findViewById(R.id.et_search);
        sv_contentView.setOnObservableScrollViewScrollChanged(this);
    }
    /*下面这个是创造一个视图然后返回*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String title = getArguments().getString(FRAG_KEY);
        View view = inflater.inflate(R.layout.activity_udmove, null);
        assignViews(view);
        view.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("post  -----"+getHeight());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        System.out.println("onActivityCreated设置文本");
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    /**
     *
     * @return
     */
    public int getHeight(){
        return ll_topView.getTop();
    }

    /**
     * 这是implements后面的监听事件处理
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    public void onObservableScrollViewScrollChanged(int l, int t, int oldl, int oldt) {
        System.out.println();
        if (t>=mHeight){//向上互动剧大于mHeight
            if (tv_topView.getParent()!=ll_fixedView){//tv_topView 固定文本   ll_topView 固定线性布局
                ll_fixedView.removeView(searchView);
                ll_topView.removeView(tv_topView);//线性布局里边除去固定文本
                ll_fixedView.addView(tv_topView);//搜索框里边改为tv_topView
            }
        }else {
            if (tv_topView.getParent()!=ll_topView){//这里已经导致了tv_topView的福清是fixed了
                ll_fixedView.removeView(tv_topView);
                ll_topView.addView(tv_topView);
                ll_fixedView.addView(searchView);//添加上布局

            }

        }
    }
}
