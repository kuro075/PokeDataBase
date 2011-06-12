package kuro075.poke.pokedatabase;

import java.util.List;

import kuro075.poke.pokedatabase.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ポケモン候補リストアダプター
 * 図鑑No,名前,情報表示
 * @author sanogenma
 *
 */
public class ListAdapter extends ArrayAdapter<ListItemBean>{
   	  private LayoutInflater mInflater;
   	  private TextView mNo,mName,mInfo;
   	  private final boolean view_no;
   	  private final int max_length;
      protected ListAdapter(Context context, List<ListItemBean> objects,boolean view_no,int max_length) {
   		  super(context, 0, objects);
   		  this.view_no=view_no;
   		  this.max_length=max_length;
   		  mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	  }
      
      public View getView(final int position, View convertView, ViewGroup parent) {
    	  if (convertView == null) {
    		  //convertView = mInflater.inflate(view_no?R.layout.result_list_row_layout:R.layout.result_list_row_without_no_layout, null);
    		  convertView = mInflater.inflate(R.layout.result_list_row_layout, null);
    	  }
    	  final ListItemBean item = this.getItem(position);
    	  if(item != null){
    		  mNo = (TextView)convertView.findViewById(R.id.text_no);
		   	  mNo.setText(String.valueOf(item.getNo()));
		   	  if(!view_no) mNo.setVisibility(View.GONE);
	   	      mName = (TextView)convertView.findViewById(R.id.text_name);
	   	      mName.setText(item.getName());
	   	      mName.setMinWidth((int)mName.getTextSize()*max_length);
	   	      mInfo = (TextView)convertView.findViewById(R.id.text_info);
	   	      mInfo.setText(item.getInfo());
	   	    }
  	    return convertView;
   	}
}
