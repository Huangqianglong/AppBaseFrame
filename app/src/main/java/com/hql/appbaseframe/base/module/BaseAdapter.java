package com.hql.appbaseframe.base.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly-huangql
 * <br /> Create time : 2019/7/10
 * <br /> Description :
 */
public abstract class BaseAdapter<T, Y extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<Y> {
    protected Context mContext;
    protected ArrayList<T> dataList = new ArrayList<>();
    protected LayoutInflater inflater;
    protected boolean addHead = false;
    protected boolean addFoot = false;
    //item类型
    public static final int ITEM_NORMAL_VIEW = 0;
    public static final int ITEM_FOOT_VIEW = 1;
    public static final int ITEM_HEAD_VIEW = 2;
    //footer的个数
    private int mFooterCount = 1;
    private View mHeadView;
    protected View mFootView;

    public BaseAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        if (0 != getHeadViewId()) {
            mHeadView = inflater.inflate(getHeadViewId(), null, false);
        }
        if (0 != getFootViewId()) {
            mFootView = inflater.inflate(getFootViewId(), null, false);
        }
    }

    @NonNull
    @Override
    public Y onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case ITEM_HEAD_VIEW:
                if (null == mHeadView) {
                    mHeadView = view = inflater.inflate(getHeadViewId(), viewGroup, false);
                } else {
                    view = mHeadView;//= mHeadView= inflater.inflate(getHeadViewId(), viewGroup, false);
                }

                break;
            case ITEM_FOOT_VIEW:
                if (null == mFootView) {
                    mFootView = view = inflater.inflate(getFootViewId(), null, false);
                } else {
                    view = mFootView;
                }
                break;
            case ITEM_NORMAL_VIEW:
            default:
                view = inflater.inflate(getItemLayout(), viewGroup, false);
                break;
        }
        return getHolder(view, viewType);
    }


    @Override
    public int getItemCount() {
        int increase = 0;
        if (0 != getFootViewId()) {
            increase = increase + mFooterCount;
        }
        if (0 != getHeadViewId()) {
            increase = increase + 1;
        }
        return dataList.size() + increase;
    }

    public int getRealPosition(int position) {
        if (0 != getHeadViewId()) {
            return position - 1;
        }
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void updateList(List<T> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && 0 != getFootViewId()) {
            return ITEM_FOOT_VIEW;
        } else if (position == 0 && 0 != getHeadViewId()) {
            return ITEM_HEAD_VIEW;
        } else {
            return ITEM_NORMAL_VIEW;
        }
    }

    public abstract int getItemLayout();

    public abstract Y getHolder(View view, int viewType);

    public abstract int getFootViewId();

    public abstract int getHeadViewId();

    public View getHeadView() {
        return mHeadView;
    }

    public boolean isAddHead() {
        return addHead;
    }

    public void setAddHead(boolean addHead) {
        this.addHead = addHead;
    }

    public boolean isAddFoot() {
        return addFoot;
    }

    public void setAddFoot(boolean addFoot) {
        this.addFoot = addFoot;
    }


}
