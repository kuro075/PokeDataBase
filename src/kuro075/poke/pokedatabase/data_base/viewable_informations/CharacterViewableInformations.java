package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.CharacterTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;

public enum CharacterViewableInformations {
	BATTLE_EFFECT("戦闘中の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			return chara.getBattleEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					return c1.getBattleEffect().compareTo(c2.getBattleEffect());
				}
			};
		}
	},
	FIELD_EFFECT("フィールド上の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			return chara.getFieldEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					return c1.getFieldEffect().compareTo(c2.getFieldEffect());
				}
			};
		}
	},
	NUM_POKE("ポケモンの数"){
		private int getNumPoke(CharacterData chara){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasCharacter(chara)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(CharacterData chara) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumPoke(chara));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=getNumPoke(c1)-getNumPoke(c2);
					if(d==0){
						return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_DREAM_POKE("ポケモンの数(夢特性)"){
		private int getNumPoke(CharacterData chara){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.getCharacter(CharacterTypes.DREAM)==chara){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(CharacterData chara) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumPoke(chara));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=getNumPoke(c1)-getNumPoke(c2);
					if(d==0){
						return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	};
	
	/*========/
	/  メンバ  /
	/========*/
	private final String name;
	CharacterViewableInformations(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	
	/**
	 * 表示する情報を取得
	 * @param chara
	 * @return
	 */
	abstract public String getInformation(CharacterData chara);
	/**
	 * ソートに使うComparatorを取得
	 * @return
	 */
	abstract public Comparator<CharacterData> getComparator();
}
