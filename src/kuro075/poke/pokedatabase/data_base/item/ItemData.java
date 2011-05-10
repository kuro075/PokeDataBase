package kuro075.poke.pokedatabase.data_base.item;

import java.util.HashMap;
import java.util.Map;

import kuro075.poke.pokedatabase.data_base.BasicData;

/**
 * アイテムクラス
 * @author sanogenma
 *
 */
public class ItemData extends BasicData{

	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return this.getNo()-another.getNo();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static class Builder{
		private String name="-";//名前
		private int no=999;//管理ナンバー
		private ItemClasses item_class=null;//分類
		private ItemSubClasses item_sub_class=null;//サブ分類
		private int buy_value=-1;//買値
		private int sell_value=-1;//売値
		private String using_effect="-";//使った時の効果
		private String having_effect="-";//持たせた時の効果
		private String getting_place="-";//入手場所
		
		protected Builder(){}

		public ItemData build(){
			return new ItemData(name,no,item_class,item_sub_class,buy_value,sell_value,using_effect,having_effect,getting_place);
		}
		public void setName(String name) {
			this.name = name;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public void setItemClass(ItemClasses item_class) {
			this.item_class = item_class;
		}

		public void setUsingEffect(String using_effect) {
			this.using_effect = using_effect;
		}

		public void setHavingEffect(String having_effect) {
			this.having_effect = having_effect;
		}

		public void setBuyValue(int buy_value) {
			this.buy_value = buy_value;
		}

		public void setSellValue(int sell_value) {
			this.sell_value = sell_value;
		}
		
		public void setItemSubClass(ItemSubClasses item_sub_class) {
			this.item_sub_class = item_sub_class;
		}

		public void setGettingPlace(String getting_place) {
			this.getting_place = getting_place;
		}
	}
	
	private final ItemClasses item_class;//分類
	private final ItemSubClasses item_sub_class;//サブ分類
	private final int buy_value;//買値
	private final int sell_value;//売値
	private final String using_effect;//使った時の効果
	private final String having_effect;//持たせた時の効果
	private final String getting_place;//入手場所
	
	private ItemData(String name,int no,ItemClasses item_class,ItemSubClasses item_sub_class,
					 int buy_value,int sell_value,
					 String using_effect,String having_effect,String getting_place) {
		super(name,no);
		// TODO Auto-generated constructor stub
		this.item_class=item_class;
		this.item_sub_class=item_sub_class;
		this.buy_value=buy_value;
		this.sell_value=sell_value;
		this.using_effect=using_effect;
		this.having_effect=having_effect;
		this.getting_place=getting_place;
	}
	//=============================================================
	//enum
	public enum ItemClasses{
		TOOL("道具",0),EQUIPMENT("持たせる道具",1),BALL("ボール",2),RECOVERY("回復",3),MACHINE("わざマシン",4),NUT("きのみ",5),PRECIOUS("たいせつなもの",6);
		
		private final String name;
		private final int index;
		ItemClasses(String name,int index){this.name=name;this.index=index;}
		
		@Override
		public String toString(){return name;}
		public int getIndex(){return index;}
		
		private static final Map<Integer,ItemClasses> 
			integerToEnum = new HashMap<Integer, ItemClasses>();//数値からenumへ
		private static final Map<String,ItemClasses>
			stringToEnum = new HashMap<String,ItemClasses>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(ItemClasses ic : values()){
				integerToEnum.put(ic.index, ic);
				stringToEnum.put(ic.toString(), ic);
			}
		}
		/**
		 * 文字列からItemClassesを取得
		 * @param step
		 * @return
		 */
		public static ItemClasses fromString(String step){
			return stringToEnum.get(step);
		}
		/**
		 * 数値（インデックス）からItemClassesを取得
		 * @param integer
		 * @return
		 */
		public static ItemClasses fromInteger(int integer){
			return integerToEnum.get(integer);
		}
	}
	/**
	 * サブクラス
	 * @author sanogenma
	 *
	 */
	enum ItemSubClasses{
		HP("HP回復"),PP("PP回復"),STATE("状態回復"),EFF("努力値"),OKOU("おこう"),
		REINFORCEMENT("能力変化"),EVOLUTION("進化アイテム"),EXCLUSIVE("専用アイテム"),PLATE("プレート"),
		JEWEL("ジュエル"),FOSSIL("化石"),WEATHER("天候岩"),HALF("半減の実"),
		BATTLE("せんとうよう"),MACHINE("わざマシン"),BALL("ボール"),MAIL("メール"),
		PRECIOUS("たいせつなもの"),OTHER("その他");
		
		private final String name;
		ItemSubClasses(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		
		private static final Map<String,ItemSubClasses>
			stringToEnum = new HashMap<String,ItemSubClasses>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(ItemSubClasses isc : values()){
				stringToEnum.put(isc.toString(), isc);
			}
		}
		/**
		 * 文字列からItemSubClassesを取得
		 * @param step
		 * @return
		 */
		public static ItemSubClasses fromString(String step){
			return stringToEnum.get(step);
		}
	}
	//=============================================================
	//メソッド
	public ItemClasses getItemClass() {
		return item_class;
	}
	public ItemSubClasses getItemSubClass() {
		return item_sub_class;
	}
	public int getBuyValue() {
		return buy_value;
	}
	public int getSellValue() {
		return sell_value;
	}
	public String getUsingEffect() {
		return using_effect;
	}
	public String getHavingEffect() {
		return having_effect;
	}
	public String getGettingPlace() {
		return getting_place;
	}
}
