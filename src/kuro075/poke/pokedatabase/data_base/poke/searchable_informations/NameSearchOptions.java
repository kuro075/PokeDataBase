package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import kuro075.poke.pokedatabase.data_base.BasicData;

public enum NameSearchOptions {
	START("から始まる","からはじまる") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			return base.toString().indexOf(text)==0;
		}
	},INVOLVE("を含む","をふくむ") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			return base.toString().indexOf(text)>=0;
		}
	},END("で終わる","でおわる") {
		@Override
		public boolean compareOf(BasicData base, String text) {
			// TODO Auto-generated method stub
			final String name=base.toString();
			if(name.length()>=text.length()){
				return name.lastIndexOf(text)==name.length()-text.length();
			}
			return false;
		}
	};
	
	private final String name;
	private final String hiragana_name;
	NameSearchOptions(String name,String hira){this.name=name;hiragana_name=hira;}
	
	@Override
	public String toString(){return name;}
	public String getHiraganaName(){return hiragana_name;}
	abstract public boolean compareOf(BasicData base,String text);
	
	/**
	 * 文字列からNameSearchOptionsを取得
	 * @param name
	 * @return
	 */
	public static NameSearchOptions fromString(String name){
		for(NameSearchOptions nso:values()){
			if(nso.toString().equals(name)){return nso;}
			if(nso.getHiraganaName().equals(name)){return nso;}
			if(name.indexOf(nso.toString())>=0){return nso;}
			if(name.indexOf(nso.getHiraganaName())>=0){return nso;}
		}
		return null;
	}

}
