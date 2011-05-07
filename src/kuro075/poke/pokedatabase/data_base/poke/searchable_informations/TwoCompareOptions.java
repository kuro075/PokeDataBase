package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

public enum TwoCompareOptions {
	EQUALS("="){

		@Override
		public boolean compareOf(int left, int right) {
			return left==right;
		}
		
	},
	LESS("<"){

		@Override
		public boolean compareOf(int left, int right) {
			return left<right;
		}
		
	},
	MORE(">"){

		@Override
		public boolean compareOf(int left, int right) {
			return left>right;
		}
		
	},
	LESS_EQUAL("≦"){

		@Override
		public boolean compareOf(int left, int right) {
			return left<=right;
		}
		
	},
	MORE_EQUAL("≧"){

		@Override
		public boolean compareOf(int left, int right) {
			return left>=right;
		}
		
	};

	private String name;
	TwoCompareOptions(String name){this.name=name;}
	
	@Override
	public String toString(){return name;}
	
	abstract public boolean compareOf(int left,int right);
	
	/**
	 * インデックスから取得
	 * @param index
	 * @return
	 */
	public static TwoCompareOptions fromIndex(int index){
		return values()[index];
	}
	
	/**
	 * 文字列から取得
	 * @param name
	 * @return
	 */
	public static TwoCompareOptions fromString(String name){
		for(TwoCompareOptions option:values()){
			if(option.toString().equals(name)){
				return option;
			}
		}
		return null;
	}
}
