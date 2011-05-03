package kuro075.poke.pokedatabase.data_base.poke;

import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager.ViewableInformations;

/**
 * PokeDataのテストクラス
 * @author sanogenma
 *
 */
public class Test {
	
	StringBuilder sb;
	public Test(){
       	sb=new StringBuilder();
       	for(int i=1,n=PokeDataManager.INSTANCE.getNum();i<=n;i++){
       		PokeData poke=PokeDataManager.INSTANCE.getPokeData(i);
       		sb.append(poke.getNo());
       		sb.append(" ");
       		sb.append(poke.toString());
       		sb.append("\n");
       		for(ViewableInformations vi:ViewableInformations.values()){
       			sb.append(vi.toString());
       			sb.append(":");
       			sb.append(vi.getInformation(poke));
       			sb.append("\n");
       		}
       		sb.append("\n");
       	}
	}
	
	public String getTestString(){
		return new String(sb);
	}
}
