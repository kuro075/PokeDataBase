package kuro075.poke.pokedatabase.data_base.type;

import kuro075.poke.pokedatabase.data_base.BasicData;

public class TypeDataForSearch extends BasicData{

	private final TypeDataManager.TypeData type_data1,type_data2;
	
	
	protected TypeDataForSearch(String name,int no,TypeDataManager.TypeData type){
		super(name,no);
		type_data1=type;
		type_data2=null;
	}
	
	protected TypeDataForSearch(String name, int no,TypeDataManager.TypeData type1,TypeDataManager.TypeData type2) {
		super(name, no);
		type_data1=type1;
		type_data2=type2;
	}
	
	//単タイプかどうか
	public boolean isSingleType(){
		return type_data2==null;
	}

	//複合タイプかどうか
	public boolean isMultipleType(){
		return type_data2!=null;
	}
	
	public TypeDataManager.TypeData getType1(){
		return type_data1;
	}
	public TypeDataManager.TypeData getType2(){
		return type_data2;
	}
}