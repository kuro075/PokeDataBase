package kuro075.poke.pokedatabase.data_base;

import java.util.HashMap;
import java.util.Map;


public enum SearchTypes{
	FILTER("絞り込み"),ADD("追加"),REMOVE("除外");
	private final String name;
	SearchTypes(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	
	
	private static final Map<String,SearchTypes>
	stringToEnum = new HashMap<String,SearchTypes>();//文字列からenumへ
static { //定数名からenum定数へのマップを初期化
	for(SearchTypes st : values()){
		stringToEnum.put(st.toString(), st);
	}
}
/**
 * 文字列からSearchTypesを取得
 * @param step
 * @return
 */
public static SearchTypes fromString(String name){
	return stringToEnum.get(name);
}
}
