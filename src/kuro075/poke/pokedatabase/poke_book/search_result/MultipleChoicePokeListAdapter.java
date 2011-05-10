package kuro075.poke.pokedatabase.poke_book.search_result;

import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
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
public class MultipleChoicePokeListAdapter extends ArrayAdapter<PokeData>{
	  //private LayoutInflater mInflater;
   	  private TextView mNo,mName;
   	  private CheckedTextView mCheck;
   	  
      protected MultipleChoicePokeListAdapter(Context context, List<PokeData> objects) {
   		  super(context, 0, objects);
   		  //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   	  }
      
      public View getView(final int position, View convertView, ViewGroup parent) {
    	  if (convertView == null) {
    		  convertView = new MultipleChoicePokeListItem(getContext());//mInflater.inflate(R.layout.poke_list_row_layout, null);
    	  }
    	  final PokeData item = this.getItem(position);
    	  if(item != null){
    		  mNo = (TextView)convertView.findViewById(R.id.text_no);
	   	      mNo.setText(String.valueOf(item.getNo2String()));
	   	      mName = (TextView)convertView.findViewById(R.id.text_name);
	   	      mName.setText(item.getName());
	   	      mCheck = (CheckedTextView)convertView.findViewById(R.id.checked_text_view);

	   	      //mCheck.setChecked();
	   	    }
  	    return convertView;
   	}
}
