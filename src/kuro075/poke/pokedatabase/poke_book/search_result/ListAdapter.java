package kuro075.poke.pokedatabase.poke_book.search_result;

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
   	  
      protected ListAdapter(Context context, List<ListItemBean> objects) {
   		  super(context, 0, objects);
   		  mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	  }
      
      public View getView(final int position, View convertView, ViewGroup parent) {
    	  if (convertView == null) {
    		  convertView = mInflater.inflate(R.layout.poke_list_row_layout, null);
    	  }
    	  final ListItemBean item = this.getItem(position);
    	  if(item != null){
    		  mNo = (TextView)convertView.findViewById(R.id.text_no);
	   	      mNo.setText(String.valueOf(item.getNo()));
	   	      mName = (TextView)convertView.findViewById(R.id.text_name);
	   	      mName.setText(item.getName());
	   	      mInfo = (TextView)convertView.findViewById(R.id.text_info);
	   	      mInfo.setText(item.getInfo());
	   	    }
  	    return convertView;
   	}
}
