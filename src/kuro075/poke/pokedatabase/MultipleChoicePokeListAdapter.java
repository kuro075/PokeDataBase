package kuro075.poke.pokedatabase;

import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.BasicData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

/**
 * 複数選択ポケモンリスト用アダプター
 * @author sanogenma
 *
 */
public class MultipleChoicePokeListAdapter extends ArrayAdapter<BasicData>{
	  //private LayoutInflater mInflater;
   	  private TextView mNo,mName;
   	  private CheckedTextView mCheck;
   	  private final boolean view_no;
   	  private final int max_length;
      protected MultipleChoicePokeListAdapter(Context context, List<BasicData> objects,boolean view_no,int max_length) {
   		  super(context, 0, objects);
   		  this.view_no=view_no;
   		  this.max_length=max_length;
   		  //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	  }
      
      public View getView(final int position, View convertView, ViewGroup parent) {
    	  if (convertView == null) {
    		  convertView = new MultipleChoicePokeListItem(getContext());//mInflater.inflate(R.layout.poke_list_row_layout, null);
    	  }
    	  final BasicData item = this.getItem(position);
    	  if(item != null){
    		  mNo = (TextView)convertView.findViewById(R.id.text_no);
	   	      mNo.setText(String.valueOf(item.getNo2String()));
	   	      if(view_no)mNo.setVisibility(View.VISIBLE);
	   	      else mNo.setVisibility(View.GONE);
	   	      mName = (TextView)convertView.findViewById(R.id.text_name);
	   	      mName.setText(item.getName());
	   	      mName.setMinWidth((int)mName.getTextSize()*max_length);
	   	      mCheck = (CheckedTextView)convertView.findViewById(R.id.checked_text_view);

	   	      //mCheck.setChecked();
	   	    }
  	    return convertView;
   	}
}
