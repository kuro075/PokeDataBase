package kuro075.poke.pokedatabase.data_base.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;

import android.graphics.Color;

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
	public enum TypeRelations implements Serializable{
		_0("こうかが ないようだ",0,0,"×"),_25("こうかは いまひとつの ようだ",25,1,"▲"),_50("こうかは いまひとつの ようだ",50,2,"△"),
		_100("",100,3,""),_200("こうかは ばつぐんだ",200,4,"○"),_400("こうかは ばつぐんだ",400,5,"◎");
		private final String text;
		private final int relation;//相性
		private final int index;//インデックス
		private final String figure;//図形 
		TypeRelations(String text,int relation,int index,String figure){this.text=text;this.relation=relation;this.index=index;this.figure=figure;}
		@Override
		public String toString(){return text;}
		public int getRelation(){return relation;}
		public int getIndex(){return index;}
		
		public String getFigure(){return figure;}
		/**
		 * 相性同士のかけ算
		 * @param type_relations
		 * @return
		 */
		public TypeRelations by(TypeRelations type_relations){
			return TypeRelations.fromRelation(this.relation*type_relations.relation/100);
		}
		/**
		 * インデックスから取得
		 * @param index
		 * @return
		 */
		public static TypeRelations fromIndex(int index){
			for(TypeRelations relation:values()){
				if(relation.getIndex()==index) return relation;
			}
			return null;
		}
		/**
		 * 相性から取得
		 * @param index
		 * @return
		 */
		public static TypeRelations fromRelation(int relation){
			for(TypeRelations type_relation:values()){
				if(type_relation.getRelation()==relation)return type_relation;
			}
			return null;
		}
	}
	//N,炎,水,電,草,氷,闘,毒,地,飛,超,虫,岩,霊,竜,悪,鋼　（17種類）
	/**
	 * タイプデータ
	 */
	public enum TypeData implements Serializable{
		N("ノーマル","N",0) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}
			
			@Override
			public String getSkillFeature() {
				return "数が最も多い。";
			}
		},
		炎("ほのお","炎",1){
			@Override
			public String getPokeFeature(){
				return "「やけど」状態にならない。";
			}

			@Override
			public String getSkillFeature() {
				return "天気が「ひざしがつよい」のとき、威力が1.5倍。天気が「あめ」のとき、威力が0.5倍。とくせい「かんそうはだ」には威力が1.25倍。とくせい「あついしぼう」で半減。とくせい「もらいび」で無効化。";
			}
		},
		水("みず","水",2) {
			@Override
			public String getPokeFeature() {
				return "数が最も多い。";
			}

			@Override
			public String getSkillFeature() {
				return "天気が「あめ」のとき、威力が1.5倍。天気が「ひざしがつよい」のとき、威力が0.5倍。とくせい「かんそうはだ・よびみず・ちょすい」で無効化。";
			}
		},電("でんき","電",3) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}

			@Override
			public String getSkillFeature() {
				return "とくせい「ちくでん・ひらいしん・でんきエンジン」で無効化。";
			}
		},草("くさ","草",4) {
			@Override
			public String getPokeFeature() {
				return "わざ「やどりぎのタネ」を受けない。";
			}

			@Override
			public String getSkillFeature() {
				return "とくせい「そうしょく」で無効化。";
			}
		},
		氷("こおり","氷",5) {
			@Override
			public String getPokeFeature() {
				return "「こおり」状態にならない。天気「あられ」のダメージを受けない。";
			}

			@Override
			public String getSkillFeature() {
				return "とくせい「あついしぼう」で半減。";
			}
		},闘("かくとう","闘",6) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},毒("どく","毒",7) {
			@Override
			public String getPokeFeature() {
				return "「どく・もうどく」状態にならない。わざ「どくびし」を消滅させる。持ち物「くろいヘドロ」で毎ターン最大HPの1/16回復";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},地("じめん","地",8) {
			@Override
			public String getPokeFeature() {
				return "天気「すなあらし」のダメージを受けない。";
			}

			@Override
			public String getSkillFeature() {
				return "とくせい「ふゆう」で無効化。わざ「でんじふゆう」で無効化。";
			}
		},飛("ひこう","飛",9) {
			@Override
			public String getPokeFeature() {
				return "わざ「まきびし・どくびし」、とくせい「ありじごく」の影響を受けない。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},
		超("エスパー","超",10) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},虫("むし","虫",11) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},岩("いわ","岩",12) {
			@Override
			public String getPokeFeature() {
				return "天気「すなあらし」のダメージを受けない。天気が「すなあらし」のとき、「とくぼう」が1.5倍になる";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},霊("ゴースト","霊",13) {
			@Override
			public String getPokeFeature() {
				return "わざ「かぎわける・みやぶる」を受けると「ノーマル・かくとう」タイプの「わざ」が当たるようになる。とくせい「きもったま」の「ノーマル・かくとう」タイプの「わざ」が当たる";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},竜("ドラゴン","竜",14) {
			@Override
			public String getPokeFeature() {
				return "特になし。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},
		悪("あく","悪",15) {
			@Override
			public String getPokeFeature() {
				return "わざ「ミラクルアイ」を受けると、「エスパー」タイプの「わざ」が当たるようになる。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		},鋼("はがね","鋼",16) {
			@Override
			public String getPokeFeature() {
				return "「どく・もうどく」状態にならない。天気「すなあらし」のダメージを受けない。とくせい「じりょく」で逃げられない。";
			}

			@Override
			public String getSkillFeature() {
				return "特になし。";
			}
		};
		/**
		 * タイプ相性
		 * [タイプ１][タイプ２]でタイプ１→タイプ２の相性倍率
		 * [攻撃タイプ][防御タイプ]
		 * 1/2->50,1->100,2->200
		 */
		private static final int[][] TYPE_RELATION={
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
		//タイプの色
		private static final int[] TypeColor={/*ノーマルColor.rgb(204,204,153)*/Color.rgb(204, 204, 204),/*ほのお*/Color.rgb(255,102,0),/*みず*/Color.rgb(0,102,255),
			 /*でんき*/Color.rgb(255,220,10),/*くさ*/Color.rgb(102,204,0),/*こおり*/Color.rgb(53,153,255),
			 /*かくとう*//*Color.rgb(102,0,51)*/Color.rgb(122, 20, 71),/*どく*/Color.rgb(153,0,153),/*じめん*//*Color.rgb(153,102,51)*/Color.rgb(173, 122, 71),
			 /*ひこう*/Color.rgb(153,153,255),/*エスパー*/Color.rgb(255,102,204),/*むし*/Color.rgb(204,204,0),
			 /*いわ*//*Color.rgb(204,153,51)*/Color.rgb(204, 153, 51),/*ゴースト*//*Color.rgb(51,0,102)*/Color.rgb(111, 60, 162),/*ドラゴン*//*Color.rgb(102,0,153)*/Color.rgb(142, 40, 193),
			 /*あく*//*Color.rgb(102,51,51)*/Color.rgb(112, 61, 61),/*はがね*/Color.rgb(153,153,153)};

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
		
		public int getColor(){
			return TypeColor[index];
		}
		
		public abstract String getSkillFeature();
		public abstract String getPokeFeature();
		
		/**
		 * 相性を取得
		 * @param attack_type
		 * @param defense_type
		 * @return
		 */
		private static final TypeRelations getTypeRelation(TypeData attack_type,TypeData defense_type){
			return TypeRelations.fromRelation(TYPE_RELATION[attack_type.getIndex()][defense_type.getIndex()]);
		}
		
		
		/**
		 * 単タイプを攻撃したときの相性を取得
		 * @param defense_type　タイプ
		 * @return 攻撃したときの相性(100倍されたタイプ相性)
		 */
		public TypeRelations attackTo(TypeData defense_type){
			return getTypeRelation(this,defense_type);
		}
		/**
		 * 複合タイプを攻撃したときの相性を取得
		 * @param defense_type1
		 * @param defense_type2
		 * @return
		 */
		public TypeRelations attackTo(TypeData defense_type1,TypeData defense_type2){
			if(defense_type1==null){
				if(defense_type2==null){
					return TypeRelations._100;
				}else{
					return attackTo(defense_type2);
				}
			}else
			if(defense_type2==null){
				return attackTo(defense_type1);
			}
			
			return getTypeRelation(this,defense_type1).by(getTypeRelation(this,defense_type2));
		}
		
		/**
		 * ポケモンに攻撃したときの相性を取得
		 * @param poke
		 * @return
		 */
		public TypeRelations attackTo(PokeData poke){
			return attackTo(poke.getType(0),poke.getType(1));
		}
		/**
		 * 攻撃したときの相性がvalueと同じ防御タイプを全て取得
		 * @param value　タイプ相性倍率
		 * @return 防御タイプのArrayList
		 */
		public ArrayList<TypeData> getDefenseTypesByRelationEquals(TypeRelations value){
			ArrayList<TypeData> types=new ArrayList<TypeData>();
			for(TypeData i:values()){
				if(attackTo(i)==value){
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
		public TypeRelations attackedBy(TypeData attack_type){
			return getTypeRelation(attack_type,this);
		}
		/**
		 * 攻撃されたときの相性がvalueと同じ攻撃タイプを全て取得
		 * @param value　タイプ相性
		 * @return　攻撃タイプのArrayList
		 */
		public ArrayList<TypeData> getAttackTypesByRelationEquals(TypeRelations value){
			ArrayList<TypeData> types=new ArrayList<TypeData>();
			for(TypeData i:values()){
				if(attackedBy(i)==value){
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
	
		/**
		 * 項目を文字列配列にして返す
		 * @return
		 */
		public static String[] toStringArray(){
			String[] array=new String[values().length];
			for(int i=0,n=values().length;i<n;i++){
				array[i]=values()[i].toString();
			}
			return array;
		}
	}
	/**
	 * 複合タイプに攻撃したときの相性がvalueのタイプを全て取得
	 * @param defense_type1
	 * @param defense_type2 単タイプのとき、null
	 * @param value
	 * @return
	 */
	public static final TypeData[] getWeakTypes(TypeData defense_type1,TypeData defense_type2,TypeRelations value){
		List<TypeData> weak_list=new ArrayList<TypeData>();
		for(TypeData attack_type:TypeData.values()){
			if(attack_type.attackTo(defense_type1,defense_type2)==value){
				weak_list.add(attack_type);
			}
		}
		return weak_list.toArray(new TypeData[0]);
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
