package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.CharacterTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.ItemRarities;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Sexes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Statuses;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;

public enum PokeViewableInformations {
	TYPE("タイプ"){
		@Override
		public String getInformation(PokeData poke){
			TypeData type2=poke.getType(1);
			if(type2==null){
				return poke.getType(0).toString();
			}
			return poke.getType(0).toString()+" / "+type2.toString();
		}
		@Override
		public Comparator<PokeData> getComparator(){
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					int p1_type=TypeDataManager.changeTypeToNum(p1.getType(0), p1.getType(1));
					int p2_type=TypeDataManager.changeTypeToNum(p2.getType(0), p2.getType(1));
					return p1_type-p2_type;
				}
			};
		}
	},
	CHARACTER1("特性1"){
		@Override
		public String getInformation(PokeData poke){
			//pokeからとくせい１を取得してCharacterDataで変換して返す
			return poke.getCharacter(CharacterTypes.FIRST).toString();
		}
		@Override
		public Comparator<PokeData> getComparator(){
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getCharacter(CharacterTypes.FIRST).compareTo(p2.getCharacter(CharacterTypes.FIRST));
				}
			};
		}
	},
	CHARACTER2("特性2"){
		@Override
		public String getInformation(PokeData poke){
			//pokeからとくせい2を取得してCharacterDataで変換して返す
			return poke.getCharacter(CharacterTypes.SECOND).toString();
		}
		@Override
		public Comparator<PokeData> getComparator(){
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getCharacter(CharacterTypes.SECOND).compareTo(p2.getCharacter(CharacterTypes.SECOND));
				}
			};
		}
	},
	DREAM_CHARACTER("夢特性"){
		@Override
		public String getInformation(PokeData poke){
			//pokeから夢特性を取得してCharacterDataで変換して返す
			return poke.getCharacter(CharacterTypes.DREAM).toString();
		}
		@Override
		public Comparator<PokeData> getComparator(){
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getCharacter(CharacterTypes.DREAM).compareTo(p2.getCharacter(CharacterTypes.DREAM));
				}
			};
		}
	},
	SPEC_ALL("種族値(全て)"){
		@Override
		public String getInformation(PokeData poke){
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<6;i++){
				sb.append(poke.getSpec(Statuses.values()[i]));
				if(i<5) sb.append(",");
			}
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getTotalSpec()-p2.getTotalSpec();
				}
			};
		}
	},
	SPEC_HP("種族値(HP)"){
		private final Statuses status=Statuses.H;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_ATTACK("種族値(攻撃)"){
		private final Statuses status=Statuses.A;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_BLOCK("種族値(防御)"){
		private final Statuses status=Statuses.B;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_CONTACT("種族値(特殊)"){
		private final Statuses status=Statuses.C;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_DEFENSE("種族値(特防)"){
		private final Statuses status=Statuses.D;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_SPEED("種族値(素早)"){
		private final Statuses status=Statuses.S;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_TOTAL("種族値(合計)"){
		private final Statuses status=Statuses.TOTAL;
		@Override
		public String getInformation(PokeData poke){
			return String.valueOf(poke.getSpec(status));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_MIN("種族値(最小)"){
		private final Statuses status=Statuses.MIN;
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			List<Statuses> min_list=new ArrayList<Statuses>();
			int min=poke.getSpec(status);
			for(int i=0;i<6;i++){
				if(min==poke.getSpec(Statuses.values()[i])){
					min_list.add(Statuses.values()[i]);
				}
			}
			StringBuilder sb=new StringBuilder();
			sb.append(min);
			sb.append(" (");
			sb.append(min_list.get(0).toString());
			for(int i=1,n=min_list.size();i<n;i++){
				sb.append(",");
				sb.append(min_list.get(i).toString());
			}
			sb.append(")");
			return String.valueOf(new String(sb));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	SPEC_MAX("種族値(最大)"){
		private final Statuses status=Statuses.MAX;
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			List<Statuses> max_list=new ArrayList<Statuses>();
			int max=poke.getSpec(status);
			for(int i=0;i<6;i++){
				if(max==poke.getSpec(Statuses.values()[i])){
					max_list.add(Statuses.values()[i]);
				}
			}
			StringBuilder sb=new StringBuilder();
			sb.append(max);
			sb.append(" (");
			sb.append(max_list.get(0).toString());
			for(int i=1,n=max_list.size();i<n;i++){
				sb.append(",");
				sb.append(max_list.get(i).toString());
			}
			sb.append(")");
			return String.valueOf(new String(sb));
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getSpec(status)-p2.getSpec(status);
				}
			};
		}
	},
	
	
	EFF_ALL("努力値(全て)"){
		@Override
		public String getInformation(PokeData poke){
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<6;i++){
				final int eff=poke.getEff(Statuses.values()[i]);
				if(eff>0){
					sb.append("+");
				}
				sb.append(poke.getEff(Statuses.values()[i]));
				if(i<5) sb.append(",");
			}
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getTotalEff()-p2.getTotalEff();
				}
			};
		}
	},
	EFF_HP("努力値(HP)"){
		private final Statuses status = Statuses.H;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_ATTACK("努力値(攻撃)"){
		private final Statuses status = Statuses.A;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_BLOCK("努力値(防御)"){
		private final Statuses status = Statuses.B;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_CONTACT("努力値(特殊)"){
		private final Statuses status = Statuses.C;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_DEFENSE("努力値(特防)"){
		private final Statuses status = Statuses.H;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_SPEED("努力値(素早)"){
		private final Statuses status = Statuses.S;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_TOTAL("努力値(合計)"){
		private final Statuses status = Statuses.TOTAL;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_MIN("努力値(最小)"){
		private final Statuses status = Statuses.MIN;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	EFF_MAX("努力値(最大)"){
		private final Statuses status = Statuses.MAX;
		@Override
		public String getInformation(PokeData poke){
			final int eff=poke.getEff(status);
			StringBuilder sb=new StringBuilder();
			if(eff>0){
				sb.append("+");
			}
			sb.append(eff);
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getEff(status)-p2.getEff(status);
				}
			};
		}
	},
	MAX_PHYSICAL_DEFENSE("最大物理耐久"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return String.valueOf(poke.getMaxPhysicalDefense());
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getMaxPhysicalDefense()-p2.getMaxPhysicalDefense();
				}
			};
		}
	},
	MAX_SPCIAL_DEFENSE("最大特殊耐久"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return String.valueOf(poke.getMaxSpecialDefense());
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getMaxSpecialDefense()-p2.getMaxSpecialDefense();
				}
			};
		}
	},
	HEIGHT("高さ"){

		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			sb.append(poke.getHeight()/10);
			sb.append(".");
			sb.append(poke.getHeight()%10);
			sb.append("m");
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getHeight()-p2.getHeight();
				}
			};
		}
	},
	WEIGHT("重さ"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			sb.append(poke.getWeight()/10);
			sb.append(".");
			sb.append(poke.getWeight()%10);
			sb.append("kg");
			return new String(sb);
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getWeight()-p2.getWeight();
				}
			};
		}
	},
	EGG_GROUP("タマゴグループ"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			EggGroups egg_group2=poke.getEggGroup(1);
			if(egg_group2==null){
				return poke.getEggGroup(0).toString();
			}
			return poke.getEggGroup(0).toString()+" / "+egg_group2.toString();
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					int p1_group=EggGroups.changeEggGroupToNum(p1.getEggGroup(0), p1.getEggGroup(1));
					int p2_group=EggGroups.changeEggGroupToNum(p2.getEggGroup(0), p2.getEggGroup(1));
					return p1_group-p2_group;
				}
			};
		}
	},
	HATCHING_STEP("孵化歩数"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return poke.getHatchingStep().toString();
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getHatchingStep().getIndex()-p2.getHatchingStep().getIndex();
				}
			};
		}
	},
	SEX_RATIO("性別比率(♂:♀)"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			final int male=poke.getSexRatio(Sexes.MALE),
				female=poke.getSexRatio(Sexes.FEMALE);
			if(male<=0){
				if(female<=0){
					return "性別不明";
				}else{
					return "♀のみ";
				}
			}else if(female<=0){
				return "♂のみ";
			}StringBuilder sb=new StringBuilder();
			sb.append(male);
			sb.append(":");
			sb.append(female);
			return new String(sb);
		}

		/**
		 * ♂:♀=1:1,1:3,1:7,3:1,7:1,♂のみ,♀のみ,性別不明
		 * の順になるように
		 */
		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					int p1_male=p1.getSexRatio(Sexes.MALE),
					 	p1_female=p1.getSexRatio(Sexes.FEMALE);
					int p2_male=p2.getSexRatio(Sexes.MALE),
					 	p2_female=p2.getSexRatio(Sexes.FEMALE);
					if(p2_male==p1_male){
						return p2_female-p1_female;
					}
					return p2_male-p1_male;
					/*final int p1_female=p1.getSexRatio(Sexes.FEMALE)<=0?0:p1.getSexRatio(Sexes.FEMALE),
							  p1_sex=p1.getSexRatio(Sexes.MALE)<=0?0:p1.getSexRatio(Sexes.MALE)+p1_female;
					
					final int p2_female=p2.getSexRatio(Sexes.FEMALE)<=0?0:p2.getSexRatio(Sexes.FEMALE),
							  p2_sex=p2.getSexRatio(Sexes.MALE)<=0?0:p2.getSexRatio(Sexes.MALE)+p2_female;
					return (p1_female*p2_sex)-(p2_female*p1_sex);*/
				}
			};
		}
	},
	ITEM_COMMON("持っている道具(通常)"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			//ItemDataManagerから道具名を取ってきて返す
			final ItemData item=poke.getItem(ItemRarities.COMMON);
			if(item==null) return "-";
			return item.toString();
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					final ItemData item1=p1.getItem(ItemRarities.COMMON);
					final ItemData item2=p2.getItem(ItemRarities.COMMON);
					return item1.compareTo(item2);
				}
			};
		}
	},
	ITEM_RARE("持っている道具(レア)"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			//ItemDataManagerから道具名を取ってきて返す
			final ItemData item=poke.getItem(ItemRarities.RARE);
			if(item==null) return "-";
			return item.toString();
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					final ItemData item1=p1.getItem(ItemRarities.RARE);
					final ItemData item2=p2.getItem(ItemRarities.RARE);
					return item1.compareTo(item2);
				}
			};
		}
	},
	FINAL_EX("最終経験値"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return poke.getFinalEx().toString();
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getFinalEx().getIndex()-p2.getFinalEx().getIndex();
				}
			};
		}
	},
	EASE_GET("つかまえやすさ"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return String.valueOf(poke.getEaseGet());
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getEaseGet()-p2.getEaseGet();
				}
			};
		}
	},
	BASIC_EX("基礎経験値"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return String.valueOf(poke.getBasicEx());
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getBasicEx()-p2.getBasicEx();
				}
			};
		}
	},
	INITIAL_NATSUKI("初期なつき"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			return String.valueOf(poke.getInitialNatsuki());
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1,PokeData p2){
					return p1.getInitialNatsuki()-p2.getInitialNatsuki();
				}
			};
		}
	},
	KETAGURI_KUSAMUSUBI("草結びの威力"){
		@Override
		public String getInformation(PokeData poke) {
			// TODO Auto-generated method stub
			final int weight=poke.getWeight();
			if(weight<=100)		  return "20";
			else if(weight<=250)  return "40";
			else if(weight<=500)  return "60";
			else if(weight<=1000) return "80";
			else if(weight<=2000) return "100";
			return "120";
		}

		@Override
		public Comparator<PokeData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<PokeData>(){
				@Override
				public int compare(PokeData p1, PokeData p2) {
					// TODO Auto-generated method stub
					return p1.getWeight()-p2.getWeight();
				}
			};
		}
	};
	
	
	//====================================================================
	private final String name;
	PokeViewableInformations(String name){
		this.name=name;
	}
	@Override
	public String toString(){
		return name;
	}
	//表示する情報を取得
	abstract public String getInformation(PokeData poke);
	
	/**
	 * 項目全てを文字列配列で取得
	 * @return
	 */
	public static String[] toStringArray(){
		String[] array=new String[values().length];
		for(int i=0,n=array.length;i<n;i++){
			array[i]=values()[i].toString();
		}
		return array;
	}
	/**
	 * ソートするときのComparatorを取得
	 * 一つ目の引数がが二つ目の引数より小さければ負の整数、同じであれば０、大きければ正の整数を戻す。
	 * 
	 * @return
	 */
	abstract public Comparator<PokeData> getComparator();
}
