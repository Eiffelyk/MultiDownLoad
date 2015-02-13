package com.eiffelyk.www.multidownload.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eiffelyk.www.multidownload.R;
import com.eiffelyk.www.multidownload.db.DbTool;
import com.eiffelyk.www.multidownload.entity.FileState;
import com.eiffelyk.www.multidownload.service.DownloadService;

import java.util.List;


/**
 *  类名称：DownloadManageAdapter 类描述： 下载管理的适配器 创建人：wpy
 * 创建时间：2014-10-11 下午6:30:10
 */
public class DownloadManageAdapter extends BaseAdapter {
    private Context context;
    private List<FileState> fileStates;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private ListView listView;
    public DownloadManageAdapter(Context context, List<FileState> fileStates, DbTool dao) {
        this.context = context;
        this.fileStates = fileStates;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置当前listview给adapter更新单条数据用
     * @param listView  View
     */
    public void setListView(ListView listView){
        this.listView =listView;
    }

    /**
     * 更新listView单条数据
     * @param index 当前条目的索引值
     */
    public void updateView(int index, final FileState fileState){
        int visiblePos = listView.getFirstVisiblePosition();
        int offset = index - visiblePos;
        if(offset <0 ) return;
        View view = listView.getChildAt(offset);
        ViewHolder holder = (ViewHolder) view.getTag();
        //final FileState fileState = fileStates.get(index);
        setDateToView(index, holder, fileState);
    }

    /**
     * 给item中的单项赋值
     * @param index 索引
     * @param holder viewHolder
     * @param fileState  Model对象
     */
    private void setDateToView(int index, ViewHolder holder, FileState fileState) {
        holder.tv_name.setText(fileState.getName());
        holder.iv_icon.setBackgroundResource(R.drawable.ico);// TODO: 2015/1/22 后续修改为网络图片
        if (0 == fileState.getState()) {// 下载完成
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.btn_stop.setText("已下载");
            holder.btn_stop.setClickable(false);
        } else if (1 == fileState.getState()) {// 正在下载
            int completeSize = fileState.getCompleteSize();
            // Log.e("test>>", "progressBar  completeSize当前进度：" + completeSize);
            int fileSize = fileState.getFileSize();
            // Log.e("test>>", "progressBar文件大小：" + fileSize);
            float num = (float) completeSize / (float) fileSize;
            int result = (int) (num * 100);
            holder.progressBar.setProgress(result);
            Log.e("test>>", "progressBar当前进度：" + result);
        }
        // 当文件下载完成
        if (fileState.getCompleteSize() == fileState.getFileSize()) {
            fileState.setState(0);
            fileStates.set(index, fileState);
            holder.progressBar.setVisibility(ProgressBar.INVISIBLE);
            holder.btn_stop.setText("已下载");
            holder.btn_stop.setClickable(false);
        }
    }

    @Override
    public int getCount() {
        return fileStates.size();
    }

    @Override
    public Object getItem(int position) {
        return fileStates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.download_list_item, parent,
                    false);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.progressBar = (ProgressBar) convertView
                    .findViewById(R.id.progressBar);
            holder.progressBar.setMax(100);
            holder.btn_stop = (Button) convertView.findViewById(R.id.btn_stop);
            holder.btn_continue = (Button) convertView
                    .findViewById(R.id.btn_continue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FileState fileState = fileStates.get(position);
        setBtnLister(fileState);
        setDateToView(position, holder, fileState);
        return convertView;
    }

    /***
     * item中的按钮设置listener事件
     * @param fileState model对象
     */
    private void setBtnLister(final FileState fileState) {
        holder.btn_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChange(fileState);
                holder.btn_stop.setVisibility(View.GONE);
                holder.btn_continue.setVisibility(View.VISIBLE);
            }
        });
        holder.btn_continue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChange(fileState);
                holder.btn_continue.setVisibility(View.GONE);
                holder.btn_stop.setVisibility(View.VISIBLE);
            }
        });
    }

    /***
     * 通知下载器更改当前下载状态
     * @param fileState
     */
    private void setChange(FileState fileState) {
        Intent intent = new Intent();
        intent.setClass(context, DownloadService.class);
        intent.putExtra("downloadUrl", fileState.getUrl());
        intent.putExtra("name", fileState.getName());
        intent.putExtra("flag", "changeState");
        context.startService(intent);
    }

    private class ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private ProgressBar progressBar;
        private Button btn_stop;
        private Button btn_continue;
    }
}
