package kuro075.poke.pokedatabase.data_base.type;

import kuro075.poke.pokedatabase.data_base.BasicData;

public class TypeData extends BasicData{

	private final TypeDataManager.TypeData type_data1,type_data2;
	
	
	protected TypeData(String name,int no,TypeDataManager.TypeData type){
		super(name,no);
		type_data1=type;
		type_data2=null;
	}
	
	protected TypeData(String name, int no,TypeDataManager.TypeData type1,TypeDataManager.TypeData type2) {
		super(name, no);
		type_data1=type1;
		type_data2=type2;
	}

}
