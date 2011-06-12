package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.skill.SkillData;

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
			return String.valueOf(skill.getPower());
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
	HIT("命中率") {
		@Override
		public String getInformation(SkillData skill) {
			// TODO Auto-generated method stub
			return String.valueOf(skill.getHit());
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
