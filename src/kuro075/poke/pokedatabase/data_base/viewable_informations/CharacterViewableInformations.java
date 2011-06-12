package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.character.CharacterData;

public enum CharacterViewableInformations {
	BATTLE_EFFECT("戦闘中の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			// TODO Auto-generated method stub
			return chara.getBattleEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					// TODO Auto-generated method stub
					return c1.getBattleEffect().compareTo(c2.getBattleEffect());
				}
			};
		}
	},
	FIELD_EFFECT("フィールド上の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			// TODO Auto-generated method stub
			return chara.getFieldEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					// TODO Auto-generated method stub
					return c1.getFieldEffect().compareTo(c2.getFieldEffect());
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
