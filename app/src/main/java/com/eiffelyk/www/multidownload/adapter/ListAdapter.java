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
import android.widget.TextView;
import android.widget.Toast;

import com.eiffelyk.www.multidownload.R;
import com.eiffelyk.www.multidownload.constant.Constant;
import com.eiffelyk.www.multidownload.entity.DownloadFile;
import com.eiffelyk.www.multidownload.service.DownloadService;
import com.eiffelyk.www.multidownload.util.NetworkUtil;

import java.util.List;


public class ListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<DownloadFile> files;


    public ListAdapter(Context context, List<DownloadFile> files) {
        this.context = context;
        this.files = files;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (files != null && files.size() > 0) {
            return files.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.resouceName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.startDownload = (Button) convertView.findViewById(R.id.btn_download);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DownloadFile file = files.get(position);
        viewHolder.icon.setImageResource(R.drawable.ico);// TODO: 2015/1/22   以后改成真是网络地址图片
        viewHolder.resouceName.setText(file.getName());
        viewHolder.startDownload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                NetworkUtil networkUtil = new NetworkUtil(context);
                if (networkUtil.isNetworkConnected()) {
                    if (Constant.IS_NETWORKTYPE&& networkUtil.getNetworkType() == 1){
                            intentDownloadService(file);
                    }else if(!Constant.IS_NETWORKTYPE){
                        intentDownloadService(file);
                    } else {
                        Toast.makeText(context, "当前的网络状态不是wifi，请先设置", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,"未连接到互联网",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private void intentDownloadService(DownloadFile file) {
        Intent intent = new Intent();
        intent.setClass(context, DownloadService.class);
        intent.putExtra("downloadUrl", file.getPath());
        intent.putExtra("name", file.getName());
        intent.putExtra("image", file.getImg_path());
        intent.putExtra("flag", "startDownload");
        Log.e("go", file.getName() + ">>>>>" + file.getPath());
        context.startService(intent);
    }

    private class ViewHolder {
        public ImageView icon;
        public TextView resouceName;
        public Button startDownload;
    }
}
