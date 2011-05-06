package kuro075.poke.pokedatabase.data_base;


public enum SearchTypes{
	FILTER("絞込",0),ADD("追加",1),REMOVE("除外",2);
	private final String name;
	private final int index;
	SearchTypes(String name,int index){this.name=name;this.index=index;}
	@Override
	public String toString(){return name;}
	
	public int getIndex(){return index;}
	/**
	 * 文字列からSearchTypesを取得
	 * @param step
	 * @return
	 */
	public static SearchTypes fromString(String name){
		for(SearchTypes type:values()){
			if(type.toString().equals(name)) return type;
		}
		return null;
	}
	/**
	 * インデックスからSearchTypesを取得
	 * @param index
	 * @return
	 */
	public static SearchTypes fromIndex(int index){
		for(SearchTypes type:values()){
			if(type.getIndex()==index){
				return type;
			}
		}
		return null;
	}

	/**
	 * 文字列配列で全ての項目を取得
	 * @return
	 */
	public static String[] toStringArray(){
		String[] arrays=new String[values().length];
		for(int i=0,n=arrays.length;i<n;i++){
			arrays[i]=values()[i].toString();
		}
		return arrays;
	}
}
