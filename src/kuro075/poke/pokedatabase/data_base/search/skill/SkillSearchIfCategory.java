package kuro075.poke.pokedatabase.data_base.search.skill;

import kuro075.poke.pokedatabase.data_base.search.SearchIfCategory;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;

public interface SkillSearchIfCategory extends SearchIfCategory{
	/**
	 * poke_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param skill_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	public SkillData[] search(SkillData[] skill_array,String category,String _case);

}
