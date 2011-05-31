package kuro075.poke.pokedatabase.data_base.search.poke;

/**
 * 比較条件
 * @author sanogenma
 *
 */
public enum OneCompareOptions {
	EQUAL("に等しい") {
		@Override
		public boolean compareOf(int spec, int th) {
			// TODO Auto-generated method stub
			return spec==th;
		}
	},MORE("以上") {
		@Override
		public boolean compareOf(int spec, int th) {
			// TODO Auto-generated method stub
			return spec>=th;
		}
	},LESS("以下") {
		@Override
		public boolean compareOf(int spec, int th) {
			// TODO Auto-generated method stub
			return spec<=th;
		}
	};
	private String name;
	OneCompareOptions(String name){this.name=name;}
	
	@Override
	public String toString(){return name;}
	
	abstract public boolean compareOf(int spec,int th);
	
	/**
	 * インデックスから取得
	 * @param index
	 * @return
	 */
	public static OneCompareOptions fromIndex(int index){
		return values()[index];
	}
	
	/**
	 * 文字列から取得
	 * @param name
	 * @return
	 */
	public static OneCompareOptions fromString(String name){
		for(OneCompareOptions option:values()){
			if(option.toString().equals(name)){
				return option;
			}
		}
		return null;
	}
}
