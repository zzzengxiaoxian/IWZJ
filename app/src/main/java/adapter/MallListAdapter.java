package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import activity.HomeCompany;
import bean.ImageContent;
import bean.MallList;

/**
 * Created by dell on 2016/11/3.
 */
public class MallListAdapter extends BaseAdapter{

    private String[] mImageList;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public MallListAdapter(Context context) {
        this.mImageList = ImageContent.IMAGELIST;//ImageContent.IMAGELIST
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();
    }

    @Override
    public int getCount() {
        return mImageList.length;
    }

    @Override
    public String getItem(int position) {
        return mImageList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup partent) {

        View view = convertView;
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.item_malllist, partent, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.item_image);
            holder.tv = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv.setText("item"+position);
        ImageLoader.getInstance().displayImage( mImageList[position] , holder.iv , options , animateFirstListener );
        return view;

    }

    private static class ViewHolder {
        ImageView iv;
        TextView tv;
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

    }



}
