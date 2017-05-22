package com.zhangtian.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fastindex)
    FastIndex fastindex;
    private ArrayList<Item> lists = new ArrayList<>();

    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.text)
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initdata();

        listview.setAdapter(new MyAdapter());
        fastindex.setDragIndexListener(new FastIndex.DragIndexListener() {
            @Override
            public void onDrag(String index) {
                for (int i = 0; i < lists.size(); i++) {
                    String s = lists.get(i).pinyin.charAt(0) + "";

                    if (s.equals(index)) {
                        //说明找到了，将其置顶
                        listview.setSelection(i);
                        //找到一次赶紧break
                        break;
                    }
                }

                text.setText(index);
               text.setVisibility(View.VISIBLE);
                ViewCompat.animate(text).scaleX(1.1f).setDuration(300).start();
                ViewCompat.animate(text).scaleY(1.1f).setDuration(300).start();
                ViewCompat.animate(text).alpha(1f).setDuration(300).start();
            }

            @Override
            public void OnRelease() {
                ViewCompat.animate(text).scaleX(0f).setDuration(300).start();
                ViewCompat.animate(text).scaleY(0f).setDuration(300).start();
                ViewCompat.animate(text).alpha(0f).setDuration(300).start();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        text.setVisibility(View.GONE);
//                    }
//                },300);
                text.setVisibility(View.GONE);



            }
        });
        Collections.sort(lists);


    }

    private void initdata() {
        lists.add(new Item("李伟"));
        lists.add(new Item("张三"));
        lists.add(new Item("阿三"));
        lists.add(new Item("阿四"));
        lists.add(new Item("段誉"));
        lists.add(new Item("段正淳"));
        lists.add(new Item("张三丰"));
        lists.add(new Item("陈坤"));
        lists.add(new Item("林俊杰1"));
        lists.add(new Item("陈坤2"));
        lists.add(new Item("王二a"));
        lists.add(new Item("林俊杰a"));
        lists.add(new Item("张四"));
        lists.add(new Item("林俊杰"));
        lists.add(new Item("王二"));
        lists.add(new Item("王二b"));
        lists.add(new Item("赵四"));
        lists.add(new Item("杨坤"));
        lists.add(new Item("赵子龙"));
        lists.add(new Item("杨坤1"));
        lists.add(new Item("李伟1"));
        lists.add(new Item("宋江"));
        lists.add(new Item("宋江1"));
        lists.add(new Item("李伟3"));


    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder = null;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.adapter_friend, null);
                viewholder = new ViewHolder(convertView);
                convertView.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }
            String curLet = lists.get(position).pinyin.charAt(0) + "";



            if(position > 0){
                String preLet = lists.get(position - 1).pinyin.charAt(0)+ "";
                if(curLet.equals(preLet)){
                    viewholder.tvLetter.setVisibility(View.GONE);
                }else {
                    viewholder.tvLetter.setText(curLet);
                    viewholder.tvLetter.setVisibility(View.VISIBLE);
                }
            }else {
                viewholder.tvLetter.setVisibility(View.VISIBLE);
                viewholder.tvLetter.setText(curLet);
            }

            viewholder.tvName.setText(lists.get(position).name);

            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_letter)
        TextView tvLetter;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
