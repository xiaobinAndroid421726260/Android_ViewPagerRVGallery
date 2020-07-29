package com.dbz.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dbz.gallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2020/6/9 10:36
 * @version V1.0
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int mChildCount = 0;
    private List<String> mDatas = new ArrayList<>();

    public void addData(List<String> data) {
        if (null != data) {
            addData(data, true);
        }
    }

    public void addData(List<String> data, boolean refresh) {
        if (refresh) {
            clear();
        }
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 覆盖getItemPosition()方法，当调用notifyDataSetChanged时，
     * 让getItemPosition方法人为的返回POSITION_NONE，
     * 从而达到强迫viewpager重绘所有item的目的。
     */
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        if (mContext == null){
            mContext = container.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, container, false);
        final TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(mDatas.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}