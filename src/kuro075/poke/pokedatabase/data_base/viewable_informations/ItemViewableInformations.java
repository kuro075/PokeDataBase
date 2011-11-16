package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.ItemRarities;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;

public enum ItemViewableInformations {
	CLASS("分類") {
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			return item.getItemClass().toString();
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getItemClass().toString().compareTo(item2.getItemClass().toString());
				}
			};
		}
	},
	SUB_CLASS("サブ分類"){
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			return item.getItemSubClass().toString();
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getItemSubClass().toString().compareTo(item2.getItemSubClass().toString());
				}
			};
		}
	},
	BUY_VALUE("買値"){
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			final int value=item.getBuyValue();
			if(value<0){
				return "-";
			}
			return String.valueOf(value);
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getBuyValue()-item2.getBuyValue();
				}
			};
		}
	},
	SELL_VALUE("売値"){
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			final int value=item.getSellValue();
			if(value<0){
				return "-";
			}
			return String.valueOf(value);
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getSellValue()-item2.getSellValue();
				}
			};
		}
	},
	USING_EFFECT("使用時の効果"){
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			return item.getUsingEffect();
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getUsingEffect().compareTo(item2.getUsingEffect().toString());
				}
			};
		}
	},
	HAVING_EFFECT("所持時の効果"){
		@Override
		public String getInformation(ItemData item) {
			// TODO Auto-generated method stub
			return item.getHavingEffect();
		}
		@Override
		public Comparator<ItemData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					// TODO Auto-generated method stub
					return item1.getHavingEffect().compareTo(item2.getHavingEffect().toString());
				}
			};
		}
	},
	GETTING_PLACE("入手場所(BW)"){
		@Override
		public String getInformation(ItemData item) {
			return item.getGettingPlace();
		}
		@Override
		public Comparator<ItemData> getComparator() {
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData item1, ItemData item2) {
					return item1.getGettingPlace().compareTo(item2.getGettingPlace());
				}
			};
		}
	},
	NUM_HAVING_POKE("所持ポケモン"){
		@Override
		public String getInformation(ItemData item) {
			StringBuilder sb=new StringBuilder();
			sb.append(item.getNumPoke());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<ItemData> getComparator() {
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData s1, ItemData s2) {
					int d=s1.getNumPoke()-s2.getNumPoke();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_NORMAL_HAVING_POKE("所持ポケモン(通常)"){
		@Override
		public String getInformation(ItemData item) {
			StringBuilder sb=new StringBuilder();
			sb.append(item.getNumPokeCommon());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<ItemData> getComparator() {
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData s1, ItemData s2) {
					int d=s1.getNumPokeCommon()-s2.getNumPokeCommon();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_RARE_HAVING_POKE("所持ポケモン(レア)"){
		@Override
		public String getInformation(ItemData item) {
			StringBuilder sb=new StringBuilder();
			sb.append(item.getNumPokeRare());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<ItemData> getComparator() {
			return new Comparator<ItemData>(){
				@Override
				public int compare(ItemData s1, ItemData s2) {
					int d=s1.getNumPokeRare()-s2.getNumPokeRare();
					if(d==0){
						return s1.getNo()-s2.getNo();
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
	ItemViewableInformations(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	/**
	 * 表示する情報を取得
	 * @param skill
	 * @return
	 */
	abstract public String getInformation(ItemData item);
	/**
	 * ソートに使うComparatorを取得
	 * @return
	 */
	abstract public Comparator<ItemData> getComparator();
}
