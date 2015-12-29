package com.winkyqin.cycleviewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar       mToolbar;
    private ViewPager     mViewPager;
    private TextView      mPointDesc;
    private LinearLayout  mLlPointContainer;

    private List<ImageView> mImageList;

    /** 图片文字描述 */
    private String[] mTextDescArray = new String[] {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来了，再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };

    private int mPreviousItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPointDesc = (TextView)  findViewById(R.id.tv_point_desc);
        mLlPointContainer = (LinearLayout) findViewById(R.id.ll_point_container);
    }

    /**
     * 1. 准备资源图片等数据
     * 2. 根据资源图片文件填充ViewPager
     * 3. 设置自动轮播异步任务处理类
     */
    private void initData() {
        //1. 获取资源文件图片
        int[] resArray = {  R.mipmap.a,
                            R.mipmap.b,
                            R.mipmap.c,
                            R.mipmap.d,
                            R.mipmap.e,};

        //2. 将资源文件图片填充至ImageList集合类
        mImageList = new ArrayList<>();
        ImageView imageView = null;
        View point = null;
        for(int i=0; i<resArray.length; i++){
            imageView = new ImageView(this);
            imageView.setBackgroundResource(resArray[i]);
            mImageList.add(imageView);

            // 同时向线性布局中添加小圆点
            point = new View(this);
            point.setBackgroundResource(R.drawable.bg_point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            params.leftMargin = 10;
            point.setEnabled(false);
            point.setLayoutParams(params);
            mLlPointContainer.addView(point);
        }

        mViewPager.setAdapter(new ImageViewAdapter());
        // 选中第一个图片、文字描述
        mLlPointContainer.getChildAt(0).setEnabled(true);
        mPointDesc.setText(mTextDescArray[0]);
        mViewPager.setCurrentItem(0);
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mLlPointContainer.getChildAt(position).setEnabled(true);
                if(mPreviousItem != -1){
                    mLlPointContainer.getChildAt(mPreviousItem).setEnabled(false);
                }
                mPreviousItem = position;
                mPointDesc.setText(mTextDescArray[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 图片轮播适配器
     */
    public class ImageViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mImageList.get(position % mImageList.size());
            //设置图片点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "position:"+position, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageList.get(position%mImageList.size()));
        }

        @Override
        public int getCount() {
            if(null != mImageList && mImageList.size() == 0){
                return mImageList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
