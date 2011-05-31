package kuro075.poke.pokedatabase.data_base.search.skill;

import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import android.content.Context;

/**
 * SkillDataの検索可能情報
 * @author sanogenma
 *
 */
public enum SkillSearchableInformations implements SkillSearchIfCategory{
	NAME("名前") {

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	private final String name;
	SkillSearchableInformations(String name){this.name=name;}
	@Override
	public String toString(){return name;}


	/**
	 * 文字列からSearchableInformationsを取得
	 * @param step
	 * @return
	 */
	public static SkillSearchableInformations fromString(String name){
		for(SkillSearchableInformations info:values()){
			if(info.toString().equals(name)){
				return info;
			}
		}
		return null;
	}



}
