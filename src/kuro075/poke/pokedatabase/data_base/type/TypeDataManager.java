package kuro075.poke.pokedatabase.data_base.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * タイプデータマネージャークラス
 * @author sanogenma
 *
 */
public class TypeDataManager{
	/**
	 * タイプ相性倍率
	 * @author sanogenma
	 *
	 */
	public enum ValuesOfTypeRelation implements Serializable{
		_0("こうかが ないようだ",0),_25("こうかは いまひとつの ようだ",25),_50("こうかは いまひとつの ようだ",50),
		_100("",100),_200("こうかは ばつぐんだ",200),_400("こうかは ばつぐんだ",400);
		private final String text;
		private final int relation;
		ValuesOfTypeRelation(String text,int relation){this.text=text;this.relation=relation;}
		@Override
		public String toString(){return text;}
		public int getRelation(){return relation;}
		
	}
	//N,炎,水,電,草,氷,闘,毒,地,飛,超,虫,岩,霊,竜,悪,鋼　（17種類）
	/**
	 * タイプデータ
	 */
	public enum TypeData implements Serializable{
		N("ノーマル","N",0),炎("ほのお","炎",1),水("みず","水",2),電("でんき","電",3),草("くさ","草",4),
		氷("こおり","氷",5),闘("かくとう","闘",6),毒("どく","毒",7),地("じめん","地",8),飛("ひこう","飛",9),
		超("エスパー","超",10),虫("むし","虫",11),岩("いわ","岩",12),霊("ゴースト","霊",13),竜("ドラゴン","竜",14),
		悪("あく","悪",15),鋼("はがね","鋼",16);
		/**
		 * タイプ相性
		 * [タイプ１][タイプ２]でタイプ１→タイプ２の相性倍率
		 * [攻撃タイプ][防御タイプ]
		 * 1/2->50,1->100,2->200
		 */
		private static final int[][] TypeRelation={
				{/*ノーマル*/100,100,100,100,100,100,100,100,100,100,100,100,50,0,100,100,50},
				{/*ほのお*/100,50,50,100,200,200,100,100,100,100,100,200,50,100,50,100,200},
				{/*みず*/100,200,50,100,50,100,100,100,200,100,100,100,200,100,50,100,100},
				{/*でんき*/100,100,200,50,50,100,100,100,0,200,100,100,100,100,50,100,100},
				{/*くさ*/100,50,200,100,50,100,100,50,200,50,100,50,200,100,50,100,50},
				{/*こおり*/100,50,50,100,200,50,100,100,200,200,100,100,100,100,200,100,50},
				{/*かくとう*/200,100,100,100,100,200,100,50,100,50,50,50,200,0,100,200,200},
				{/*どく*/100,100,100,100,200,100,100,50,50,100,100,100,50,50,100,100,0},
				{/*じめん*/100,200,100,200,50,100,100,200,100,0,100,50,200,100,100,100,200},
				{/*ひこう*/100,100,100,50,200,100,200,100,100,100,100,200,50,100,100,100,50},
				{/*エスパー*/100,100,100,100,100,100,200,200,100,100,50,100,100,100,100,0,50},
				{/*むし*/100,50,100,100,200,100,50,50,100,50,200,100,100,50,100,200,50},
				{/*いわ*/100,200,100,100,100,200,50,100,50,200,100,200,100,100,100,100,50},
				{/*ゴースト*/0,100,100,100,100,100,100,100,100,100,200,100,100,200,100,50,50},
				{/*ドラゴン*/100,100,100,100,100,100,100,100,100,100,100,100,100,100,200,100,50},
				{/*あく*/100,100,100,100,100,100,50,100,100,100,200,100,100,200,100,50,50},
				{/*はがね*/100,50,50,50,100,200,100,100,100,100,100,100,200,100,100,100,50}
		};
		private final String name;//タイプ名
		private final String short_name;//一文字でのタイプ名
		private final int index;//インデックス
		TypeData(String name,String short_name, int index){this.name=name;this.short_name=short_name;this.index=index;}
		@Override
		public String toString(){
			return this.name;
		}
		/**
		 * 一文字のタイプ名を取得
		 * @return 略称タイプ名
		 */
		public String getShortName(){
			return short_name;
		}
		public int getIndex(){return index;}
		private static final int getTypeRelation(TypeData attack_type,TypeData defense_type){return TypeRelation[attack_type.getIndex()][defense_type.getIndex()];}
		/**
		 * 単タイプを攻撃したときの相性を取得
		 * @param defense_type　タイプ
		 * @return 攻撃したときの相性(100倍されたタイプ相性)
		 */
		public int AttackTo(TypeData defense_type){
			return getTypeRelation(this,defense_type);
		}
		/**
		 * 攻撃したときの相性がvalueと同じ防御タイプを全て取得
		 * @param value　タイプ相性倍率
		 * @return 防御タイプのArrayList
		 */
		public ArrayList<TypeData> getDefenseTypesByRelationEquals(ValuesOfTypeRelation value){
			ArrayList<TypeData> types=new ArrayList<TypeData>();
			for(TypeData i:values()){
				if(AttackTo(i)==value.getRelation()){
					types.add(i);
				}
			}
			return types;
		}
		/**
		 * 攻撃されたときの相性を取得
		 * @param attack_type
		 * @return　攻撃されたときの相性
		 */
		public int AttackedBy(TypeData attack_type){
			return getTypeRelation(attack_type,this);
		}
		/**
		 * 攻撃されたときの相性がvalueと同じ攻撃タイプを全て取得
		 * @param value　タイプ相性
		 * @return　攻撃タイプのArrayList
		 */
		public ArrayList<TypeData> getAttackTypesByRelationEquals(ValuesOfTypeRelation value){
			ArrayList<TypeData> types=new ArrayList<TypeData>();
			for(TypeData i:values()){
				if(AttackedBy(i)==value.getRelation()){
					types.add(i);
				}
			}
			return types;
		}
	
		private static final Map<Integer,TypeData> 
			integerToEnum = new HashMap<Integer, TypeData>();//数値からenumへ
		private static final Map<String,TypeData>
			stringToEnum = new HashMap<String,TypeData>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(TypeData td : values()){
				integerToEnum.put(td.index, td);
				stringToEnum.put(td.toString(), td);
				stringToEnum.put(td.getShortName(), td);
			}
		}
		/**
		 * 文字列からTypeDataを取得
		 * @param step
		 * @return
		 */
		public static TypeData fromString(String string){
			return stringToEnum.get(string);
		}
		/**
		 * 数値（インデックスか孵化歩数）からTypeDataを取得
		 * @param integer
		 * @return
		 */
		public static TypeData fromInteger(int integer){
			return integerToEnum.get(integer);
		}
	}
	
	/**
	 * 単色、または複合タイプを数値にして取得
	 * @param type1　タイプ１
	 * @param type2　タイプ２(単色の場合null)
	 * @return
	 */
	public static final int changeTypeToNum(TypeData type1,TypeData type2){
		int num=0;
		if(type1!=null) num+=type1.getIndex()*TypeData.values().length;
		if(type2!=null) num+=type2.getIndex();
		return num;
	}
}
