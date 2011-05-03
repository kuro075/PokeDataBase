package kuro075.poke.pokedatabase.data_base.character;

import android.util.Log;

public class Test {
	private static final String TAG="CharacterData.Test";
	StringBuilder sb;
	public Test(){
       	sb=new StringBuilder();
       	for(int i=0,n=CharacterDataManager.INSTANCE.getNum();i<n;i++){
       		Log.v(TAG,"No."+i);
       		CharacterData character=CharacterDataManager.INSTANCE.getCharacterData(i);
       		sb.append(character.getNo());
       		sb.append(" ");
       		sb.append(character.toString());
       		sb.append("\n");
       		for(kuro075.poke.pokedatabase.data_base.character.CharacterDataManager.ViewableInformations vi:kuro075.poke.pokedatabase.data_base.character.CharacterDataManager.ViewableInformations.values()){
       			sb.append(vi.toString());
       			sb.append(":");
       			sb.append(vi.getInformation(character));
       			sb.append("\n");
       		}
       		sb.append("\n");
       	}
	}
	
	public String getTestString(){
		return new String(sb);
	}
}
