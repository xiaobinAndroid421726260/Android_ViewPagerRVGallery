package com.dbz.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.dbz.gallery.adapter.RecyclerViewAdapter;
import com.dbz.gallery.adapter.ViewPagerAdapter;
import com.dbz.gallery.view.CustPagerTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mData = new ArrayList<>();

    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);
        mRecyclerView = findViewById(R.id.recyclerView);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 6; i++) {
            mData.add("这是第" + (i + 1) + "个");
        }
        initPagerAdapter();
        initAdapter();
    }

    private void initPagerAdapter() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ViewPagerAdapter();
            // 设置页面之间的边距
            mViewPager.setPageMargin(dp2px(12));
            // 设置缩放 透明度
            mViewPager.setPageTransformer(false, new CustPagerTransformer());
        }
        //添加数据之后在设置适配器这样setPageTransformer会生效，否则两边的item没有透明的效果
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.addData(mData);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 滑动之后处理逻辑
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
            layoutManager = new LinearLayoutManager(this);
            // 设置方向
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mRecyclerView.setLayoutManager(layoutManager);
            // 让item居中显示
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            // 绑定到 mRecyclerView
            snapHelper.attachToRecyclerView(mRecyclerView);
        }
        mAdapter.addData(mData);
        // 需要在添加数据时 再调用一次 不然会在滑动时才会显示效果
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final float MIN_SCALE = 0.85f;
                final float MIN_ALPHA = 0.5f;
                final float MAX_SCALE = 1.0f;
                final float MAX_ALPHA = 1.0f;
                int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);
                    int left = child.getLeft();
                    int paddingStart = recyclerView.getPaddingStart();
                    // 遍历recyclerView子项，以中间项左侧偏移量为基准进行缩放
                    float bl = Math.min(1, Math.abs(left - paddingStart) * 1f / child.getWidth());
                    float scale = MAX_SCALE - bl * (MAX_SCALE - MIN_SCALE);
                    float alpha = MAX_ALPHA - bl * (MAX_ALPHA - MIN_ALPHA);
                    child.setScaleY(scale);
                    child.setAlpha(alpha);
                }
            }
        });
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}