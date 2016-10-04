//
//package project.householdgod;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
///**
// * Created by 10105-김유진 on 2016-09-08.
// */
//public class BaseAdapterEx extends BaseAdapter {
//    private static final int TYPE_COUNT = 2;
//
//    public static final int VIEW_TYPE_COVER_DOOR = 1;
//    public static final int VIEW_TYPE_COVER_BELL = 2;
//    LayoutInflater  mLayoutInflater = null;
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View itemLayout = convertView;
//        ViewHolder viewHolder=null;
//
//        if(itemLayout == null){
//            itemLayout =
//        }
//        return null;
//    }
//
//    @Override
//    public int getViewTypeCount(){
//        return TYPE_COUNT;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        //return super.getItemViewType(position);
//        final ArticleItem item = mArticleItemList.get(position); // 데이터
//
//        if (PostConstants.POST_ITEM_TYPE_COVER_TEXT.equals(item.type)) {
//            return VIEW_TYPE_COVER_TEXT;
//        } else if (PostConstants.POST_ITEM_TYPE_COVER_IMAGE.equals(item.type)) {
//            return VIEW_TYPE_COVER_IMAGE;
//        }
//    }
//}
