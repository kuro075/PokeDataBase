package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataForSearch;

/**
 * タイプの情報表示項目
 * 特徴(ポケモン),特徴(わざ),タイプ相性(x4,x2,x1,/2,/4,x0)の数(攻撃時、防御時),最高種族値(H,A,B,C,D,S,T),ポケモンの数、わざの数
 * @author sanogenma
 *
 */
public enum TypeViewableInformations {
	NUM_POKE("ポケモンの数"){
		@Override
		public String getInformation(TypeDataForSearch type) {
			StringBuilder sb=new StringBuilder();
			sb.append(type.getNumPoke());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getNumPoke()-type2.getNumPoke();
				}
			};
		}
	},
	NUM_SKILL("わざの数"){
		@Override
		public String getInformation(TypeDataForSearch type) {
			StringBuilder sb=new StringBuilder();
			sb.append(type.getNumSkill());
			sb.append("個");
			return new String(sb);
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getNumSkill()-type2.getNumSkill();
				}
			};
		}
	},
	FEATURE_POKEMON("特徴(ポケモン)"){
		@Override
		public String getInformation(TypeDataForSearch type) {
			if(type.isSingleType())
				return type.getType1().getPokeFeature();
			else
				return type.getType1().getPokeFeature()+type.getType2().getPokeFeature();
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getType1().getPokeFeature().compareTo(type2.getType2().getPokeFeature());
				}
			};
		}
	},
	FEATURE_SKILL("特徴(わざ)"){
		@Override
		public String getInformation(TypeDataForSearch type) {
			if(type.isSingleType())
				return type.getType1().getSkillFeature();
			else
				return type.getType1().getSkillFeature()+type.getType2().getSkillFeature();
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getType1().getSkillFeature().compareTo(type2.getType2().getSkillFeature());
				}
			};
		}
	},
	MAX_SPEC_H("最高種族値(HP)"){
		private final PokeData.Statuses status=PokeData.Statuses.H;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_A("最高種族値(攻撃)"){
		private final PokeData.Statuses status=PokeData.Statuses.A;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_B("最高種族値(防御)"){
		private final PokeData.Statuses status=PokeData.Statuses.B;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_C("最高種族値(特攻)"){
		private final PokeData.Statuses status=PokeData.Statuses.C;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_D("最高種族値(特防)"){
		private final PokeData.Statuses status=PokeData.Statuses.D;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_S("最高種族値(素早)"){
		private final PokeData.Statuses status=PokeData.Statuses.S;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MAX_SPEC_T("最高種族値(合計)"){
		private final PokeData.Statuses status=PokeData.Statuses.TOTAL;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMaxSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMaxSpec(status)-type2.getMaxSpec(status);
				}
			};
		}
	},
	MIN_SPEC_H("最低種族値(HP)"){
		private final PokeData.Statuses status=PokeData.Statuses.H;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_A("最低種族値(攻撃)"){
		private final PokeData.Statuses status=PokeData.Statuses.A;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_B("最低種族値(防御)"){
		private final PokeData.Statuses status=PokeData.Statuses.B;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_C("最低種族値(特攻)"){
		private final PokeData.Statuses status=PokeData.Statuses.C;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_D("最低種族値(特防)"){
		private final PokeData.Statuses status=PokeData.Statuses.D;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_S("最低種族値(素早)"){
		private final PokeData.Statuses status=PokeData.Statuses.S;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	MIN_SPEC_T("最低種族値(合計)"){
		private final PokeData.Statuses status=PokeData.Statuses.TOTAL;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getMinSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getMinSpec(status)-type2.getMinSpec(status);
				}
			};
		}
	},
	AVE_SPEC_H("平均種族値(HP)"){
		private final PokeData.Statuses status=PokeData.Statuses.H;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_A("平均種族値(攻撃)"){
		private final PokeData.Statuses status=PokeData.Statuses.A;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_B("平均種族値(防御)"){
		private final PokeData.Statuses status=PokeData.Statuses.B;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_C("平均種族値(特攻)"){
		private final PokeData.Statuses status=PokeData.Statuses.C;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_D("平均種族値(特防)"){
		private final PokeData.Statuses status=PokeData.Statuses.D;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_S("平均種族値(素早)"){
		private final PokeData.Statuses status=PokeData.Statuses.S;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	},
	AVE_SPEC_T("平均種族値(合計)"){
		private final PokeData.Statuses status=PokeData.Statuses.TOTAL;
		@Override
		public String getInformation(TypeDataForSearch type) {
			int spec=type.getAveSpec(status);
			if(spec>0){
				return String.valueOf(spec);
			}
			return "-";
		}

		@Override
		public Comparator<TypeDataForSearch> getComparator() {
			return new Comparator<TypeDataForSearch>(){
				@Override
				public int compare(TypeDataForSearch type1,
						TypeDataForSearch type2) {
					return type1.getAveSpec(status)-type2.getAveSpec(status);
				}
			};
		}
	};

	/*========/
	/  メンバ  /
	/========*/
	private final String name;
	TypeViewableInformations(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	/**
	 * 表示する情報を取得
	 * @param skill
	 * @return
	 */
	abstract public String getInformation(TypeDataForSearch type);
	/**
	 * ソートに使うComparatorを取得
	 * @return
	 */
	abstract public Comparator<TypeDataForSearch> getComparator();
}
