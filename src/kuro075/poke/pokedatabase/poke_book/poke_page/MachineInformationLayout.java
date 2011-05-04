package kuro075.poke.pokedatabase.poke_book.poke_page;

import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MachineInformationLayout extends FrameLayout{

	//リストアダプター
	private class ListAdapter extends ArrayAdapter<ListMachineItemBean>{
		private LayoutInflater mInflater;
		private TextView mNo,mSkillName;
		
		public ListAdapter(Context context,List<ListMachineItemBean> objects){
			super(context,0,objects);
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		public View getView(final int position, View convertView, ViewGroup parent) {
    		  if (convertView == null) {
    			  convertView = mInflater.inflate(R.layout.machine_list_row_layout, null);
    		  }
    		  final ListMachineItemBean item = this.getItem(position);
    		  if(item != null){
    			  mNo = (TextView)convertView.findViewById(R.id.text_No);
	    	      mNo.setText(String.valueOf(item.getMachineNo()));
	    	      mSkillName = (TextView)convertView.findViewById(R.id.text_name);
	    	      mSkillName.setText(item.getSkillName());
	    	      mSkillName.setTextColor(SkillDataManager.INSTANCE.getSkillData(item.getSkillName()).getType().getColor());
	    	    }
	    	    return convertView;
    	  	}
	}

	private PokeData poke;

	public MachineInformationLayout(Context context,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
	}

}
