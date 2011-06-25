package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.util.Utility;

public enum SkillViewableInformations {
	TYPE("タイプ") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return skill.getType().toString();
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getType().getIndex()-s2.getType().getIndex();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	SKILL_CLASS("分類"){
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return skill.getSkillClass().toString();
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getSkillClass().getIndex()-s2.getSkillClass().getIndex();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
		
	},
	POWER("威力"){
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			int pow=skill.getPower();
			if(pow==0){
				return "-";
			}
			return String.valueOf(pow);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getPower()-s2.getPower();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	EXPECTED_POWER_HIT("威力期待値(命中込)"){
		@Override
		public String getInformation(SkillData skill) {
			int pow=skill.getPower(),hit=skill.getHit();
			if(pow<=0){
				return "-";
			}
			return String.valueOf(pow*hit/100);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					final int d=s1.getPower()*s1.getHit()-s2.getPower()*s2.getHit();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	EXPECTED_POWER_HIT_CRITICAL("威力期待値(命中・急所込"){
		private int calc(int pow,int hit,int critical){
			return (pow*hit*(16-critical)+pow*hit*critical*2)/16/100;
		}
		@Override
		public String getInformation(SkillData skill) {
			//急所技かどうか判断
			int pow=skill.getPower(),hit=skill.getHit();
			if(pow<=0){
				return "-";
			}
			return String.valueOf(calc(pow,hit,1));
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					final int d=calc(s1.getPower(),s1.getHit(),1)-calc(s2.getPower(),s2.getHit(),1);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	EXPECTED_POWER_HIT_CRITICAL_EFFECT("威力期待値(命中・急所・効果込"){
		private int calc(int pow,int hit,int critical){
			return (pow*hit*(16-critical)+pow*hit*critical*2)/16/100;
		}
		@Override
		public String getInformation(SkillData skill) {
			//急所技かどうか判断
			int pow=skill.getPower(),hit=skill.getHit();
			if(pow<=0){
				return "-";
			}
			return String.valueOf(calc(pow,hit,1));
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					final int d=calc(s1.getPower(),s1.getHit(),1)-calc(s2.getPower(),s2.getHit(),1);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	HIT("命中率") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			int hit=skill.getHit();
			if(hit==0){
				return "-";
			}
			return String.valueOf(hit);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getHit()-s2.getHit();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	PP("PP") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return String.valueOf(skill.getPp());
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getPp()-s2.getPp();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	DIRECT("直接攻撃") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return skill.getDirect().isDirect()?"○":"×";
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getDirect().getIndex()-s2.getDirect().getIndex();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	TARGET("攻撃対象") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return skill.getTarget().toString();
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getTarget().getIndex()-s2.getTarget().getIndex();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	EFFECT("効果") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return skill.getOutlineEffect();
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					return s1.getNo()-s2.getNo();
				}
			};
		}
	},
	PRIORITY("優先度") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			final int priority=skill.getPriority();
			if(priority>0){
				sb.append("+");
			}
			sb.append(priority);
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			// TODO Auto-generated method stub
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					// TODO Auto-generated method stub
					final int d=s1.getPriority()-s2.getPriority();
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_LEARNING_POKE("覚えるポケモンの数"){
		private int getNumLearningPoke(SkillData skill){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkill(skill)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(SkillData skill) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumLearningPoke(skill));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					int d=getNumLearningPoke(s1)-getNumLearningPoke(s2);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_LV_LEARNING_POKE("覚えるポケモンの数(Lv)"){
		private int getNumLearningPoke(SkillData skill){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkillByLvSkill(skill)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(SkillData skill) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumLearningPoke(skill));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					int d=getNumLearningPoke(s1)-getNumLearningPoke(s2);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_MACHINE_LEARNING_POKE("覚えるポケモンの数(わざマシン)"){
		private int getNumLearningPoke(SkillData skill){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkillByMachine(skill)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(SkillData skill) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumLearningPoke(skill));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					int d=getNumLearningPoke(s1)-getNumLearningPoke(s2);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_EGG_LEARNING_POKE("覚えるポケモンの数(タマゴわざ)"){
		private int getNumLearningPoke(SkillData skill){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkillByEggSkill(skill)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(SkillData skill) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumLearningPoke(skill));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					int d=getNumLearningPoke(s1)-getNumLearningPoke(s2);
					if(d==0){
						return s1.getNo()-s2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_TEACH_LEARNING_POKE("覚えるポケモンの数(教え技)"){
		private int getNumLearningPoke(SkillData skill){
			int num=0;
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkillByTeachSkill(skill)){
					num++;
				}
			}
			return num;
		}
		@Override
		public String getInformation(SkillData skill) {
			StringBuilder sb=new StringBuilder();
			sb.append(getNumLearningPoke(skill));
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<SkillData> getComparator() {
			return new Comparator<SkillData>(){
				@Override
				public int compare(SkillData s1, SkillData s2) {
					int d=getNumLearningPoke(s1)-getNumLearningPoke(s2);
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
	SkillViewableInformations(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	/**
	 * 表示する情報を取得
	 * @param skill
	 * @return
	 */
	abstract public String getInformation(SkillData skill);
	/**
	 * ソートに使うComparatorを取得
	 * @return
	 */
	abstract public Comparator<SkillData> getComparator();
}
