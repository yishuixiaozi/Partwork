package com.hhit.edu.partwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by long on 2016/4/15.
 */
public class TabFragment extends Fragment{

    public static final String FRAG_KEY = "FragKey";//在MoveActivity里边
    private TextView mFragTabText;//新建一个文本标签
    private Button loginMissps;
    /*R.id.frag_tab_text就是为了获取空白页文本对象*/
    private void assignViews(View view) {
        //获取fragment_menu_tab里边的mFragTabText文本标签
        mFragTabText = (TextView) view.findViewById(R.id.frag_tab_text);
    }
    /*下面这个是创造一个视图然后返回*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String title = getArguments().getString(FRAG_KEY);
        View view = inflater.inflate(R.layout.fragment_menu_tab, null);
        assignViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        System.out.println("onActivityCreated设置文本");
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            String title = getArguments().getString(FRAG_KEY);//获取每个传入过来的字体
                mFragTabText.setText(title);
        }
    }

}
