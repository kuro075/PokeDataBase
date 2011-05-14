package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import kuro075.poke.pokedatabase.data_base.BasicData;

public enum NameSearchOptions {
	START("から始まる") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			return base.toString().indexOf(text)==0;
		}
	},INVOLVE("を含む") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			return base.toString().indexOf(text)>=0;
		}
	},END("で終わる") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			final String name=base.toString();
			return name.lastIndexOf(text)==name.length()-text.length();
		}
	};
	
	private final String name;
	NameSearchOptions(String name){this.name=name;}
	
	@Override
	public String toString(){return name;}
	
	abstract public boolean compareOf(BasicData base,String text);
	
	/**
	 * 文字列からNameSearchOptionsを取得
	 * @param name
	 * @return
	 */
	public static NameSearchOptions fromString(String name){
		for(NameSearchOptions nso:values()){
			if(nso.toString().equals(name)){return nso;}
		}
		return null;
	}
}
