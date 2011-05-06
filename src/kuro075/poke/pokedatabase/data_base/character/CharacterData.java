package kuro075.poke.pokedatabase.data_base.character;

import kuro075.poke.pokedatabase.data_base.BasicData;


/**
 * 特性のデータクラス
 * @author sanogenma
 *
 */
public class CharacterData extends BasicData{


	protected static class Builder{
		private String name="-";//名前
		private int no=9999;//管理ナンバー
		private String battle_effect="-";//戦闘中の効果
		private String field_effect="-";//フィールド上の効果
		
		protected Builder(){}

		public CharacterData build(){
			return new CharacterData(name,no,battle_effect,field_effect);
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public void setBattle_effect(String battle_effect) {
			this.battle_effect = battle_effect;
		}

		public void setField_effect(String field_effect) {
			this.field_effect = field_effect;
		}
		
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String battle_effect;//戦闘中の効果
	private final String field_effect;//フィールド上の効果
	
	
	private CharacterData(String name,int no,String battle_effect,String field_effect) {
		super(name,no);
		// TODO Auto-generated constructor stub
		this.battle_effect=battle_effect;
		this.field_effect=field_effect;
	}

	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return getNo()-another.getNo();
	}

	/**
	 * 戦闘中の効果を取得
	 * @return　戦闘中の効果
	 */
	public String getBattleEffect() {
		return battle_effect;
	}

	/**
	 * フィールド上の効果を取得
	 * @return　フィールド上の効果
	 */
	public String getFieldEffect() {
		return field_effect;
	}


}
