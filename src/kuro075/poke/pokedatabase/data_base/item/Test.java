package kuro075.poke.pokedatabase.data_base.item;

import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import android.util.Log;

public class Test {
	private static final String TAG="ItemData.Test";
	StringBuilder sb;
	public Test(){
       	sb=new StringBuilder();
       	for(int i=0,n=ItemDataManager.INSTANCE.getNum();i<n;i++){
       		Log.v(TAG,"No."+i);
       		ItemData skill=ItemDataManager.INSTANCE.getItemData(i);
       		sb.append(skill.getNo());
       		sb.append(" ");
       		sb.append(skill.toString());
       		sb.append("\n");
       		for(ItemDataManager.ViewableInformations vi:ItemDataManager.ViewableInformations.values()){
       			sb.append(vi.toString());
       			sb.append(":");
       			sb.append(vi.getInformation(skill));
       			sb.append("\n");
       		}
       		sb.append("\n");
       	}
	}
	
	public String getTestString(){
		return new String(sb);
	}
}
