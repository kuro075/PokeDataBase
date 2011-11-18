package kuro075.poke.pokedatabase.data_base.viewable_informations;

import java.util.Comparator;

import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.CharacterTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;

public enum CharacterViewableInformations {
	BATTLE_EFFECT("戦闘中の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			return chara.getBattleEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					return c1.getBattleEffect().compareTo(c2.getBattleEffect());
				}
			};
		}
	},
	FIELD_EFFECT("フィールド上の効果") {
		@Override
		public String getInformation(CharacterData chara) {
			return chara.getFieldEffect();
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					return c1.getFieldEffect().compareTo(c2.getFieldEffect());
				}
			};
		}
	},
	TIMING("発動タイミング"){
		@Override
		public String getInformation(CharacterData chara) {
			CharacterData.Timings[] timings=chara.getAllTimings();
			StringBuilder sb=new StringBuilder();
			sb.append(timings[0]);
			for(int i=1,n=timings.length;i<n;i++){
				sb.append(",");
				sb.append(timings[i]);
			}
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=0;
					int n1=c1.getAllTimings().length;
					int n2=c2.getAllTimings().length;
					for(int i=0;i<n1 && i<n2 && d==0;i++){
						d=c1.getAllTimings()[i].compareTo(c2.getAllTimings()[i]);
					}
					if(d==0){
						if(n1!=n2){
							return n1-n2;
						}else
							return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	},
	TARGET("対象"){
		@Override
		public String getInformation(CharacterData chara) {
			CharacterData.Targets[] targets=chara.getAllTargets();
			StringBuilder sb=new StringBuilder();
			sb.append(targets[0]);
			for(int i=1,n=targets.length;i<n;i++){
				sb.append(",");
				sb.append(targets[i]);
			}
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=0;
					int n1=c1.getAllTargets().length;
					int n2=c2.getAllTargets().length;
					for(int i=0;i<n1 && i<n2 && d==0;i++){
						d=c1.getAllTargets()[i].compareTo(c2.getAllTargets()[i]);
					}
					if(d==0){
						if(n1!=n2){
							return n1-n2;
						}else
							return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	},
	KIND("効果の種類"){
		@Override
		public String getInformation(CharacterData chara) {
			CharacterData.Kinds[] kinds=chara.getAllKinds();
			StringBuilder sb=new StringBuilder();
			sb.append(kinds[0]);
			for(int i=1,n=kinds.length;i<n;i++){
				sb.append(",");
				sb.append(kinds[i]);
			}
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=0;
					int n1=c1.getAllKinds().length;
					int n2=c2.getAllKinds().length;
					for(int i=0;i<n1 && i<n2 && d==0;i++){
						d=c1.getAllKinds()[i].compareTo(c2.getAllKinds()[i]);
					}
					if(d==0){
						if(n1!=n2){
							return n1-n2;
						}else
							return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_POKE("ポケモンの数"){
		@Override
		public String getInformation(CharacterData chara) {
			StringBuilder sb=new StringBuilder();
			sb.append(chara.getNumPoke());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=c1.getNumPoke()-c2.getNumPoke();
					if(d==0){
						return c1.getNo()-c2.getNo();
					}
					return d;
				}
			};
		}
	},
	NUM_DREAM_POKE("ポケモンの数(夢特性)"){
		@Override
		public String getInformation(CharacterData chara) {
			StringBuilder sb=new StringBuilder();
			sb.append(chara.getNumDreamPoke());
			sb.append("匹");
			return new String(sb);
		}

		@Override
		public Comparator<CharacterData> getComparator() {
			return new Comparator<CharacterData>(){
				@Override
				public int compare(CharacterData c1, CharacterData c2) {
					int d=c1.getNumDreamPoke()-c2.getNumDreamPoke();
					if(d==0){
						return c1.getNo()-c2.getNo();
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
